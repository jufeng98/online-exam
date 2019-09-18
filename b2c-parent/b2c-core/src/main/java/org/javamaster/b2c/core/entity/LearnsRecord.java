package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学习记录表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class LearnsRecord {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 关联主题表topics_code
     */
    private String topicsCode;

    /**
     * 关联章节表sections_code
     */
    private String sectionsCode;

    /**
     * 关联知识表knowledges_code
     */
    private String knowledgesCode;

    /**
     * 关联知识点表knowledge_points_code
     */
    private String knowledgePointsCode;

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
     * 获取关联主题表topics_code
     */
    public String getTopicsCode() {
        return topicsCode;
    }

    /**
     * 设置关联主题表topics_code
     */
    public void setTopicsCode(String topicsCode) {
        this.topicsCode = topicsCode;
    }

    /**
     * 获取关联章节表sections_code
     */
    public String getSectionsCode() {
        return sectionsCode;
    }

    /**
     * 设置关联章节表sections_code
     */
    public void setSectionsCode(String sectionsCode) {
        this.sectionsCode = sectionsCode;
    }

    /**
     * 获取关联知识表knowledges_code
     */
    public String getKnowledgesCode() {
        return knowledgesCode;
    }

    /**
     * 设置关联知识表knowledges_code
     */
    public void setKnowledgesCode(String knowledgesCode) {
        this.knowledgesCode = knowledgesCode;
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