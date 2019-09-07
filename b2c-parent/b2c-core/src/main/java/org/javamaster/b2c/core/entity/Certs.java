package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 证书表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class Certs {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 证书名称
     */
    private String certsName;

    /**
     * 证书说明
     */
    private String certsDesc;

    /**
     * 证书样图url
     */
    private String picUrl;

    /**
     * 创建人名称
     */
    private String createUsername;

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
     * 获取证书名称
     */
    public String getCertsName() {
        return certsName;
    }

    /**
     * 设置证书名称
     */
    public void setCertsName(String certsName) {
        this.certsName = certsName;
    }

    /**
     * 获取证书说明
     */
    public String getCertsDesc() {
        return certsDesc;
    }

    /**
     * 设置证书说明
     */
    public void setCertsDesc(String certsDesc) {
        this.certsDesc = certsDesc;
    }

    /**
     * 获取证书样图url
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置证书样图url
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * 获取创建人名称
     */
    public String getCreateUsername() {
        return createUsername;
    }

    /**
     * 设置创建人名称
     */
    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
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