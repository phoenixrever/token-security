package com.phoenixhell.securityuaa.controller;


import com.phoenixhell.common.utils.R;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("securityuaa/auth")
public class AuthController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String CAPTCHA_PREFIX="captcha:";

    @RequestMapping("/captcha")
    public R captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        stringRedisTemplate.opsForValue().set(CAPTCHA_PREFIX+key,verCode,30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        return R.ok().put("key", key).put("captchaImage", specCaptcha.toBase64());
    }
}
