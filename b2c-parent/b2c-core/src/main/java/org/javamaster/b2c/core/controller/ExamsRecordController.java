package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.annos.AopLock;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.SubmitAnswersReqVo;
import org.javamaster.b2c.core.model.vo.SubmitAnswersResVo;
import org.javamaster.b2c.core.service.ExamsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考试记录管理
 *
 * @author yudong
 * @date 2019/09/19
 */
@RestController
@RequestMapping("/core/examsRecord")
public class ExamsRecordController {

    @Autowired
    private ExamsRecordService examsRecordService;

    @AopLock
    @PostMapping("/submitAnswers")
    public Result<SubmitAnswersResVo> submitAnswers(@Validated @RequestBody SubmitAnswersReqVo reqVo,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        SubmitAnswersResVo resVo = examsRecordService.submitAnswers(reqVo, userDetails);
        return new Result<>(resVo);
    }

}