package org.javamaster.b2c.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.model.vo.ExamQuestionsVo;

import java.util.List;

/**
 * @author yudong
 */
public interface ExtMapper {
    List<ExamQuestionsVo> selectQuestionsByExamsCode(@Param("examsCode") String examsCode);

    int findKnowledgePointsCount(@Param("topicsCode") String topicsCode, @Param("sectionsCode") String sectionsCode);
}