package com.phoenixhell.securityuaa.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统菜单
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@Data
@TableName("sys_menu")
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long menuId;
	/**
	 * 上级菜单ID
	 */
	private Long pid;

	/**
	 * 菜单标题
	 */
	private String title;
	/**
	 * 组件名称
	 */
	private String name;
	/**
	 * 组件
	 */
	private String component;
	/**
	 * 排序
	 */
	private Integer menuSort;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 链接地址
	 */
	private String path;
	/**
	 * 重定向地址
	 */
	private String redirect;
	/**
	 * 是否外链
	 */
	private Boolean iFrame;
	/**
	 * 缓存
	 */
	private Boolean cache;
	/**
	 * 隐藏
	 */
	private Integer hidden;
	/**
	 * 权限
	 */
	private String permission;
	/**
	 * 创建者
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createBy;
	/**
	 * 更新者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	/**
	 * 创建日期
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

}
