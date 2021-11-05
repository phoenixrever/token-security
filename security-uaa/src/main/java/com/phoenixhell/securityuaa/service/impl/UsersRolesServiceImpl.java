package com.phoenixhell.securityuaa.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.securityuaa.dao.UsersRolesDao;
import com.phoenixhell.securityuaa.entity.UsersRolesEntity;
import com.phoenixhell.securityuaa.service.UsersRolesService;


@Service("usersRolesService")
public class UsersRolesServiceImpl extends ServiceImpl<UsersRolesDao, UsersRolesEntity> implements UsersRolesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsersRolesEntity> page = this.page(
                new Query<UsersRolesEntity>().getPage(params),
                new QueryWrapper<UsersRolesEntity>()
        );

        return new PageUtils(page);
    }

}