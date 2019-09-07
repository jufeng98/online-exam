package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class Users {
    /**
     * 用户名
     */
    private String username;

    /**
     * 别名
     */
    private String nickname;

    /**
     * 性别,M:男;F:女
     */
    private String gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像url
     */
    private String picUrl;

    /**
     * 账户永不过期
     */
    private Boolean accountNonExpired;

    /**
     * 账户永不锁定
     */
    private Boolean accountNonLocked;

    /**
     * 凭证永不过期
     */
    private Boolean credentialsNonExpired;

    /**
     * 账户启用状态
     */
    private Boolean enabled;

    /**
     * 账户删除状态
     */
    private Boolean delFlag;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账户创建人
     */
    private String createUsername;

    /**
     * 账户创建时间
     */
    private Date createTime;

    /**
     * 最后操作人
     */
    private String lastOpUsername;

    /**
     * 最后操作时间
     */
    private Date lastOpTime;

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
     * 获取别名
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置别名
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取性别,M:男;F:女
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别,M:男;F:女
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取头像url
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置头像url
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * 获取账户永不过期
     */
    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * 设置账户永不过期
     */
    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /**
     * 获取账户永不锁定
     */
    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * 设置账户永不锁定
     */
    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /**
     * 获取凭证永不过期
     */
    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * 设置凭证永不过期
     */
    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    /**
     * 获取账户启用状态
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置账户启用状态
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取账户删除状态
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 设置账户删除状态
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取账户创建人
     */
    public String getCreateUsername() {
        return createUsername;
    }

    /**
     * 设置账户创建人
     */
    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    /**
     * 获取账户创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置账户创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后操作人
     */
    public String getLastOpUsername() {
        return lastOpUsername;
    }

    /**
     * 设置最后操作人
     */
    public void setLastOpUsername(String lastOpUsername) {
        this.lastOpUsername = lastOpUsername;
    }

    /**
     * 获取最后操作时间
     */
    public Date getLastOpTime() {
        return lastOpTime;
    }

    /**
     * 设置最后操作时间
     */
    public void setLastOpTime(Date lastOpTime) {
        this.lastOpTime = lastOpTime;
    }
}