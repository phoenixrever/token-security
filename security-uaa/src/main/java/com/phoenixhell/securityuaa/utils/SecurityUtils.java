package com.phoenixhell.securityuaa.utils;

import com.alibaba.fastjson.JSONObject;
import com.phoenixhell.common.exception.MyException;
import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author phoenixhell
 * @since 2021/11/8 0008-上午 9:30
 */

public class SecurityUtils {

    @Autowired
    private static UserService userService;

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserEntity getCurrentUser() {
        UserEntity userEntity = userService.query().eq("username", getCurrentUsername()).one();
        return userEntity;
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new MyException(401, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new MyException(401, "找不到当前登录的信息");
    }

}
