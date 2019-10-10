package org.javamaster.b2c.core.controller

import org.javamaster.b2c.core.entity.Discussions
import org.javamaster.b2c.core.model.Result
import org.javamaster.b2c.core.model.vo.CreateDiscussionsReqVo
import org.javamaster.b2c.core.model.vo.FindDiscussionsListReqVo
import org.javamaster.b2c.core.service.DiscussionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 讨论管理
 *
 * @author yudong
 * @date 2019/10/9
 */
@RestController
@RequestMapping("/core/discussions")
class DiscussionsController {

    @Autowired
    private lateinit var discussionsService: DiscussionsService

    @PostMapping("/createDiscussions")
    fun createDiscussions(@RequestBody @Validated reqVo: CreateDiscussionsReqVo,
                          @AuthenticationPrincipal userDetails: UserDetails): Result<Int> {
        return Result(discussionsService.createDiscussions(reqVo, userDetails))
    }

    @PostMapping("/findDiscussionsList")
    fun findDiscussionsList(@RequestBody @Validated reqVo: FindDiscussionsListReqVo,
                            @AuthenticationPrincipal userDetails: UserDetails): Result<List<Discussions>> {
        val pageInfo = discussionsService.findDiscussionsList(reqVo, userDetails)
        return Result(pageInfo.list, pageInfo.total)
    }

}