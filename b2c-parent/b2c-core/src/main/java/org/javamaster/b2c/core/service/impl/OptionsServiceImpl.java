package org.javamaster.b2c.core.service.impl;

import org.javamaster.b2c.core.entity.Options;
import org.javamaster.b2c.core.entity.OptionsExample;
import org.javamaster.b2c.core.mapper.OptionsMapper;
import org.javamaster.b2c.core.model.vo.FindAssociateOptionsReqVo;
import org.javamaster.b2c.core.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yudong
 * @date 2019/09/14
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class OptionsServiceImpl implements OptionsService {

    @Autowired
    private OptionsMapper optionsMapper;

    @Override
    public List<Options> findAssociateOptions(FindAssociateOptionsReqVo reqVo) {
        OptionsExample optionsExample = new OptionsExample();
        optionsExample.createCriteria().andQuestionsCodeEqualTo(reqVo.getQuestionsCode());
        return optionsMapper.selectByExample(optionsExample);
    }

}