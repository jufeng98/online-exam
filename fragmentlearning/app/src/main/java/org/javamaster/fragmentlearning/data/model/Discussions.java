package org.javamaster.fragmentlearning.data.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 讨论表,请勿手工改动此文件,请使用 mybatis generator
 *
 * @author mybatis generator
 */
public class Discussions {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 问题
     */
    private String question;

    /**
     * 描述
     */
    private String description;

    /**
     * 关联标签,用;隔开
     */
    private String relevantTags;

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
     * 获取用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取问题
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 设置问题
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 获取描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取关联标签,用;隔开
     */
    public String getRelevantTags() {
        return relevantTags;
    }

    /**
     * 设置关联标签,用;隔开
     */
    public void setRelevantTags(String relevantTags) {
        this.relevantTags = relevantTags;
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