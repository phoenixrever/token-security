package com.phoenixhell.securityuaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.phoenixhell.securityuaa.entity.User;
import com.phoenixhell.securityuaa.mapper.UserMapper;
import com.phoenixhell.securityuaa.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<String> getStringAuthorities(String username) {
        return baseMapper.getGrantedAuthorities(username);
    }
}
