package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author yudong
 * @date 2019/09/18
 */
@Data
public class SectionsProgressVo {
    private String username;
    private String sectionsCode;
    private Integer progress;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
