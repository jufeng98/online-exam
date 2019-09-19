package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author yudong
 * @date 2019/9/19
 */
@Data
public class ExamsAnswer {
    @NotNull
    private Integer questionsNum;
    @NotBlank
    private String questionsCode;
    @NotNull
    @Size(min = 1)
    private Set<Integer> answers;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
