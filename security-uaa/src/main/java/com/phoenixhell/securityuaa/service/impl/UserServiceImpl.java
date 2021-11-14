package com.phoenixhell.securityuaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.exception.MyException;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
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

    //根据用户id 权限缓存
    //@Cacheable(value = "authorities:", key = "'userId-'+#args[0]")
    @Override
    public List<String> getStringAuthorities(Long userId) {
        return baseMapper.getGrantedAuthorities(userId);
    }


    //根据角色获取权限permissions 缓存
    @Cacheable(value = "authorities", key = "'role-'+#root.args[0]")
    public List<String> getAuthoritiesByRole(String role) {
        List<String> authorities = baseMapper.getAuthoritiesByRole(role);
        return authorities;
    }

    //根据多个角色获取权限
    public List<String> getAuthoritiesByRoles(List<String> roles) {
        HashSet<String> set = new HashSet<>();
        roles.forEach(role -> {
            List<String> authorities = this.getAuthoritiesByRole(role);
            set.addAll(authorities);
        });
        ArrayList<String> arrayList = new ArrayList<>(set);
        return arrayList;
    }


    @Transactional
    //设置角色权限
    public void setRolesByUserId(List<String> roles, Long userId) {
        List<UsersRolesEntity> usersRolesEntities = usersRolesService.query().eq("user_id", userId).list();
        if(usersRolesEntities!=null && usersRolesEntities.size()>0){
            QueryWrapper<UsersRolesEntity> wrapper = new QueryWrapper<UsersRolesEntity>().eq("user_id", userId);
            boolean remove = usersRolesService.remove(wrapper);
            if (!remove) {
                throw new MyException(40000, "删除用户权限失败");
            }
        }
        roles.forEach(role -> {
            Long roleId = roleService.query().eq("name", role).one().getRoleId();
            if (roleId == null) {
                throw new MyException(40000, "无此角色");
            }
            UsersRolesEntity usersRolesEntity = new UsersRolesEntity();
            usersRolesEntity.setRoleId(roleId);
            usersRolesEntity.setUserId(userId);
            boolean save = usersRolesService.save(usersRolesEntity);
            if (!save) {
                throw new MyException(40000, "保存用户权限失败");
            }
        });
    }

    @Override
    public List<String> getRoles(Long userId) {
        List<UsersRolesEntity> usersRolesEntities = usersRolesService.query().eq("user_id", userId).list();
        List<Long> roleIds = usersRolesEntities.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<RoleEntity> roleEntities = roleService.listByIds(roleIds);
        List<String> roles = roleEntities.stream().map(item -> item.getName()).collect(Collectors.toList());
        return roles;
    }

    public List<String> getAllRoles() {
        //查询出所有roles
        List<String> allRoles = roleService.list().stream().map(roleEntity -> roleEntity.getName()).collect(Collectors.toList());
        return allRoles;
    }

    @Override
    public UserVo getUserVoById(Long userId) {
        UserVo userVo = new UserVo();
        userVo.setAllRoles(this.getAllRoles());
        // 增加用户的时候需要角色列表
        if(userId==0L){
            return userVo;
        }
        UserEntity userEntity = this.getById(userId);
        BeanUtils.copyProperties(userEntity, userVo);
        List<String> authorities = getStringAuthorities(userId);
        userVo.setPermissions(authorities);
        List<String> roles = this.getRoles(userId);
        userVo.setRoles(roles);
        return userVo;
    }

    @Transactional
    @Override
    public void updateByUserVo(UserVo userVo) {
        String username = userVo.getUsername();
        UserEntity user = this.query().eq("username", username).one();
        if (user != null && !user.getUserId().equals(userVo.getUserId())) {
            throw new MyException(40004, "用户已经存在");
        }
        //复制普通属性
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo, userEntity);
        if (userVo.getUserId()==1L){
            userEntity.setUsername(null);
        }
        boolean update = this.updateById(userEntity);
        if (!update) {
            throw new MyException(40002, "更新用户失败");
        }
        if (!userVo.getUsername().equals("admin")){
            this.setRolesByUserId(userVo.getRoles(), userVo.getUserId());
        }
        //根据 roles update permission(menu)
    }

    @Transactional
    @Override
    public void saveUserVo(UserVo userVo) {
        String username = userVo.getUsername();
        UserEntity user = this.query().eq("username", username).one();
        if (user != null) {
            throw new MyException(40004, "用户已经存在");
        }
        //复制普通属性
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo, userEntity);
        boolean save = this.save(userEntity);
        if (!save) {
            throw new MyException(40002, "更新用户失败");
        }
        this.setRolesByUserId(userVo.getRoles(), userEntity.getUserId());
        //根据 roles update permission(menu)
    }

}
