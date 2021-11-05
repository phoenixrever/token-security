package com.phoenixhell.securityuaa.dao;

import com.phoenixhell.securityuaa.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
    List<String> getGrantedAuthorities(@Param("username") String username);
}
