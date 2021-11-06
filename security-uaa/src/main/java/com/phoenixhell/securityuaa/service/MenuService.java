package com.phoenixhell.securityuaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.securityuaa.entity.MenuEntity;

import java.util.Map;

/**
 * 系统菜单
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-06 10:27:35
 */
public interface MenuService extends IService<MenuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

