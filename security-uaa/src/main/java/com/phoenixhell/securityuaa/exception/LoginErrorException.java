package com.phoenixhell.securityuaa.exception;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class LoginErrorException {
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public String error(Exception e){
        e.printStackTrace();
        return e.getMessage();
    }
}
