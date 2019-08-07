package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 知识点表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class KnowledgePoints {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 知识点编码,KP+5位数字
     */
    private String knowledgePointsCode;

    /**
     * 知识点内容
     */
    private String knowledgePointsContent;

    /**
     * 顺序
     */
    private Integer sortOrder;

    /**
     * 关联知识表的knowledges_code
     */
    private String knowledgesCode;

    /**
     * 创建人编号
     */
    private String createUsename;

    /**
     * 创建时间
     */
    private Date createTime;

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
     * 获取知识点编码,KP+5位数字
     */
    public String getKnowledgePointsCode() {
        return knowledgePointsCode;
    }

    /**
     * 设置知识点编码,KP+5位数字
     */
    public void setKnowledgePointsCode(String knowledgePointsCode) {
        this.knowledgePointsCode = knowledgePointsCode;
    }

    /**
     * 获取知识点内容
     */
    public String getKnowledgePointsContent() {
        return knowledgePointsContent;
    }

    /**
     * 设置知识点内容
     */
    public void setKnowledgePointsContent(String knowledgePointsContent) {
        this.knowledgePointsContent = knowledgePointsContent;
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
     * 获取关联知识表的knowledges_code
     */
    public String getKnowledgesCode() {
        return knowledgesCode;
    }

    /**
     * 设置关联知识表的knowledges_code
     */
    public void setKnowledgesCode(String knowledgesCode) {
        this.knowledgesCode = knowledgesCode;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}