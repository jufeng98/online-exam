package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javamaster.b2c.core.entity.KnowledgePoints;
import org.javamaster.b2c.core.model.Page;

/**
 * @author yudong
 * @date 2019/08/07
 */
@Data
public class FindKnowledgePointsListReqVo {
    private KnowledgePoints knowledgePointsForm;
    private Page page;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
