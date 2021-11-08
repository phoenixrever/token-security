package com.phoenixhell.securityuaa.handler;

import com.phoenixhell.securityuaa.utils.ApplicationContextUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  用户提交用户名、密码被SecurityFilterChain中的 UsernamePasswordAuthenticationFilter 过滤器获取到，
 *  封装为请求Authentication，通常情况下是UsernamePasswordAuthenticationToken这个实现类。
 *
 *  照着 UsernamePasswordAuthenticationFilter 的attemptAuthentication 内容修改
 */

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private static final String CAPTCHA_PREFIX="captcha:";
    private boolean postOnly = true;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        RedisTemplate<String,String> stringRedisTemplate = ApplicationContextUtils.getBean("redisTemplate");
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String code = request.getParameter("code");
        String redisCode = stringRedisTemplate.opsForValue().get(CAPTCHA_PREFIX + request.getParameter("key"));
        //这里可以对加密传输的密码解密
        String username = super.obtainUsername(request).trim();
        String password = super.obtainPassword(request).trim();
        System.out.println("【用户名】username：" + username);
        System.out.println("【密    码】password：" + password);

        if (code != null && code.equalsIgnoreCase(redisCode)) {//验证码正确
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            return super.getAuthenticationManager().authenticate(token);
        }else {
            //如果有需要也可以继续判断用户名和密码的情况
            throw new AuthenticationServiceException("验证码不正确");
        }
    }
}
