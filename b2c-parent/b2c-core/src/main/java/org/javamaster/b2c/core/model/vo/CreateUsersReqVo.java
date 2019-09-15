package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author yudong
 * @date 2019/7/5
 */
@Data
public class CreateUsersReqVo {
    @NotNull
    @Valid
    private CreateUsersForm createOrEditUsersForm;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(ToStringStyle.JSON_STYLE);
    }

}
