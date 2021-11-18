package com.phoenixhell.securityuaa.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.securityuaa.mapper.RolesMenusMapper;
import com.phoenixhell.securityuaa.entity.RolesMenusEntity;
import com.phoenixhell.securityuaa.service.RolesMenusService;


@Service("rolesMenusService")
public class RolesMenusServiceImpl extends ServiceImpl<RolesMenusMapper, RolesMenusEntity> implements RolesMenusService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolesMenusEntity> page = this.page(
                new Query<RolesMenusEntity>().getPage(params),
                new QueryWrapper<RolesMenusEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void savePermissions(List<RolesMenusEntity> rolesMenusEntities) {
        baseMapper.savePermissions(rolesMenusEntities);
    }

}