package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 投票表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class Votes {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 投票类型,1:讨论;2:回复
     */
    private Byte type;

    /**
     * 关联discussions表主键id或replys表主键id
     */
    private Integer relateId;

    /**
     * 投票人用户名
     */
    private String username;

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
     * 获取投票类型,1:讨论;2:回复
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置投票类型,1:讨论;2:回复
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取关联discussions表主键id或replys表主键id
     */
    public Integer getRelateId() {
        return relateId;
    }

    /**
     * 设置关联discussions表主键id或replys表主键id
     */
    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    /**
     * 获取投票人用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置投票人用户名
     */
    public void setUsername(String username) {
        this.username = username;
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