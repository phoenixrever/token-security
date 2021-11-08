package com.phoenixhell.securityuaa.controller;

import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @PreAuthorize("hasAnyAuthority('p1')")
    @GetMapping({"/","/index"})
    public String index(Model model) {
        UserEntity currentUser = SecurityUtils.getCurrentUser();
        model.addAttribute("username", currentUser.getUsername());
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "403";
    }
}
