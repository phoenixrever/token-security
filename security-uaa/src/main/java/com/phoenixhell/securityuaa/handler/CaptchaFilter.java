package com.phoenixhell.securityuaa.handler;

import com.alibaba.fastjson.JSON;
import com.phoenixhell.common.utils.ExceptionCodeEnume;
import com.phoenixhell.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *  这里可以对加密传输的密码解密
 *  OncePerRequestFilter 能够确保在一次请求中只通过一次filter
 * filter 必须加 @Component 注入到容器
 */

@Component
public class CaptchaFilter extends OncePerRequestFilter {
    private static final String CAPTCHA_PREFIX="captcha:";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        RedisTemplate<String,String> stringRedisTemplate = ApplicationContextUtils.getBean("redisTemplate");
        if(request.getRequestURI().contains("/securityuaa/oauth/token")){
            String code = request.getParameter("code");
            String captchaKey= request.getParameter("captchaKey");
            String redisCode = stringRedisTemplate.opsForValue().get(CAPTCHA_PREFIX + captchaKey);
            if(!StringUtils.isEmpty(captchaKey) && !StringUtils.isEmpty(code) && code.equalsIgnoreCase(redisCode)){
                super.doFilter(request, response, filterChain);
            }else{
                R error = R.error(ExceptionCodeEnume.CAPTCHA_EXCEPTION.getCode(), ExceptionCodeEnume.CAPTCHA_EXCEPTION.getMessage());
                System.out.println("验证码验证错误");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                response.getWriter().write(JSON.toJSONString(error));
            }
        }else{
            super.doFilter(request, response, filterChain);
        }
    }
}
