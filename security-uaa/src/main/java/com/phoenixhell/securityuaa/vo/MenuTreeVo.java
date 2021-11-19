package com.phoenixhell.securityuaa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/11/12 0012-下午 4:17
 */

@Data
public class MenuTreeVo {
    private Long menuId;
    private Long pid;
    private Integer menuSort;
    private Integer type;
    private String  Icon;
    private String label;
    private Boolean disabled=false;
    private String permission;
    private List<MenuTreeVo> children;
}
