package org.javamaster.b2c.core;

import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.exception.BizException;
import org.javamaster.b2c.core.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author yudong
 * @date 2019/6/10
 */
@RestControllerAdvice
public class GlobalHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> exceptionHandler(MethodArgumentNotValidException e) {
        Result<Void> result = new Result<>(BizExceptionEnum.INVALID_REQ_PARAM.getErrorCode(),
                BizExceptionEnum.INVALID_REQ_PARAM.getErrorMsg());
        logger.error("req params error", e);
        return result;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> exceptionHandler(ConstraintViolationException e) {
        Result<Void> result = new Result<>(BizExceptionEnum.INVALID_REQ_PARAM.getErrorCode(),
                BizExceptionEnum.INVALID_REQ_PARAM.getErrorMsg());
        logger.error("req params error", e);
        return result;
    }

    @ExceptionHandler(BizException.class)
    public Result<Void> exceptionHandler(BizException e) {
        BizExceptionEnum exceptionEnum = e.getBizExceptionEnum();
        Result<Void> result = new Result<>(exceptionEnum.getErrorCode(), exceptionEnum.getErrorMsg());
        logger.error("business error", e);
        return result;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result<Void> exceptionHandler(AccessDeniedException e) {
        Result<Void> result = new Result<>(BizExceptionEnum.ACCESS_DENIED.getErrorCode(),
                BizExceptionEnum.ACCESS_DENIED.getErrorMsg());
        logger.error("access error", e);
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> exceptionHandler(Exception e) {
        Result<Void> result = new Result<>(BizExceptionEnum.APPLICATION_ERROR.getErrorCode(),
                BizExceptionEnum.APPLICATION_ERROR.getErrorMsg());
        logger.error("application error", e);
        return result;
    }

}
