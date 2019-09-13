package org.javamaster.b2c.core.model.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author yudong
 * @date 2019/9/13
 */
public class KnowledgesQuestionNumVo {
    private String sectionsCode;
    private String knowledgesCode;
    private Integer questionsNum;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String getSectionsCode() {
        return sectionsCode;
    }

    public void setSectionsCode(String sectionsCode) {
        this.sectionsCode = sectionsCode;
    }

    public String getKnowledgesCode() {
        return knowledgesCode;
    }

    public void setKnowledgesCode(String knowledgesCode) {
        this.knowledgesCode = knowledgesCode;
    }

    public Integer getQuestionsNum() {
        return questionsNum;
    }

    public void setQuestionsNum(Integer questionsNum) {
        this.questionsNum = questionsNum;
    }
}
