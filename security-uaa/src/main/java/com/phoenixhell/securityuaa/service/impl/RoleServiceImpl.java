package com.phoenixhell.securityuaa.service.impl;

import com.phoenixhell.securityuaa.service.MenuService;
import com.phoenixhell.securityuaa.service.UserService;
import com.phoenixhell.securityuaa.vo.MenuTreeVo;
import com.phoenixhell.securityuaa.vo.PermissionVo;
import com.phoenixhell.securityuaa.vo.RoleTreeVo;
import com.phoenixhell.securityuaa.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.securityuaa.mapper.RoleMapper;
import com.phoenixhell.securityuaa.entity.RoleEntity;
import com.phoenixhell.securityuaa.service.RoleService;
import org.springframework.util.StringUtils;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        String keyword = (String )params.get("keyword");
        if(!StringUtils.isEmpty(keyword)) {
            wrapper.like("name", keyword)
                    .or().like("description", keyword);
        }
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                wrapper
        );
        //分页数据里面不需要权限了 (单独在tree里面获取)
//        List<RoleEntity> collect = page.getRecords().stream().map(item -> {
//            List<PermissionVo> authoritiesByRole = userService.getAuthoritiesByRole(item.getName());
//            item.setPermissions(authoritiesByRole);
//            return item;
//        }).collect(Collectors.toList());
//        page.setRecords(collect);
        return new PageUtils(page);
    }


    @Override
    public RoleTreeVo getTreeByRoleId(Long roleId) {
        List<MenuTreeVo> menuTreeVos = menuService.buildMenuTree();
        if(roleId==1){
            //管理员禁用全部选项
            disableAllNodes(menuTreeVos);
        }
        RoleEntity roleEntity = this.getById(roleId);
        List<PermissionVo> stringAuthorities = userService.getAuthoritiesByRole(roleEntity.getName());
        List<Long> checkedIds = stringAuthorities.stream().map(p -> p.getPermissionId()).collect(Collectors.toList());
        RoleTreeVo roleTreeVo = new RoleTreeVo();
        roleTreeVo.setCheckedIds(checkedIds);
        roleTreeVo.setMenuTreeVos(menuTreeVos);
        return roleTreeVo;
    }

    //admin 禁用全部tree节点选择框
    public List<MenuTreeVo> disableAllNodes(List<MenuTreeVo> menuTreeVos) {
        return menuTreeVos.stream().map(m -> {
            m.setDisabled(true);
            if (m.getChildren().size() > 0) {
                disableAllNodes(m.getChildren());
            }
            return m;
        }).collect(Collectors.toList());
    }
}