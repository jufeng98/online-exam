package org.javamaster.b2c.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 知识点题目表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class KnowledgePointsQuestions {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 关联知识点表knowledge_points_code
     */
    private String knowledgePointsCode;

    /**
     * 关联题目表questions_code
     */
    private String questionsCode;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

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
     * 获取关联知识点表knowledge_points_code
     */
    public String getKnowledgePointsCode() {
        return knowledgePointsCode;
    }

    /**
     * 设置关联知识点表knowledge_points_code
     */
    public void setKnowledgePointsCode(String knowledgePointsCode) {
        this.knowledgePointsCode = knowledgePointsCode;
    }

    /**
     * 获取关联题目表questions_code
     */
    public String getQuestionsCode() {
        return questionsCode;
    }

    /**
     * 设置关联题目表questions_code
     */
    public void setQuestionsCode(String questionsCode) {
        this.questionsCode = questionsCode;
    }
}