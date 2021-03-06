package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javamaster.b2c.core.model.Page;

/**
 * @author yudong
 * @date 2019/08/11
 */
@Data
public class FindExamsListReqVo {
    private String examsCode;
    private String examsName;
    private Page page;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
