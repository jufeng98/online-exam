package org.javamaster.b2c.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 权限菜单表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class AuthoritiesMenus {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 关联users_authorities表的authority
     */
    private String authority;

    /**
     * 关联menus表的id
     */
    private Integer menusId;

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
     * 获取关联users_authorities表的authority
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * 设置关联users_authorities表的authority
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    /**
     * 获取关联menus表的id
     */
    public Integer getMenusId() {
        return menusId;
    }

    /**
     * 设置关联menus表的id
     */
    public void setMenusId(Integer menusId) {
        this.menusId = menusId;
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