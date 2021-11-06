package com.phoenixhell.securityuaa.handler;

import com.alibaba.fastjson.JSON;
import com.phoenixhell.common.exception.MyException;
import com.phoenixhell.common.utils.ExceptionCodeEnume;
import com.phoenixhell.common.utils.R;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author phoenixhell
 * @since 2021/11/6 0006-下午 2:35
 * The access denied handler 只用于 @PreAuthorized, @PostAuthorized, & @Secured.
 * 并且需要登陆  未登录的异常全部在 AuthenticationEntryPoint
 */

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        R error = R.error(ExceptionCodeEnume.ACCESS_DENIED.getCode(), ExceptionCodeEnume.ACCESS_DENIED.getMsg());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(JSON.toJSONString(error));
        System.out.println("CustomAccessDeniedHandler==>");
        System.out.println(JSON.toJSONString(error));
    }
}
