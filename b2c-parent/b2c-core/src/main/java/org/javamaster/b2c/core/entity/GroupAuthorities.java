package org.javamaster.b2c.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 权限组权限表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class GroupAuthorities {
    /**
     * 关联groups表id
     */
    private Integer groupId;

    /**
     * 关联权限表的authority
     */
    private String authority;

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
     * 获取关联权限表的authority
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * 设置关联权限表的authority
     */
    public void setAuthority(String authority) {
        this.authority = authority;
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