package com.phoenixhell.securityuaa.exception;

import com.phoenixhell.common.exception.MyException;
import com.phoenixhell.common.utils.ExceptionCodeEnume;
import com.phoenixhell.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @author phoenixhell
 * @since 2021/11/5 0005-下午 4:44
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //後端校驗全局異常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidationException(MethodArgumentNotValidException e) {
        log.error("validate", e);
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(item -> {
            errorMap.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(ExceptionCodeEnume.VALID_EXCEPTION.getCode(), ExceptionCodeEnume.VALID_EXCEPTION.getMessage()).put("data", errorMap);
    }

    //精确匹配不到的错误 最后来到这写了控制台就不报错了
    @ExceptionHandler(value = MyException.class)
    public R handleMyException(MyException e) {
        log.error("error===>", e);
        return R.error(e.getCode(), e.getMsg());

    }

    //精确匹配不到的错误 最后来到这写了控制台就不报错了
    @ExceptionHandler(value = RuntimeException.class)
    public R handleException(Exception e) {
        log.error("error===>", e);
        return R.error(ExceptionCodeEnume.UNKNOWN_EXCEPTION.getCode(), ExceptionCodeEnume.UNKNOWN_EXCEPTION.getMessage()).put("data", e.getMessage());

    }

}
