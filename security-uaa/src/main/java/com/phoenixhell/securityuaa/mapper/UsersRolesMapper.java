package com.phoenixhell.securityuaa.mapper;

import com.phoenixhell.securityuaa.entity.UsersRolesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-06 10:27:35
 */
@Mapper
public interface UsersRolesMapper extends BaseMapper<UsersRolesEntity> {

}
