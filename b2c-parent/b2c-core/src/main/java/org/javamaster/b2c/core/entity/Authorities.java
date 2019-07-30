package org.javamaster.b2c.core.entity;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户权限表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 * @date 2019/07/29 15:40:48
 */
public class Authorities implements Serializable {
    /**
     * 关联users表的username
     */
    private String username;

    /**
     * 关联权限表的authority
     */
    private String authority;

    private static final long serialVersionUID = 4522114392752457728L;

    /**
     * 获取关联users表的username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置关联users表的username
     */
    public void setUsername(String username) {
        this.username = username;
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