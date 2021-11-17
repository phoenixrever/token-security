package com.phoenixhell.securityuaa.service.impl;

import com.phoenixhell.securityuaa.service.MenuService;
import com.phoenixhell.securityuaa.service.UserService;
import com.phoenixhell.securityuaa.vo.MenuTreeVo;
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
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
        );
        List<RoleEntity> collect = page.getRecords().stream().map(item -> {
            List<RoleEntity.PermissionVo> authoritiesByRole = userService.getAuthoritiesByRole(item.getName());
            item.setPermissions(authoritiesByRole);
            return item;
        }).collect(Collectors.toList());
        page.setRecords(collect);
        return new PageUtils(page);
    }

    @Override
    public RoleEntity getRoleWithAllPermissionsById(Long roleId) {
        List<RoleEntity.PermissionVo> allPermissions = menuService.list().stream().map(menu -> {
            RoleEntity.PermissionVo permissionVo = new RoleEntity.PermissionVo();
            permissionVo.setPermissionId(menu.getMenuId());
            permissionVo.setName(menu.getTitle());
            permissionVo.setPermission(menu.getPermission());
            return permissionVo;
        }).filter(item -> !StringUtils.isEmpty(item.getPermission())).collect(Collectors.toList());
        // 增加用户的时候需要角色列表
        RoleEntity roleEntity=null;
        if(roleId==0L){
             roleEntity = new RoleEntity();
        }else{
             roleEntity = this.getById(roleId);
        }

        roleEntity.setAllPermissions(allPermissions);
        return roleEntity;
    }

    @Override
    public RoleTreeVo getTreeById(Long userId) {
        List<MenuTreeVo> menuTreeVos = menuService.buildMenuTree();
        List<RoleEntity.PermissionVo> stringAuthorities = userService.getStringAuthorities(userId);
        List<Long> checkedIds = stringAuthorities.stream().map(p -> p.getPermissionId()).collect(Collectors.toList());
        RoleTreeVo roleTreeVo = new RoleTreeVo();
        roleTreeVo.setCheckedIds(checkedIds);
        roleTreeVo.setMenuTreeVos(menuTreeVos);
        return roleTreeVo;
    }

}