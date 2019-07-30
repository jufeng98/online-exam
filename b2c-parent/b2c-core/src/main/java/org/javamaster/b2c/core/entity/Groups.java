package org.javamaster.b2c.core.entity;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 权限组表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 * @date 2019/07/29 15:40:48
 */
public class Groups implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 组名
     */
    private String groupName;

    private static final long serialVersionUID = 8100928395277774848L;

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
     * 获取组名
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置组名
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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