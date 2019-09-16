package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.entity.Exams;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.AddOrEditAssociateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.AddOrEditAssociateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.CreateExamsReqVo;
import org.javamaster.b2c.core.model.vo.CreateExamsResVo;
import org.javamaster.b2c.core.model.vo.DelExamsReqVo;
import org.javamaster.b2c.core.model.vo.EditExamsReqVo;
import org.javamaster.b2c.core.model.vo.FindAssociateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.FindAssociateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.FindExamsListReqVo;
import org.javamaster.b2c.core.service.ExamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 考试管理
 *
 * @author yudong
 * @date 2019/08/11
 */
@RestController
@RequestMapping("/core/exams")
public class ExamsController {

    @Autowired
    private ExamsService examsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findExamsList")
    public Result<List<Exams>> findExamsList(@Validated @RequestBody FindExamsListReqVo reqVo) {
        PageInfo<Exams> resVo = examsService.findExamsList(reqVo);
        Result<List<Exams>> result = new Result(resVo.getList(), resVo.getTotal());
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/createExams")
    public Result<CreateExamsResVo> createExams(@Validated @RequestBody CreateExamsReqVo reqVo,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        CreateExamsResVo resVo = examsService.createExams(reqVo, userDetails);
        Result<CreateExamsResVo> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/editExams")
    public Result<Integer> editExams(@Validated @RequestBody EditExamsReqVo reqVo) {
        Integer resVo = examsService.editExams(reqVo);
        Result<Integer> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/delExams")
    public Result<Integer> delExams(@Validated @RequestBody DelExamsReqVo reqVo) {
        Integer resVo = examsService.delExams(reqVo);
        Result<Integer> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/addOrEditAssociateQuestions")
    public Result<AddOrEditAssociateQuestionsResVo> addOrEditAssociateQuestions(
            @Validated @RequestBody AddOrEditAssociateQuestionsReqVo reqVo) {
        AddOrEditAssociateQuestionsResVo resVo = examsService.addOrEditAssociateQuestions(reqVo);
        Result<AddOrEditAssociateQuestionsResVo> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/findAssociateQuestions")
    public Result<FindAssociateQuestionsResVo> findAssociateQuestions(@Validated @RequestBody FindAssociateQuestionsReqVo reqVo) {
        FindAssociateQuestionsResVo resVo = examsService.findAssociateQuestions(reqVo);
        Result<FindAssociateQuestionsResVo> result = new Result(resVo);
        return result;
    }

}