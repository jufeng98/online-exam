package org.javamaster.b2c.core.enums;

/**
 * @author yudong
 * @date 2019/6/10
 */
public enum ReadEnum implements EnumBase {
    UNREAD(1, "未读"),
    READ(2, "已读"),
    ;
    private final int code;
    private final String msg;


    ReadEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
