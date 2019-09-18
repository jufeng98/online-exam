package org.javamaster.fragmentlearning.data.entity;

import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * @author yudong
 * @date 2019/9/17
 */
public class ExamQuestionsVo extends LitePalSupport {
    private String examsCode;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 题目编码,Q+5位数字
     */
    private String questionsCode;
    /**
     * 题目标题
     */
    private String questionsTitle;
    /**
     * 题目类型,1:单选;2:多选;3:判断:4:排序
     */
    private Integer questionsType;
    private List<OptionsVo> optionsVos;

    public String getExamsCode() {
        return examsCode;
    }

    public void setExamsCode(String examsCode) {
        this.examsCode = examsCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionsCode() {
        return questionsCode;
    }

    public void setQuestionsCode(String questionsCode) {
        this.questionsCode = questionsCode;
    }

    public String getQuestionsTitle() {
        return questionsTitle;
    }

    public void setQuestionsTitle(String questionsTitle) {
        this.questionsTitle = questionsTitle;
    }

    public Integer getQuestionsType() {
        return questionsType;
    }

    public void setQuestionsType(Integer questionsType) {
        this.questionsType = questionsType;
    }

    public List<OptionsVo> getOptionsVos() {
        return optionsVos;
    }

    public void setOptionsVos(List<OptionsVo> optionsVos) {
        this.optionsVos = optionsVos;
    }
}
