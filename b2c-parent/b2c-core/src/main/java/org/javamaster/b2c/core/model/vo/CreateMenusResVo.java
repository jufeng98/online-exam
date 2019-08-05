package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javamaster.b2c.core.entity.Menus;

/**
 * @author yudong
 * @date 2019/8/02
 */
@Data
public class CreateMenusResVo {
    private Menus menus;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
