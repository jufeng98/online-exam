package org.javamaster.fragmentlearning.data.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 知识表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class Knowledges {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 知识编码,K+5位数字
     */
    private String knowledgesCode;

    /**
     * 知识名称
     */
    private String knowledgesName;

    /**
     * 顺序
     */
    private Integer sortOrder;

    /**
     * 关联章节表的sections_code
     */
    private String sectionsCode;

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
     * 获取知识编码,K+5位数字
     */
    public String getKnowledgesCode() {
        return knowledgesCode;
    }

    /**
     * 设置知识编码,K+5位数字
     */
    public void setKnowledgesCode(String knowledgesCode) {
        this.knowledgesCode = knowledgesCode;
    }

    /**
     * 获取知识名称
     */
    public String getKnowledgesName() {
        return knowledgesName;
    }

    /**
     * 设置知识名称
     */
    public void setKnowledgesName(String knowledgesName) {
        this.knowledgesName = knowledgesName;
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
     * 获取关联章节表的sections_code
     */
    public String getSectionsCode() {
        return sectionsCode;
    }

    /**
     * 设置关联章节表的sections_code
     */
    public void setSectionsCode(String sectionsCode) {
        this.sectionsCode = sectionsCode;
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