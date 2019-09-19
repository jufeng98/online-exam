package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * @author yudong
 * @date 2019/9/19
 */
@Data
public class AnswerDetail {
    private String questionsCode;
    private Boolean correct;
    private Set<Integer> examsRightAnswers;
    private String answerAnalysis;
    private Integer score;
    private Integer questionsNum;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
