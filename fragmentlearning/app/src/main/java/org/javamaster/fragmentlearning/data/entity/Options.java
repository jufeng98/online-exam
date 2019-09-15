package org.javamaster.fragmentlearning.data.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

/**
 * 选项表,请勿手工改动此文件,请使用 mybatis generator
 *
 * @author mybatis generator
 */
public class Options extends LitePalSupport {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 选项名称
     */
    private String optionName;

    /**
     * 是否正确答案,仅针对非排序题
     */
    private Boolean correct;

    /**
     * 选项排序,仅针对排序题
     */
    private Integer sort;

    /**
     * 关联题目表questions_code
     */
    private String questionsCode;

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
     * 获取选项名称
     */
    public String getOptionName() {
        return optionName;
    }

    /**
     * 设置选项名称
     */
    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    /**
     * 获取是否正确答案,仅针对非排序题
     */
    public Boolean getCorrect() {
        return correct;
    }

    /**
     * 设置是否正确答案,仅针对非排序题
     */
    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    /**
     * 获取选项排序,仅针对排序题
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置选项排序,仅针对排序题
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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