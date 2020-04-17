package org.javamaster.b2c.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 请勿手工改动此文件,请使用 mybatis generator
 *
 * @author mybatis generator
 */
public enum TopicsTypeEnum {
    /**
     * 未知枚举值
     */
    UNKNOWN(-1, ""),
    /**
     * 编程挑战
     */
    CODING(1, "编程挑战"),
    /**
     * 网页开发
     */
    WEB(2, "网页开发"),
    /**
     * 编程语言
     */
    LANG(3, "编程语言"),
    /**
     * 数据科学
     */
    DATA(4, "数据科学"),
    /**
     * 开发基础
     */
    FUND(5, "开发基础"),
    ;
    private final int code;
    private final String msg;
    private static final Map<Integer, TopicsTypeEnum> MAP;

    static {
        MAP = new HashMap<>(8, 1);
        for (TopicsTypeEnum value : TopicsTypeEnum.values()) {
            MAP.put(value.getCode(), value);
        }
    }

    TopicsTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static TopicsTypeEnum getByCode(Integer code) {
        return MAP.getOrDefault(code, UNKNOWN);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}