package com.phoenixhell.securityuaa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/11/12 0012-下午 4:17
 */

@Data
public class MenuTreeVo {
    public String label;
    public List<MenuTreeVo> children;
}
