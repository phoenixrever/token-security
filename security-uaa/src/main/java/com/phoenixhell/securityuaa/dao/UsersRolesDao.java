package com.phoenixhell.securityuaa.dao;

import com.phoenixhell.securityuaa.entity.UsersRolesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@Mapper
public interface UsersRolesDao extends BaseMapper<UsersRolesEntity> {
	
}
