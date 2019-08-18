package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author yudong
 * @date 2019/08/17
 */
@Data
public class AddOrEditAssociateQuestionsReqVo {
    @NotBlank
    private String examsCode;
    @NotEmpty
    private List<String> questionsCodes;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
