package com.phoenixhell.securityuaa.mapper;

import com.phoenixhell.securityuaa.entity.RolesMenusEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色菜单关联
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-06 10:27:35
 */
@Mapper
public interface RolesMenusMapper extends BaseMapper<RolesMenusEntity> {

    void savePermissions(List<RolesMenusEntity> rolesMenusEntities);

}
