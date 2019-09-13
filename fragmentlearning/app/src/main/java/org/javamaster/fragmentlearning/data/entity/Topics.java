package org.javamaster.fragmentlearning.data.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

/**
 * @author yudong
 */
public class Topics extends LitePalSupport {
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
     * 关联考试表exams_code
     */
    private String examsCode;

    private Integer topicsType;
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

    public Integer getTopicsType() {
        return topicsType;
    }

    public void setTopicsType(Integer topicsType) {
        this.topicsType = topicsType;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}