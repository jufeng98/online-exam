package org.javamaster.fragmentlearning.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author yudong
 * @date 2019/9/19
 */
public class AnswerDetail {
    private Integer questionsNum;
    private String questionsCode;
    private Boolean correct;
    private Set<Integer> examsRightAnswers;
    private String answerAnalysis;

    @NotNull
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public Integer getQuestionsNum() {
        return questionsNum;
    }

    public void setQuestionsNum(Integer questionsNum) {
        this.questionsNum = questionsNum;
    }

    public String getQuestionsCode() {
        return questionsCode;
    }

    public void setQuestionsCode(String questionsCode) {
        this.questionsCode = questionsCode;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Set<Integer> getExamsRightAnswers() {
        return examsRightAnswers;
    }

    public void setExamsRightAnswers(Set<Integer> examsRightAnswers) {
        this.examsRightAnswers = examsRightAnswers;
    }

    public String getAnswerAnalysis() {
        return answerAnalysis;
    }

    public void setAnswerAnalysis(String answerAnalysis) {
        this.answerAnalysis = answerAnalysis;
    }
}
