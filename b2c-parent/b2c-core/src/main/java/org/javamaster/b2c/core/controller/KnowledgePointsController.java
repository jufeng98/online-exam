package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.entity.KnowledgePoints;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.CreateKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.CreateKnowledgePointsResVo;
import org.javamaster.b2c.core.model.vo.DelKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.EditKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.FindKnowledgePointsListReqVo;
import org.javamaster.b2c.core.model.vo.KnowledgesQuestionNumVo;
import org.javamaster.b2c.core.service.KnowledgePointsService;
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

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 知识点管理
 *
 * @author yudong
 * @date 2019/08/07
 */
@Validated
@RestController
@RequestMapping("/core/knowledgePoints")
public class KnowledgePointsController {

    @Autowired
    private KnowledgePointsService knowledgePointsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findKnowledgePointsList")
    public Result<List<KnowledgePoints>> findKnowledgePointsList(@Validated @RequestBody FindKnowledgePointsListReqVo reqVo) {
        PageInfo<KnowledgePoints> resVo = knowledgePointsService.findKnowledgePointsList(reqVo);
        return new Result<>(resVo.getList(), resVo.getTotal());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findKnowledgesQuestionNum")
    public Result<List<KnowledgesQuestionNumVo>> findKnowledgesQuestionNum(@NotBlank String sectionsCode) {
        List<KnowledgesQuestionNumVo> resVo = knowledgePointsService.findKnowledgesQuestionNum(sectionsCode);
        return new Result<>(resVo);
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/createKnowledgePoints")
    public Result<CreateKnowledgePointsResVo> createKnowledgePoints(@Validated @RequestBody CreateKnowledgePointsReqVo reqVo,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        CreateKnowledgePointsResVo resVo = knowledgePointsService.createKnowledgePoints(reqVo, userDetails);
        return new Result<>(resVo);
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/editKnowledgePoints")
    public Result<Integer> editKnowledgePoints(@Validated @RequestBody EditKnowledgePointsReqVo reqVo) {
        Integer resVo = knowledgePointsService.editKnowledgePoints(reqVo);
        return new Result<>(resVo);
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/delKnowledgePoints")
    public Result<Integer> delKnowledgePoints(@Validated @RequestBody DelKnowledgePointsReqVo reqVo) {
        Integer resVo = knowledgePointsService.delKnowledgePoints(reqVo);
        return new Result<>(resVo);
    }

}