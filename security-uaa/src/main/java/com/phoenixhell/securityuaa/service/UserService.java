package com.phoenixhell.securityuaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
public interface UserService extends IService<UserEntity> {
   PageUtils queryPage(Map<String, Object> params);

   List<String> getStringAuthorities(Long userId);

    List<String> getRoles(Long userId);

    UserVo getUserVoById(Long userId);

    void updateByUserVo(UserVo userVo);

    void saveUserVo(UserVo userVo);

    List<String> getAuthoritiesByRole(String role);
}
