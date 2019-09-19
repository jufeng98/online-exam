package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.javamaster.b2c.core.entity.Exams;
import org.javamaster.b2c.core.entity.ExamsExample;
import org.javamaster.b2c.core.entity.ExamsQuestions;
import org.javamaster.b2c.core.entity.ExamsQuestionsExample;
import org.javamaster.b2c.core.helper.CodeHelper;
import org.javamaster.b2c.core.mapper.ExamsMapper;
import org.javamaster.b2c.core.mapper.ExamsQuestionsMapper;
import org.javamaster.b2c.core.model.vo.AddOrEditAssociateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.AddOrEditAssociateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.CreateExamsReqVo;
import org.javamaster.b2c.core.model.vo.CreateExamsResVo;
import org.javamaster.b2c.core.model.vo.DelExamsReqVo;
import org.javamaster.b2c.core.model.vo.EditExamsReqVo;
import org.javamaster.b2c.core.model.vo.FindAssociateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.FindAssociateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.FindExamsListReqVo;
import org.javamaster.b2c.core.service.ExamsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yudong
 * @date 2019/08/11
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ExamsServiceImpl implements ExamsService {
    @Autowired
    private ExamsMapper examsMapper;
    @Autowired
    private ExamsQuestionsMapper examsQuestionsMapper;
    @Autowired
    private CodeHelper codeHelper;

    @Override
    public PageInfo<Exams> findExamsList(FindExamsListReqVo reqVo) {
        ExamsExample examsExample = new ExamsExample();
        ExamsExample.Criteria criteria = examsExample.createCriteria();
        if (StringUtils.isNotBlank(reqVo.getExamsName())) {
            criteria.andExamsNameEqualTo(reqVo.getExamsName() + "%");
        }
        if (StringUtils.isNotBlank(reqVo.getExamsCode())) {
            criteria.andExamsCodeEqualTo(reqVo.getExamsCode());
        }
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "create_time desc");
        List<Exams> exams = examsMapper.selectByExample(examsExample);
        PageInfo<Exams> pageInfo = new PageInfo<>(exams);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateExamsResVo createExams(CreateExamsReqVo reqVo, UserDetails userDetails) {
        String code = codeHelper.generateCode("exams_code", "exams", "E");
        Exams exams = new Exams();
        BeanUtils.copyProperties(reqVo.getExamsForm(), exams);
        exams.setExamsCode(code);
        exams.setCreateUsername(userDetails.getUsername());
        examsMapper.insertSelective(exams);
        CreateExamsResVo createExamsResVo = new CreateExamsResVo();
        createExamsResVo.setExams(exams);
        return createExamsResVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer editExams(EditExamsReqVo reqVo) {
        Exams exams = new Exams();
        BeanUtils.copyProperties(reqVo.getExamsForm(), exams);
        exams.setExamsCode(null);
        return examsMapper.updateByPrimaryKeySelective(exams);
    }

    @Override
    public Integer delExams(DelExamsReqVo reqVo) {
        return examsMapper.deleteByPrimaryKey(reqVo.getId());
    }

    @Override
    public AddOrEditAssociateQuestionsResVo addOrEditAssociateQuestions(AddOrEditAssociateQuestionsReqVo reqVo) {
        ExamsQuestionsExample examsQuestionsExample = new ExamsQuestionsExample();
        ExamsQuestionsExample.Criteria criteria = examsQuestionsExample.createCriteria();
        criteria.andExamsCodeEqualTo(reqVo.getExamsCode());
        examsQuestionsMapper.deleteByExample(examsQuestionsExample);

        List<Integer> affects = reqVo.getQuestionsCodes().stream().map(questionsCode -> {
            ExamsQuestions examsQuestions = new ExamsQuestions();
            examsQuestions.setExamsCode(reqVo.getExamsCode());
            examsQuestions.setQuestionsCode(questionsCode);
            return examsQuestionsMapper.insertSelective(examsQuestions);
        }).collect(Collectors.toList());

        AddOrEditAssociateQuestionsResVo resVo = new AddOrEditAssociateQuestionsResVo();
        resVo.setAffects(affects);
        return resVo;
    }

    @Override
    public FindAssociateQuestionsResVo findAssociateQuestions(FindAssociateQuestionsReqVo reqVo) {
        ExamsQuestionsExample examsQuestionsExample = new ExamsQuestionsExample();
        ExamsQuestionsExample.Criteria criteria = examsQuestionsExample.createCriteria();
        criteria.andExamsCodeEqualTo(reqVo.getExamsCode());
        List<ExamsQuestions> examsQuestions = examsQuestionsMapper.selectByExample(examsQuestionsExample);
        List<String> examsQuestionsCodes = examsQuestions.stream()
                .map(ExamsQuestions::getQuestionsCode)
                .collect(Collectors.toList());
        FindAssociateQuestionsResVo findAssociateQuestionsResVo = new FindAssociateQuestionsResVo();
        findAssociateQuestionsResVo.setExamsQuestionsCodes(examsQuestionsCodes);
        return findAssociateQuestionsResVo;
    }

}