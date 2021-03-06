package org.javamaster.b2c.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javamaster.b2c.core.entity.Users;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author yudong
 * @date 2019/9/7
 */
public class UserVo extends Users {

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
