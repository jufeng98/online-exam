package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 题目表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class Questions {
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

    /**
     * 分数
     */
    private Integer questionsScore;

    /**
     * 顺序
     */
    private Integer sortOrder;

    /**
     * 答案解析
     */
    private String answerAnalysis;

    /**
     * 创建人编号
     */
    private String createUsename;

    /**
     * 创建时间
     */
    private Date createTime;

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
     * 获取题目编码,Q+5位数字
     */
    public String getQuestionsCode() {
        return questionsCode;
    }

    /**
     * 设置题目编码,Q+5位数字
     */
    public void setQuestionsCode(String questionsCode) {
        this.questionsCode = questionsCode;
    }

    /**
     * 获取题目标题
     */
    public String getQuestionsTitle() {
        return questionsTitle;
    }

    /**
     * 设置题目标题
     */
    public void setQuestionsTitle(String questionsTitle) {
        this.questionsTitle = questionsTitle;
    }

    /**
     * 获取题目类型,1:单选;2:多选;3:判断:4:排序
     */
    public Byte getQuestionsType() {
        return questionsType;
    }

    /**
     * 设置题目类型,1:单选;2:多选;3:判断:4:排序
     */
    public void setQuestionsType(Byte questionsType) {
        this.questionsType = questionsType;
    }

    /**
     * 获取分数
     */
    public Integer getQuestionsScore() {
        return questionsScore;
    }

    /**
     * 设置分数
     */
    public void setQuestionsScore(Integer questionsScore) {
        this.questionsScore = questionsScore;
    }

    /**
     * 获取顺序
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * 设置顺序
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 获取答案解析
     */
    public String getAnswerAnalysis() {
        return answerAnalysis;
    }

    /**
     * 设置答案解析
     */
    public void setAnswerAnalysis(String answerAnalysis) {
        this.answerAnalysis = answerAnalysis;
    }

    /**
     * 获取创建人编号
     */
    public String getCreateUsename() {
        return createUsename;
    }

    /**
     * 设置创建人编号
     */
    public void setCreateUsename(String createUsename) {
        this.createUsename = createUsename;
    }

    /**
     * 获取创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}