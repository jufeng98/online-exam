package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javamaster.b2c.core.entity.Exams;
import org.javamaster.b2c.core.entity.ExamsQuestions;

import java.util.List;

/**
 * @author yudong
 * @date 2019/08/11
 */
@Data
public class EditExamsReqVo {
    private Exams examsForm;
    private List<ExamsQuestions> examsQuestionsForm;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
