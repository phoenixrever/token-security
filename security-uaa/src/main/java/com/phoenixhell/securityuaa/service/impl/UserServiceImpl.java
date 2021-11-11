package com.phoenixhell.securityuaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;
import com.phoenixhell.securityuaa.config.UserPage;
import com.phoenixhell.securityuaa.entity.RoleEntity;
import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.entity.UsersRolesEntity;
import com.phoenixhell.securityuaa.mapper.UserMapper;
import com.phoenixhell.securityuaa.service.RoleService;
import com.phoenixhell.securityuaa.service.UserService;
import com.phoenixhell.securityuaa.service.UsersRolesService;
import com.phoenixhell.securityuaa.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Autowired
    private UsersRolesService usersRolesService;
    @Autowired
    private RoleService roleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
          /*
         显示除了某两个字段,其他所有字段.
         queryWrapper.select(User.class, info -> !info.getColumn().equals("manager_id")
                && !info.getColumn().equals("create_time"));
         */
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.select(UserEntity.class, user -> !user.getColumn().equals("password"));
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params), wrapper
        );

        //UserPage<T> extends Page<T>  自定义setRecords 方法
        Page<UserVo> userVoPage = new Page<>();
        //老的分页属性全部复制过来 (records T 泛型不同不能复制 )
        BeanUtils.copyProperties(page, userVoPage);

        List<UserVo> userVos = page.getRecords().stream().map(userEntity -> {
            //获取每个user 的roles
            List<String> roles = this.getRoles(userEntity.getUserId());
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userEntity, userVo);
            userVo.setRoles(roles);
            return userVo;
        }).collect(Collectors.toList());

        userVoPage.setRecords(userVos);
        return new PageUtils(userVoPage);
    }

    @Cacheable(value = "authorities", key = "#root.args[0]")
    @Override
    public List<String> getStringAuthorities(Long userId) {
        return baseMapper.getGrantedAuthorities(userId);
    }

    @Override
    public List<String> getRoles(Long userId) {
        List<UsersRolesEntity> usersRolesEntities = usersRolesService.query().eq("user_id", userId).list();
        List<Long> roleIds = usersRolesEntities.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<RoleEntity> roleEntities = roleService.listByIds(roleIds);
        List<String> roles = roleEntities.stream().map(item -> item.getName()).collect(Collectors.toList());
        return roles;
    }

    @Override
    public UserVo getUserVoById(Long userId) {
        UserEntity userEntity = this.getById(userId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userEntity,userVo);
        List<String> authorities = getStringAuthorities(userId);
        userVo.setPermissions(authorities);
        List<String> roles = this.getRoles(userId);
        userVo.setRoles(roles);
        return userVo;
    }

}
