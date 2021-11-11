package com.phoenixhell.securityuaa.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.phoenixhell.securityuaa.entity.Router;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/11/11 0011-上午 10:39
 */

@Data
public class UserVo {
    /**
     * ID
     */

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
    private String avatarName;
    /**
     * 头像真实路径
     */
    private String avatarPath;
    /**
     * 菜单routers
     */
    private List<Router> routers;

    /**
     * 拥有的权限
     */
    private List<String> permissions;

    /**
     * 角色
     */
    private List<String> roles;
    /**
     * 状态：1启用、0禁用
     */
    private Boolean enabled;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 修改密码的时间
     */
    private Date pwdResetTime;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
