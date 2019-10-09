package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.entity.Topics;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.CreateTopicsReqVo;
import org.javamaster.b2c.core.model.vo.CreateTopicsResVo;
import org.javamaster.b2c.core.model.vo.DelTopicsReqVo;
import org.javamaster.b2c.core.model.vo.EditTopicsReqVo;
import org.javamaster.b2c.core.model.vo.FindTopicsListReqVo;
import org.javamaster.b2c.core.service.TopicsService;
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
 * 主题管理
 *
 * @author yudong
 * @date 2019/08/07
 */
@RestController
@RequestMapping("/core/topics")
public class TopicsController {

    @Autowired
    private TopicsService topicsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findTopicsList")
    public Result<List<Topics>> findTopicsList(@Validated @RequestBody FindTopicsListReqVo reqVo) {
        PageInfo<Topics> resVo = topicsService.findTopicsList(reqVo);
        return new Result<>(resVo.getList(), resVo.getTotal());
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/createTopics")
    public Result<CreateTopicsResVo> createTopics(@Validated @RequestBody CreateTopicsReqVo reqVo,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        CreateTopicsResVo resVo = topicsService.createTopics(reqVo, userDetails);
        return new Result<>(resVo);
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/editTopics")
    public Result<Integer> editTopics(@Validated @RequestBody EditTopicsReqVo reqVo) {
        Integer resVo = topicsService.editTopics(reqVo);
        return new Result<>(resVo);
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/delTopics")
    public Result<Integer> delTopics(@Validated @RequestBody DelTopicsReqVo reqVo) {
        Integer resVo = topicsService.delTopics(reqVo);
        return new Result<>(resVo);
    }

}