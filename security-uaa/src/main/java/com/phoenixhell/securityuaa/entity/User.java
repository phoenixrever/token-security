package com.phoenixhell.securityuaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
@Data

@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    /**
     * 用户姓名
     */
    private String fullname;

    /**
     * 手机号
     */
    private String mobie;


}
