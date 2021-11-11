package com.phoenixhell.securityuaa.controller;


import com.phoenixhell.common.utils.R;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
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
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private TokenStore tokenStore;

    private static final String CAPTCHA_PREFIX="captcha:";

    @RequestMapping("/captcha")
    public R captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(2);  // 几位数运算，默认是两位
        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
        captcha.text();  // 获取运算的结果：5

        String verCode = captcha.text();
        String captchaKey = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisTemplate.opsForValue().set(CAPTCHA_PREFIX+captchaKey,verCode,30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        return R.ok().put("captchaKey", captchaKey).put("captchaImage", captcha.toBase64());
    }

    @PostMapping("/oauth/logout")
    public R revokeToken(HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.contains("Bearer")) {
                String tokenValue = authorization.replace("Bearer", "").trim();

                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);

                //OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(tokenValue);
                OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
                tokenStore.removeRefreshToken(refreshToken);
            }
        } catch (Exception e) {
            return R.error(30000,"Invalid access token");
        }
        return R.ok("Access token invalidated successfully");
    }
}
