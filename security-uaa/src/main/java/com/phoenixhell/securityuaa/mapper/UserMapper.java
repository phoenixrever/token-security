package com.phoenixhell.securityuaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenixhell.securityuaa.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author phoenixhell
 * @since 2021-11-05
 */
public interface UserMapper extends BaseMapper<UserEntity> {
    List<String> getGrantedAuthorities(@Param("userId") Long userId);

    List<String> getAuthoritiesByRole(@Param("role") String role);
}
