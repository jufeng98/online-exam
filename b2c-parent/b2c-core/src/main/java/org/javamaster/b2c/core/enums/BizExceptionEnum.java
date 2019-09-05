package org.javamaster.b2c.core.enums;

/**
 * @author yudong
 * @date 2019/6/10
 */
public enum BizExceptionEnum {
    APPLICATION_ERROR(1000, "网络繁忙,请稍后再试"),
    INVALID_USER(1001, "用户名或密码错误"),
    INVALID_REQ_PARAM(1002, "参数错误"),
    USERNAME_NOT_EXISTS(1003, "用户不存在"),
    PASSWORD_INCORRECT(1004, "密码错误"),
    USER_EXISTS(1005, "用户已存在"),
    OPERATION_TOO_FREQUENT(1006, "操作过于频繁，请稍后再试"),
    ACCESS_DENIED(1007, "不允许访问"),
    AUTHORITIES_EXISTS(1008, "角色已存在"),
    AUTHORITIES_NOT_EXISTS(1009, "角色不存在"),
    AUTHORITIES_ASSOCIATE_USER(1010, "角色已授权用户,请先解除授权"),
    EMAIL_EXISTS(1011, "邮箱已被使用,请重新填写"),
    ;

    BizExceptionEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private final Integer errorCode;
    private final String errorMsg;

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
