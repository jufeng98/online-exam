package org.javamaster.fragmentlearning.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author yudong
 * @date 2019/09/19
 */
public class SubmitAnswersResVo {

    private Integer score;
    private List<AnswerDetail> answerDetails;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<AnswerDetail> getAnswerDetails() {
        return answerDetails;
    }

    public void setAnswerDetails(List<AnswerDetail> answerDetails) {
        this.answerDetails = answerDetails;
    }
}
