package com.phoenixhell.securityuaa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.securityuaa.mapper.RolesMenusMapper;
import com.phoenixhell.securityuaa.entity.RolesMenusEntity;
import com.phoenixhell.securityuaa.service.RolesMenusService;
import org.springframework.transaction.annotation.Transactional;


@Service("rolesMenusService")
public class RolesMenusServiceImpl extends ServiceImpl<RolesMenusMapper, RolesMenusEntity> implements RolesMenusService {
    @Autowired
    private RolesMenusService rolesMenusService;

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
        //1 第一步传过来的选中的对应关系menuId 和原来的 menuId roleId 的list 比对
        // 少了就从list删除 原来没有的就添加

        List<RolesMenusEntity> list = rolesMenusService.query().eq("role_id",rolesMenusEntities.get(0).getRoleId()).list();
        ArrayList<RolesMenusEntity> deleteRolesMenusEntities = new ArrayList<>();
        list.forEach(item->{
            //应该有的没有了就是删除
            if(!rolesMenusEntities.contains(item)){
                deleteRolesMenusEntities.add(item);
            }
        });
        if(deleteRolesMenusEntities.size()>0){
            List<Long> menuIds = deleteRolesMenusEntities.stream().map(item -> item.getMenuId()).collect(Collectors.toList());
            this.removeByIds(menuIds);
        }
        //没有就添加 有就更新
        baseMapper.savePermissions(rolesMenusEntities);
    }

}