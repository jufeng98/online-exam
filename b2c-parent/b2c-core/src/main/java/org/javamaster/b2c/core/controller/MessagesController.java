package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.entity.Messages;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.FindMessagesListReqVo;
import org.javamaster.b2c.core.model.vo.MarkMessagesReqVo;
import org.javamaster.b2c.core.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息管理
 *
 * @author yudong
 * @date 2019/09/20
 */
@RestController
@RequestMapping("/core/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @PostMapping("/hasUnreadMessages")
    public Result<Boolean> hasUnreadMessages(@AuthenticationPrincipal UserDetails userDetails) {
        Boolean resVo = messagesService.hasUnreadMessages(userDetails);
        return new Result<>(resVo);
    }

    @PostMapping("/findMessagesList")
    public Result<List<Messages>> findMessagesList(@Validated @RequestBody FindMessagesListReqVo reqVo,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        Pair<List<Messages>, Long> resVo = messagesService.findMessagesList(reqVo, userDetails);
        return new Result<>(resVo.getFirst(), resVo.getSecond());
    }

    @PostMapping("/markMessages")
    public Result<Integer> markMessages(@RequestBody MarkMessagesReqVo reqVo,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        Integer resVo = messagesService.markMessages(reqVo, userDetails);
        return new Result<>(resVo);
    }

}