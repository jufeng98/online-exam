package org.javamaster.b2c.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 权限组用户表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class GroupMembers {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 关联groups表id
     */
    private Integer groupId;

    /**
     * 关联users表username
     */
    private String username;

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
     * 获取关联groups表id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置关联groups表id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取关联users表username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置关联users表username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}