package org.javamaster.b2c.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yudong
 * @date 2019/6/10
 */
public enum TopicsTypeEnum implements EnumBase {
    UNKNOWN(0, ""),
    CODING(1, "编码挑战"),
    WEB(2, "网页开发"),
    LANG(3, "编程语言"),
    DATA(4, "数据科学"),
    DEV(5, "开发基础"),
    ;
    private final int code;
    private final String msg;
    private static final Map<Integer, TopicsTypeEnum> MAP;

    static {
        MAP = new HashMap<>(6, 1);
        for (TopicsTypeEnum value : TopicsTypeEnum.values()) {
            MAP.put(value.getCode(), value);
        }
    }

    TopicsTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static TopicsTypeEnum getEnumByCode(Integer code) {
        return MAP.getOrDefault(code, UNKNOWN);
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
