package org.javamaster.b2c.core.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javamaster.b2c.core.enums.QuestionsTypeEnum;
import org.javamaster.b2c.core.jackson.EnumBaseDeserializer;

/**
 * @author yudong
 * @date 2019/08/10
 */
@Data
public class FindOptionsListReqVo {
    private String questionsCode;
    @JsonDeserialize(using = EnumBaseDeserializer.class)
    private QuestionsTypeEnum questionsType;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
