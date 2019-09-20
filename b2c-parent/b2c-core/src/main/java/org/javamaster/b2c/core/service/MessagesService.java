package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.entity.Messages;
import org.javamaster.b2c.core.model.vo.FindMessagesListReqVo;
import org.javamaster.b2c.core.model.vo.MarkMessagesReqVo;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 消息管理
 *
 * @author yudong
 * @date 2019/09/20
 */
public interface MessagesService {

    /**
     * 是否有未读消息
     *
     * @param userDetails
     * @return
     */
    Boolean hasUnreadMessages(UserDetails userDetails);

    /**
     * 获取消息列表
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    Pair<List<Messages>, Long> findMessagesList(FindMessagesListReqVo reqVo, UserDetails userDetails);

    /**
     * 标记消息的阅读情况
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    Integer markMessages(MarkMessagesReqVo reqVo, UserDetails userDetails);

}