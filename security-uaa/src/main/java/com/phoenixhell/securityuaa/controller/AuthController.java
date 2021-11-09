package com.phoenixhell.securityuaa.controller;


import com.phoenixhell.common.utils.R;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(2);  // 几位数运算，默认是两位
        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
        captcha.text();  // 获取运算的结果：5

        String verCode = captcha.text();
        String captchaKey = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        stringRedisTemplate.opsForValue().set(CAPTCHA_PREFIX+captchaKey,verCode,30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        return R.ok().put("captchaKey", captchaKey).put("captchaImage", captcha.toBase64());
    }
}
