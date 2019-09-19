package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.model.vo.SubmitAnswersReqVo;
import org.javamaster.b2c.core.model.vo.SubmitAnswersResVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 考试记录管理
 *
 * @author yudong
 * @date 2019/09/19
 */
public interface ExamsRecordService {

    /**
     * 提交考试答案
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    SubmitAnswersResVo submitAnswers(SubmitAnswersReqVo reqVo, UserDetails userDetails);

}