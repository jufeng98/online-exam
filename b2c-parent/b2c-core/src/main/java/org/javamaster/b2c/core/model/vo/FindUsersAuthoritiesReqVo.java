package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javamaster.b2c.core.entity.UsersAuthorities;
import org.javamaster.b2c.core.model.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author yudong
 * @date 2019/7/28
 */
@Data
public class FindUsersAuthoritiesReqVo {
    @Valid
    @NotNull
    private Page page;
    @NotNull
    private UsersAuthorities authoritiesForm;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
