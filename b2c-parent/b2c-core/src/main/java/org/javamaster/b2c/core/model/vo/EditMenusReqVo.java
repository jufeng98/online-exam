package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author yudong
 * @date 2019/8/02
 */
@Data
public class EditMenusReqVo {
    @NotNull
    @Valid
    private EditMenusVo createOrEditMenusForm;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
