package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author yudong
 * @date 2019/08/10
 */
@Data
public class FindOptionsListResVo {
    private Integer radio;
    private List<Boolean> selects;
    private List<String> options;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
