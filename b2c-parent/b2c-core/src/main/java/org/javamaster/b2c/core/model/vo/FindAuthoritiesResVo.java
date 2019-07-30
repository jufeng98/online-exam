package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author yudong
 * @date 2019/7/28
 */
@Data
public class FindAuthoritiesResVo {
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
