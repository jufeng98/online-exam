package org.javamaster.fragmentlearning.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.litepal.crud.LitePalSupport;

/**
 * @author yudong
 * @date 2019/9/17
 */
public class OptionsVo extends LitePalSupport {
    private String questionsCode;

    @JsonProperty("id")
    private Integer optionId;

    /**
     * 选项名称
     */
    private String optionName;

    /**
     * 选项排序,仅针对排序题
     */
    private Integer sort;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getQuestionsCode() {
        return questionsCode;
    }

    public void setQuestionsCode(String questionsCode) {
        this.questionsCode = questionsCode;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
