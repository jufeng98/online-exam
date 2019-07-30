package org.javamaster.b2c.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 权限表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class UsersAuthorities {
    /**
     * 权限
     */
    private String authority;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 获取权限
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * 设置权限
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    /**
     * 获取权限名称
     */
    public String getAuthorityName() {
        return authorityName;
    }

    /**
     * 设置权限名称
     */
    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
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