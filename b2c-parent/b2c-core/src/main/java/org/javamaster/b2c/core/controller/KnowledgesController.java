package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.entity.Knowledges;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.CreateKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.CreateKnowledgesResVo;
import org.javamaster.b2c.core.model.vo.DelKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.EditKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.FindKnowledgesListReqVo;
import org.javamaster.b2c.core.service.KnowledgesService;
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
 * 知识管理
 *
 * @author yudong
 * @date 2019/08/07
 */
@RestController
@RequestMapping("/core/knowledges")
public class KnowledgesController {

    @Autowired
    private KnowledgesService knowledgesService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findKnowledgesList")
    public Result<List<Knowledges>> findKnowledgesList(@Validated @RequestBody FindKnowledgesListReqVo reqVo) {
        PageInfo<Knowledges> resVo = knowledgesService.findKnowledgesList(reqVo);
        Result<List<Knowledges>> result = new Result(resVo.getList(), resVo.getTotal());
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/createKnowledges")
    public Result<CreateKnowledgesResVo> createKnowledges(@Validated @RequestBody CreateKnowledgesReqVo reqVo,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        CreateKnowledgesResVo resVo = knowledgesService.createKnowledges(reqVo, userDetails);
        Result<CreateKnowledgesResVo> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/editKnowledges")
    public Result<Integer> editKnowledges(@Validated @RequestBody EditKnowledgesReqVo reqVo) {
        Integer resVo = knowledgesService.editKnowledges(reqVo);
        Result<Integer> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/delKnowledges")
    public Result<Integer> delKnowledges(@Validated @RequestBody DelKnowledgesReqVo reqVo) {
        Integer resVo = knowledgesService.delKnowledges(reqVo);
        Result<Integer> result = new Result(resVo);
        return result;
    }

}