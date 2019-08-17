package org.javamaster.b2c.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 考试试题表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class ExamsQuestions {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 关联考试表exams_code
     */
    private String examsCode;

    /**
     * 关联试题表questions_code
     */
    private String questionsCode;

    /**
     * 获取主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取关联考试表exams_code
     */
    public String getExamsCode() {
        return examsCode;
    }

    /**
     * 设置关联考试表exams_code
     */
    public void setExamsCode(String examsCode) {
        this.examsCode = examsCode;
    }

    /**
     * 获取关联试题表questions_code
     */
    public String getQuestionsCode() {
        return questionsCode;
    }

    /**
     * 设置关联试题表questions_code
     */
    public void setQuestionsCode(String questionsCode) {
        this.questionsCode = questionsCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}