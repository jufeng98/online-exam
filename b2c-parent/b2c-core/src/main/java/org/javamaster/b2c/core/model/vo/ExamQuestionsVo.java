package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yudong
 * @date 2019/9/17
 */
@Data
@NoArgsConstructor
public class ExamQuestionsVo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 题目编码,Q+5位数字
     */
    private String questionsCode;
    /**
     * 题目标题
     */
    private String questionsTitle;
    /**
     * 题目类型,1:单选;2:多选;3:判断:4:排序
     */
    private Byte questionsType;
    private List<OptionsVo> optionsVos;
}
