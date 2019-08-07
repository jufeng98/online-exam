package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.javamaster.b2c.core.entity.Topics;
import org.javamaster.b2c.core.entity.TopicsExample;
import org.javamaster.b2c.core.helper.CodeHelper;
import org.javamaster.b2c.core.mapper.TopicsMapper;
import org.javamaster.b2c.core.model.vo.CreateTopicsReqVo;
import org.javamaster.b2c.core.model.vo.CreateTopicsResVo;
import org.javamaster.b2c.core.model.vo.DelTopicsReqVo;
import org.javamaster.b2c.core.model.vo.EditTopicsReqVo;
import org.javamaster.b2c.core.model.vo.FindTopicsListReqVo;
import org.javamaster.b2c.core.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yudong
 * @date 2019/08/07
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class TopicsServiceImpl implements TopicsService {

    @Autowired
    private TopicsMapper topicsMapper;
    @Autowired
    private CodeHelper codeHelper;

    @Override
    public PageInfo<Topics> findTopicsList(FindTopicsListReqVo reqVo) {
        TopicsExample topicsExample = new TopicsExample();
        TopicsExample.Criteria criteria = topicsExample.createCriteria();
        if (StringUtils.isNotBlank(reqVo.getTopicsForm().getTopicsName())) {
            criteria.andTopicsNameLike(reqVo.getTopicsForm().getTopicsName() + "%");
        }
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "create_time desc");
        List<Topics> topics = topicsMapper.selectByExampleWithBLOBs(topicsExample);
        PageInfo<Topics> pageInfo = new PageInfo<>(topics);
        return pageInfo;
    }

    @Override
    public CreateTopicsResVo createTopics(CreateTopicsReqVo reqVo, UserDetails userDetails) {
        Topics topics = new Topics();
        topics.setTopicsName(reqVo.getTopicsForm().getTopicsName());
        topics.setTopicsCoverImage(reqVo.getTopicsForm().getTopicsCoverImage());
        String code = codeHelper.generateCode("topics_code", "topics", "T");
        topics.setTopicsCode(code);
        topics.setCreateUsename(userDetails.getUsername());
        topicsMapper.insertSelective(topics);
        CreateTopicsResVo createTopicsResVo = new CreateTopicsResVo();
        createTopicsResVo.setTopics(topics);
        return createTopicsResVo;
    }

    @Override
    public Integer editTopics(EditTopicsReqVo reqVo) {
        Topics topics = new Topics();
        topics.setId(reqVo.getTopicsForm().getId());
        topics.setTopicsCoverImage(reqVo.getTopicsForm().getTopicsCoverImage());
        topics.setTopicsName(reqVo.getTopicsForm().getTopicsName());
        return topicsMapper.updateByPrimaryKeySelective(topics);
    }

    @Override
    public Integer delTopics(DelTopicsReqVo reqVo) {
        return topicsMapper.deleteByPrimaryKey(reqVo.getId());
    }

}