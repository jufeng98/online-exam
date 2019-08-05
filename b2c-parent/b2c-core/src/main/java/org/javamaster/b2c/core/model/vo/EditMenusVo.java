package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * @author yudong
 * @date 2019/8/2
 */
@Data
public class EditMenusVo {
    @NotNull
    private Integer id;
    private String name;
    private String path;
    private Boolean shouldShow;
    private String icon;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
