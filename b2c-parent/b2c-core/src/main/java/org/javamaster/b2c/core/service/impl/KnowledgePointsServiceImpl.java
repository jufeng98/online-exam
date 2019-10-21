package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import org.apache.commons.lang3.StringUtils;
import org.javamaster.b2c.core.entity.KnowledgePoints;
import org.javamaster.b2c.core.entity.KnowledgePointsExample;
import org.javamaster.b2c.core.entity.Knowledges;
import org.javamaster.b2c.core.entity.KnowledgesExample;
import org.javamaster.b2c.core.helper.CodeHelper;
import org.javamaster.b2c.core.mapper.KnowledgePointsMapper;
import org.javamaster.b2c.core.mapper.KnowledgesMapper;
import org.javamaster.b2c.core.model.vo.CreateKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.CreateKnowledgePointsResVo;
import org.javamaster.b2c.core.model.vo.DelKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.EditKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.FindKnowledgePointsListReqVo;
import org.javamaster.b2c.core.model.vo.KnowledgesQuestionNumVo;
import org.javamaster.b2c.core.service.KnowledgePointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yudong
 * @date 2019/08/07
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class KnowledgePointsServiceImpl implements KnowledgePointsService {

    @Autowired
    private KnowledgePointsMapper knowledgePointsMapper;
    @Autowired
    private KnowledgesMapper knowledgesMapper;
    @Autowired
    private CodeHelper codeHelper;

    @Override
    public PageInfo<KnowledgePoints> findKnowledgePointsList(FindKnowledgePointsListReqVo reqVo) {
        KnowledgePointsExample knowledgePointsExample = new KnowledgePointsExample();
        KnowledgePointsExample.Criteria criteria = knowledgePointsExample.createCriteria();
        if (StringUtils.isNotBlank(reqVo.getKnowledgePointsForm().getKnowledgesCode())) {
            criteria.andKnowledgesCodeEqualTo(reqVo.getKnowledgePointsForm().getKnowledgesCode());
        }
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "sort_order,create_time desc");
        List<KnowledgePoints> topics = knowledgePointsMapper.selectByExample(knowledgePointsExample);
        return new PageInfo<>(topics);
    }

    @Override
    public CreateKnowledgePointsResVo createKnowledgePoints(CreateKnowledgePointsReqVo reqVo, UserDetails userDetails) {
        KnowledgePoints knowledgePoints = new KnowledgePoints();
        String code = codeHelper.generateCode("knowledge_points_code", "knowledge_points", "KP");
        knowledgePoints.setKnowledgePointsCode(code);
        knowledgePoints.setCreateUsename(userDetails.getUsername());
        knowledgePoints.setKnowledgePointsContent(reqVo.getKnowledgePointsForm().getKnowledgePointsContent());
        knowledgePoints.setKnowledgesCode(reqVo.getKnowledgePointsForm().getKnowledgesCode());
        knowledgePoints.setQuestionsCode(reqVo.getKnowledgePointsForm().getQuestionsCode());
        knowledgePoints.setSortOrder(reqVo.getKnowledgePointsForm().getSortOrder());
        knowledgePointsMapper.insertSelective(knowledgePoints);
        CreateKnowledgePointsResVo createKnowledgePointsResVo = new CreateKnowledgePointsResVo();
        createKnowledgePointsResVo.setKnowledgePointsForm(knowledgePoints);
        return createKnowledgePointsResVo;
    }

    @Override
    public Integer editKnowledgePoints(EditKnowledgePointsReqVo reqVo) {
        KnowledgePoints knowledgePoints = new KnowledgePoints();
        knowledgePoints.setId(reqVo.getKnowledgePointsForm().getId());
        knowledgePoints.setKnowledgePointsContent(reqVo.getKnowledgePointsForm().getKnowledgePointsContent());
        knowledgePoints.setSortOrder(reqVo.getKnowledgePointsForm().getSortOrder());
        knowledgePoints.setKnowledgesCode(reqVo.getKnowledgePointsForm().getKnowledgesCode());
        knowledgePoints.setQuestionsCode(reqVo.getKnowledgePointsForm().getQuestionsCode());
        return knowledgePointsMapper.updateByPrimaryKeySelective(knowledgePoints);
    }

    @Override
    public Integer delKnowledgePoints(DelKnowledgePointsReqVo reqVo) {
        return knowledgePointsMapper.deleteByPrimaryKey(reqVo.getId());
    }

    @Override
    public List<KnowledgesQuestionNumVo> findKnowledgesQuestionNum(String sectionsCode) {
        KnowledgesExample knowledgesExample = new KnowledgesExample();
        knowledgesExample.createCriteria().andSectionsCodeEqualTo(sectionsCode);
        List<String> knowledgesCodes = knowledgesMapper.selectByExample(knowledgesExample).stream()
                .map(Knowledges::getKnowledgesCode).collect(toList());

        KnowledgePointsExample knowledgePointsExample = new KnowledgePointsExample();
        knowledgePointsExample.createCriteria().andKnowledgesCodeIn(knowledgesCodes);
        List<KnowledgePoints> knowledgePointsList = knowledgePointsMapper.selectByExample(knowledgePointsExample);
        Map<String, List<String>> mapCodes = knowledgePointsList.stream()
                .collect(groupingBy(KnowledgePoints::getKnowledgesCode, mapping(KnowledgePoints::getQuestionsCode, toList())));
        List<KnowledgesQuestionNumVo> list = new ArrayList<>(mapCodes.size());
        for (Map.Entry<String, List<String>> stringListEntry : mapCodes.entrySet()) {
            Integer questionsNum = stringListEntry.getValue().stream()
                    .filter(StringUtils::isNotBlank)
                    .collect(toList())
                    .size();
            KnowledgesQuestionNumVo numVo = new KnowledgesQuestionNumVo();
            numVo.setSectionsCode(sectionsCode);
            numVo.setKnowledgesCode(stringListEntry.getKey());
            numVo.setQuestionsNum(questionsNum);
            list.add(numVo);
        }
        return list;
    }

}