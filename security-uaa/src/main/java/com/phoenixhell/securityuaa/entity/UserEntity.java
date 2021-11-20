package com.phoenixhell.securityuaa.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 系统用户
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@Data
@TableName("sys_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private Long userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像地址
	 */
	private String avatar;
	/**
	 * 密码
	 */
	private String password;

	/**
	 * 状态：1启用、0禁用
	 */
	private Integer enabled;
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
	 * 修改密码的时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date pwdResetTime;
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
