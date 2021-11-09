package com.phoenixhell.common.utils;

/**
 * @author phoenixhell
 * @since 2021/11/5 0005-下午 4:46
 */

public enum ExceptionCodeEnume {
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    TO_MANY_REQUEST(10002, "请求过于频繁"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    SMS_CODE_EXCEPTION(10001, "验证码获取频率太高请稍后再试"),
    USER_EXIST_EXCEPTION(15001, "用户名已经存在"),
    PHONE_EXIST_EXCEPTION(15002, "手机号已经存在"),
    CAPTCHA_EXCEPTION(21000, "验证码不正确"),
    TOKEN_NOT_MATCH_EXCEPTION(21001, "令牌校验失败"),
    PRICE_NOT_MATCH_EXCEPTION(21002, "价格校验失败"),
    LOGINACCOUNT_PASSWORD_EXCEPTION(150303, "账号或者密码错误"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),
    FEIGN_EXCEPTION(22000, "feign远程服务调用失败"),
    ACCESS_DENIED(30000, "你无权限访问此页面");

    private Integer code;
    private String message;

    ExceptionCodeEnume(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}