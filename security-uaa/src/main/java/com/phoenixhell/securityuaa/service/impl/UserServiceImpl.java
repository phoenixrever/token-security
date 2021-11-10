package com.phoenixhell.securityuaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;
import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.mapper.UserMapper;
import com.phoenixhell.securityuaa.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
          /*
         显示除了某两个字段,其他所有字段.
         queryWrapper.select(User.class, info -> !info.getColumn().equals("manager_id")
                && !info.getColumn().equals("create_time"));
         */
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.select(UserEntity.class,user->!user.getColumn().equals("password"));
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),wrapper
        );

        return new PageUtils(page);
    }

    @Cacheable(value = {"authorities"},key ="#root.args[0]")
    @Override
    public List<String> getStringAuthorities(String username) {
        return baseMapper.getGrantedAuthorities(username);
    }

}
