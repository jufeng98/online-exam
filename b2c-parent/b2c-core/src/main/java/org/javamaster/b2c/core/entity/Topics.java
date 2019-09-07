package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 主题表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class Topics {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 主题编码,T+5位数字
     */
    private String topicsCode;

    /**
     * 主题名称
     */
    private String topicsName;

    /**
     * 主题类型,1:编码挑战;2:网页开发;3:编程语言;4:数据科学;5:开发基础
     */
    private Byte topicsType;

    /**
     * 关联考试表exams_code
     */
    private String examsCode;

    /**
     * 创建人编号
     */
    private String createUsename;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 主题封面
     */
    private byte[] topicsCoverImage;

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
     * 获取主题编码,T+5位数字
     */
    public String getTopicsCode() {
        return topicsCode;
    }

    /**
     * 设置主题编码,T+5位数字
     */
    public void setTopicsCode(String topicsCode) {
        this.topicsCode = topicsCode;
    }

    /**
     * 获取主题名称
     */
    public String getTopicsName() {
        return topicsName;
    }

    /**
     * 设置主题名称
     */
    public void setTopicsName(String topicsName) {
        this.topicsName = topicsName;
    }

    /**
     * 获取主题类型,1:编码挑战;2:网页开发;3:编程语言;4:数据科学;5:开发基础
     */
    public Byte getTopicsType() {
        return topicsType;
    }

    /**
     * 设置主题类型,1:编码挑战;2:网页开发;3:编程语言;4:数据科学;5:开发基础
     */
    public void setTopicsType(Byte topicsType) {
        this.topicsType = topicsType;
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
     * 获取主题封面
     */
    public byte[] getTopicsCoverImage() {
        return topicsCoverImage;
    }

    /**
     * 设置主题封面
     */
    public void setTopicsCoverImage(byte[] topicsCoverImage) {
        this.topicsCoverImage = topicsCoverImage;
    }
}