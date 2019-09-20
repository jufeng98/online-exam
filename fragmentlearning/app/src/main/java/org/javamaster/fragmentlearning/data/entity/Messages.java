package org.javamaster.fragmentlearning.data.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 消息表,请勿手工改动此文件,请使用 mybatis generator
 *
 * @author mybatis generator
 */
public class Messages {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读,1:否;2:是
     */
    private Byte alreadyRead;

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
     * 获取消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取是否已读,1:否;2:是
     */
    public Byte getAlreadyRead() {
        return alreadyRead;
    }

    /**
     * 设置是否已读,1:否;2:是
     */
    public void setAlreadyRead(Byte alreadyRead) {
        this.alreadyRead = alreadyRead;
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