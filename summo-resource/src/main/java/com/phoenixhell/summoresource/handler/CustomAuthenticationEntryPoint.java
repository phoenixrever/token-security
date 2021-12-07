package com.phoenixhell.summoresource.handler;

import com.alibaba.fastjson.JSON;
import com.phoenixhell.common.utils.ExceptionCodeEnume;
import com.phoenixhell.common.utils.R;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author phoenixhell
 * @since 2021/11/6 0006-下午 2:47
 *
 * 未登录的异常全部在 AuthenticationEntryPoint
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // handle the authentication exception
        // you can return json with Jackson and
        // write it to the HttpServletResponse.
        System.out.println(request.getRequestURI());
        System.out.println(authException.getMessage());
        R error = R.error(10086, "请登录");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(JSON.toJSONString(error));
        System.out.println("CustomAuthenticationEntryPoint==>");
        System.out.println(JSON.toJSONString(error));
    }
}