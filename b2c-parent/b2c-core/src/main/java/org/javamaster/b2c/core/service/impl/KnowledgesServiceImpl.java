package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.javamaster.b2c.core.entity.Knowledges;
import org.javamaster.b2c.core.entity.KnowledgesExample;
import org.javamaster.b2c.core.helper.CodeHelper;
import org.javamaster.b2c.core.mapper.KnowledgesMapper;
import org.javamaster.b2c.core.model.vo.CreateKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.CreateKnowledgesResVo;
import org.javamaster.b2c.core.model.vo.DelKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.EditKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.FindKnowledgesListReqVo;
import org.javamaster.b2c.core.service.KnowledgesService;
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
public class KnowledgesServiceImpl implements KnowledgesService {

    @Autowired
    private KnowledgesMapper knowledgesMapper;
    @Autowired
    private CodeHelper codeHelper;

    @Override
    public PageInfo<Knowledges> findKnowledgesList(FindKnowledgesListReqVo reqVo) {
        KnowledgesExample knowledgesExample = new KnowledgesExample();
        KnowledgesExample.Criteria criteria = knowledgesExample.createCriteria();
        if (StringUtils.isNotBlank(reqVo.getKnowledgesForm().getKnowledgesName())) {
            criteria.andKnowledgesNameLike(reqVo.getKnowledgesForm().getKnowledgesName() + "%");
        }
        if (StringUtils.isNotBlank(reqVo.getKnowledgesForm().getSectionsCode())) {
            criteria.andSectionsCodeEqualTo(reqVo.getKnowledgesForm().getSectionsCode());
        }
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "sort_order,create_time desc");
        List<Knowledges> topics = knowledgesMapper.selectByExample(knowledgesExample);
        PageInfo<Knowledges> pageInfo = new PageInfo<>(topics);
        return pageInfo;
    }

    @Override
    public CreateKnowledgesResVo createKnowledges(CreateKnowledgesReqVo reqVo, UserDetails userDetails) {
        Knowledges knowledges = new Knowledges();
        String code = codeHelper.generateCode("knowledges_code", "knowledges", "K");
        knowledges.setKnowledgesCode(code);
        knowledges.setKnowledgesName(reqVo.getKnowledgesForm().getKnowledgesName());
        knowledges.setCreateUsename(userDetails.getUsername());
        knowledges.setSectionsCode(reqVo.getKnowledgesForm().getSectionsCode());
        knowledges.setSortOrder(reqVo.getKnowledgesForm().getSortOrder());
        knowledgesMapper.insertSelective(knowledges);
        CreateKnowledgesResVo createKnowledgesResVo = new CreateKnowledgesResVo();
        createKnowledgesResVo.setKnowledges(knowledges);
        return createKnowledgesResVo;
    }

    @Override
    public Integer editKnowledges(EditKnowledgesReqVo reqVo) {
        Knowledges knowledges = new Knowledges();
        knowledges.setId(reqVo.getKnowledgesForm().getId());
        knowledges.setKnowledgesName(reqVo.getKnowledgesForm().getKnowledgesName());
        knowledges.setSectionsCode(reqVo.getKnowledgesForm().getSectionsCode());
        knowledges.setSortOrder(reqVo.getKnowledgesForm().getSortOrder());
        return knowledgesMapper.updateByPrimaryKeySelective(knowledges);
    }

    @Override
    public Integer delKnowledges(DelKnowledgesReqVo reqVo) {
        return knowledgesMapper.deleteByPrimaryKey(reqVo.getId());
    }

}