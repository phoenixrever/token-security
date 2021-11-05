package com.phoenixhell.securityuaa.dao;

import com.phoenixhell.securityuaa.entity.MenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统菜单
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@Mapper
public interface MenuDao extends BaseMapper<MenuEntity> {
	
}
