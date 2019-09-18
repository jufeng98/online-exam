package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Questions;
import org.javamaster.b2c.core.model.vo.BatchImportQuestionsResVo;
import org.javamaster.b2c.core.model.vo.CreateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.CreateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.DelQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.EditQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.ExamQuestionsVo;
import org.javamaster.b2c.core.model.vo.FindOptionsListReqVo;
import org.javamaster.b2c.core.model.vo.FindOptionsListResVo;
import org.javamaster.b2c.core.model.vo.FindQuestionsListReqVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 试题管理
 *
 * @author yudong
 * @date 2019/08/08
 */
public interface QuestionsService {

    /**
     * 获取试题列表
     *
     * @param reqVo
     * @return
     */
    PageInfo<Questions> findQuestionsList(FindQuestionsListReqVo reqVo);

    /**
     * 创建试题
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    CreateQuestionsResVo createQuestions(CreateQuestionsReqVo reqVo, UserDetails userDetails);

    /**
     * 编辑试题
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    Integer editQuestions(EditQuestionsReqVo reqVo, UserDetails userDetails);

    /**
     * 删除试题
     *
     * @param reqVo
     * @return
     */
    Integer delQuestions(DelQuestionsReqVo reqVo);

    /**
     * 下载试题导入模板
     *
     * @return
     */
    byte[] downloadQuestionsTemplate();

    /**
     * 批量导入试题
     *
     * @param multipartFile
     * @param userDetails
     * @return
     */
    BatchImportQuestionsResVo batchImportQuestions(MultipartFile multipartFile, UserDetails userDetails);

    /**
     * 获取试题选项
     *
     * @param reqVo
     * @return
     */
    FindOptionsListResVo findOptionsList(FindOptionsListReqVo reqVo);

    /**
     * 获取试题信息
     *
     * @param examsCode
     * @return
     */
    List<ExamQuestionsVo> findQuestionsByExamsCode(String examsCode);

}