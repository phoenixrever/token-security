package com.phoenixhell.securityuaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.securityuaa.entity.UsersRolesEntity;

import java.util.Map;

/**
 * 用户角色关联
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
public interface UsersRolesService extends IService<UsersRolesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

