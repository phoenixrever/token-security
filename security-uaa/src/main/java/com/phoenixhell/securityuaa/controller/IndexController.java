package com.phoenixhell.securityuaa.controller;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof org.springframework.security.core.userdetails.User){
            //密码已经被security 清空
            org.springframework.security.core.userdetails.User user= (User) principal;
            model.addAttribute("username", user.getUsername());
        }
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
