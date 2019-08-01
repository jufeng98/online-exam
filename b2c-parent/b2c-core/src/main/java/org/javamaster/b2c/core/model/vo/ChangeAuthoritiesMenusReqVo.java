package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author yudong
 * @date 2019/7/31
 */
@Data
public class ChangeAuthoritiesMenusReqVo {
    @NotBlank
    private String authority;
    @Size(min = 1)
    private List<Integer> menusIds;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
