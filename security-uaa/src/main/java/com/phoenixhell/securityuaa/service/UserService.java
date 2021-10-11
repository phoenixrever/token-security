package com.phoenixhell.securityuaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.securityuaa.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
public interface UserService extends IService<User> {
   List<String> getStringAuthorities(String username);
}
