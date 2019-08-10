package org.javamaster.b2c.core.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.javamaster.b2c.core.enums.QuestionsTypeEnum;
import org.javamaster.b2c.core.jackson.EnumBaseDeserializer;

import java.util.List;

/**
 * @author yudong
 * @date 2019/8/10
 */
@Data
public class QuestionsReqVo {
    private Integer id;
    private String questionsCode;
    @JsonDeserialize(using = EnumBaseDeserializer.class)
    private QuestionsTypeEnum questionsType;
    private String questionsTitle;
    private String answerAnalysis;
    private Integer sortOrder;
    private Integer radio;
    private List<Boolean> selects;
    private List<String> options;
}
