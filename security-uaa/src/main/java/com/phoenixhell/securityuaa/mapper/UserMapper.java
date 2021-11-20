package com.phoenixhell.securityuaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenixhell.securityuaa.entity.RoleEntity;
import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.vo.PermissionVo;
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
    List<PermissionVo> getGrantedAuthorities(@Param("userId") Long userId);

    List<PermissionVo> getAuthoritiesByRole(@Param("roleName") String roleName);
}
