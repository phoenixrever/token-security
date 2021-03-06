package com.phoenixhell.securityuaa.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author phoenixhell
 * @since 2021/11/8 0008-上午 9:53
 */
@Data
public class Router {
    private String name;
    private String path;
    private String component;
    private Map<String,String> redirect;
    private Integer hidden;
    private Integer isAlwaysShow;
    private Map<String,String> meta;
    private List<Router> children;

}
