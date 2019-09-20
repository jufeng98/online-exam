package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Messages;
import org.javamaster.b2c.core.entity.MessagesExample;
import org.javamaster.b2c.core.enums.ReadEnum;
import org.javamaster.b2c.core.mapper.MessagesMapper;
import org.javamaster.b2c.core.model.vo.FindMessagesListReqVo;
import org.javamaster.b2c.core.model.vo.MarkMessagesReqVo;
import org.javamaster.b2c.core.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yudong
 * @date 2019/09/20
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    private MessagesMapper messagesMapper;

    @Override
    public Boolean hasUnreadMessages(UserDetails userDetails) {
        MessagesExample messagesExample = new MessagesExample();
        messagesExample.createCriteria()
                .andUsernameEqualTo(userDetails.getUsername())
                .andAlreadyReadEqualTo((byte) ReadEnum.UNREAD.getCode());
        return messagesMapper.selectByExample(messagesExample).size() > 0;
    }

    @Override
    public Pair<List<Messages>, Long> findMessagesList(FindMessagesListReqVo reqVo, UserDetails userDetails) {
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "create_time desc");
        MessagesExample messagesExample = new MessagesExample();
        messagesExample.createCriteria()
                .andUsernameEqualTo(userDetails.getUsername());
        List<Messages> messagesList = messagesMapper.selectByExample(messagesExample);
        PageInfo<Messages> pageInfo = new PageInfo<>(messagesList);
        return Pair.of(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Integer markMessages(MarkMessagesReqVo reqVo, UserDetails userDetails) {
        MessagesExample messagesExample = new MessagesExample();
        MessagesExample.Criteria criteria = messagesExample.createCriteria()
                .andUsernameEqualTo(userDetails.getUsername())
                .andAlreadyReadEqualTo((byte) ReadEnum.UNREAD.getCode());
        if (reqVo.getId() != null) {
            criteria.andIdEqualTo(reqVo.getId());
        }
        Messages messages = new Messages();
        messages.setAlreadyRead((byte) ReadEnum.READ.getCode());
        return messagesMapper.updateByExampleSelective(messages, messagesExample);
    }

}