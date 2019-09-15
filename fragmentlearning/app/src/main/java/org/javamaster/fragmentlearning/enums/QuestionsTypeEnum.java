package org.javamaster.fragmentlearning.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yudong
 * @date 2019/6/10
 */
public enum QuestionsTypeEnum implements EnumBase {
    UNKNOWN(0, ""),
    SINGLE(1, "单选题"),
    MULTIPLY(2, "多选题"),
    JUDGE(3, "判断题"),
    SORT(4, "排序题");
    private static final Map<Integer, QuestionsTypeEnum> MAP;
    private static final Map<String, QuestionsTypeEnum> MAP1;

    static {
        MAP = new HashMap<>(5, 1);
        MAP1 = new HashMap<>(5, 1);
        for (QuestionsTypeEnum value : QuestionsTypeEnum.values()) {
            MAP.put(value.getCode(), value);
            MAP1.put(value.getMsg(), value);
        }
    }

    private final int code;
    private final String msg;

    QuestionsTypeEnum(int code, String msg) {
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
