package com.phoenixhell.securityorder.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phoenixhell
 * @since 2021/10/12 0012-上午 9:03
 */

@RestController
public class ResourceController {
    //@EnableGlobalMethodSecurity 注解一定要开启
//    @PreAuthorize("hasAnyAuthority('p1')")
    @GetMapping("/resource1")
    public String resource(){
        return "resource1";
    }
}
