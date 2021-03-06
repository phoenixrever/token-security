package com.phoenixhell.securityuaa.utils;

import com.phoenixhell.common.exception.MyException;
import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.service.UserService;
import com.phoenixhell.securityuaa.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author phoenixhell
 * @since 2021/11/8 0008-上午 9:30
 */

public class SecurityUtils {


    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserVo getCurrentUser(UserService userService) {
        UserEntity userEntity = userService.query().eq("username", getSecurityUser().getUsername()).one();
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userEntity,userVo );
        List<String> authorities = getAuthorities();
        userVo.setPermissions(authorities);
        return userVo;
    }


    /**
     * 获取当前登录的用户权限
     */
    public static List<String> getAuthorities() {
        User securityUser = getSecurityUser();
        Collection<GrantedAuthority> grantedAuthorities = securityUser.getAuthorities();
        List<String> authorities = grantedAuthorities.stream().map(a -> a.getAuthority()).collect(Collectors.toList());
        return authorities;
    }

    /**
     * 获取系统用户
     *
     * @return 系统用户
     */
    public static User getSecurityUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new MyException(401, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        throw new MyException(401, "找不到当前登录的信息");
    }

}
