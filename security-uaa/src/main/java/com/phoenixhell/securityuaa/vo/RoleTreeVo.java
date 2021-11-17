package com.phoenixhell.securityuaa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/11/17 0017-下午 4:45
 */

@Data
public class RoleTreeVo {
    private List<Long> checkedIds;
    private List<MenuTreeVo> menuTreeVos;
}
