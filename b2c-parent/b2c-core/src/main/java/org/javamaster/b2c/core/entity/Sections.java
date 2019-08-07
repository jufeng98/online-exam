package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 章节表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class Sections {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 章节编码,S+5位数字
     */
    private String sectionsCode;

    /**
     * 章节名称
     */
    private String sectionsName;

    /**
     * 关联主题表topics_code
     */
    private String topicsCode;

    /**
     * 顺序
     */
    private Integer sortOrder;

    /**
     * 创建人编号
     */
    private String createUsename;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 章节封面
     */
    private byte[] sectionsCoverImage;

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
     * 获取章节编码,S+5位数字
     */
    public String getSectionsCode() {
        return sectionsCode;
    }

    /**
     * 设置章节编码,S+5位数字
     */
    public void setSectionsCode(String sectionsCode) {
        this.sectionsCode = sectionsCode;
    }

    /**
     * 获取章节名称
     */
    public String getSectionsName() {
        return sectionsName;
    }

    /**
     * 设置章节名称
     */
    public void setSectionsName(String sectionsName) {
        this.sectionsName = sectionsName;
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

    /**
     * 获取章节封面
     */
    public byte[] getSectionsCoverImage() {
        return sectionsCoverImage;
    }

    /**
     * 设置章节封面
     */
    public void setSectionsCoverImage(byte[] sectionsCoverImage) {
        this.sectionsCoverImage = sectionsCoverImage;
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