package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Exams;
import org.javamaster.b2c.core.model.vo.AddOrEditAssociateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.AddOrEditAssociateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.CreateExamsReqVo;
import org.javamaster.b2c.core.model.vo.CreateExamsResVo;
import org.javamaster.b2c.core.model.vo.DelExamsReqVo;
import org.javamaster.b2c.core.model.vo.EditExamsReqVo;
import org.javamaster.b2c.core.model.vo.FindAssociateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.FindAssociateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.FindExamsListReqVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 考试管理
 *
 * @author yudong
 * @date 2019/08/11
 */
public interface ExamsService {

    /**
     * 获取考试列表
     *
     * @param reqVo
     * @return
     */
    PageInfo<Exams> findExamsList(FindExamsListReqVo reqVo);

    /**
     * 创建考试
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    CreateExamsResVo createExams(CreateExamsReqVo reqVo, UserDetails userDetails);

    /**
     * 编辑考试
     *
     * @param reqVo
     * @return
     */
    Integer editExams(EditExamsReqVo reqVo);

    /**
     * 删除考试
     *
     * @param reqVo
     * @return
     */
    Integer delExams(DelExamsReqVo reqVo);

    /**
     * 添加或编辑关联的试题
     *
     * @param reqVo
     * @return
     */
    AddOrEditAssociateQuestionsResVo addOrEditAssociateQuestions(AddOrEditAssociateQuestionsReqVo reqVo);

    /**
     * 获取关联的试题
     *
     * @param reqVo
     * @return
     */
    FindAssociateQuestionsResVo findAssociateQuestions(FindAssociateQuestionsReqVo reqVo);

}