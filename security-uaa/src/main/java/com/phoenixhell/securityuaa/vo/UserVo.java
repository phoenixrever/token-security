package com.phoenixhell.securityuaa.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.phoenixhell.securityuaa.entity.Router;
import lombok.Data;
import valid.AddGroup;
import valid.ListValue;
import valid.UpdateGroup;
import valid.UpdateStatusGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/11/11 0011-上午 10:39
 *
 * @NotNull 适用于任何类型被注解的元素必须不能与NULL
 * @NotEmpty 适用于String Map或者数组不能为Null且长度必须大于0
 * @NotBlank 只能用于String上面 不能为null,调用trim()后，长度必须大于0
 */
//todo 校验
@Data
public class UserVo {
    /**
     * ID
     */
    @NotNull(message = "修改必须要指定ID", groups = {UpdateGroup.class, UpdateStatusGroup.class})
    @Null(message = "增加不需要指定ID", groups = AddGroup.class)
    private Long userId;
    /**
     * 用户名
     *
     */
    @NotBlank(message = "用戶名不能为空",groups = {UpdateGroup.class,AddGroup.class})
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
    @NotEmpty(message = "权限不能为空",groups = {AddGroup.class,UpdateGroup.class, UpdateStatusGroup.class})
    private List<String> roles;


    /**
     * 所有角色
     */

    private List<String> allRoles;
    /**
     * 状态：1启用、0禁用
     */
    @NotNull(message = "新增不能为空",groups = {AddGroup.class})
    @ListValue(values = {0,1},groups = {AddGroup.class,UpdateGroup.class, UpdateStatusGroup.class})
    private Integer enabled;
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
