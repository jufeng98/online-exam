package org.javamaster.b2c.core.model.vo;

import cn.com.bluemoon.handypoi.excel.annos.ExcelColumn;
import lombok.Data;

/**
 * @author yudong
 * @date 2019/8/8
 */
@Data
public class QuestionsVo {
    @ExcelColumn(columnName = "题型")
    private String questionsType;
    @ExcelColumn(columnName = "题目内容")
    private String questionsTitle;
    @ExcelColumn(columnName = "可选项")
    private String options;
    @ExcelColumn(columnName = "答案")
    private String answer;
    @ExcelColumn(columnName = "解析")
    private String answerAnalysis;
}
