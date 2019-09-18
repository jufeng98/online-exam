package org.javamaster.b2c.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.model.vo.ExamQuestionsVo;

import java.util.List;

/**
 * @author yudong
 */
public interface QuestionsExtMapper {
    List<ExamQuestionsVo> selectQuestionsByExamsCode(@Param("examsCode") String examsCode);
}