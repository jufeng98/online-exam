package org.javamaster.b2c.core.service.impl;

import static java.util.stream.Collectors.groupingBy;
import org.javamaster.b2c.core.entity.LearnsRecord;
import org.javamaster.b2c.core.entity.LearnsRecordExample;
import org.javamaster.b2c.core.mapper.ExtMapper;
import org.javamaster.b2c.core.mapper.LearnsRecordMapper;
import org.javamaster.b2c.core.model.vo.LearnsRecordVo;
import org.javamaster.b2c.core.model.vo.SaveLearnsReqVo;
import org.javamaster.b2c.core.model.vo.SectionsProgressVo;
import org.javamaster.b2c.core.model.vo.TopicsProgressVo;
import org.javamaster.b2c.core.service.LearnsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yudong
 * @date 2019/09/18
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class LearnsServiceImpl implements LearnsService {

    @Autowired
    private LearnsRecordMapper learnsRecordMapper;
    @Autowired
    private ExtMapper extMapper;

    @Override
    public Integer saveLearns(SaveLearnsReqVo reqVo) {
        if (findLearnRecord(reqVo.getLearnsRecordVo()) != null) {
            return 0;
        }
        LearnsRecord learnsRecord = new LearnsRecord();
        BeanUtils.copyProperties(reqVo.getLearnsRecordVo(), learnsRecord);
        return learnsRecordMapper.insertSelective(learnsRecord);
    }

    @Override
    public List<LearnsRecord> findLearnsByUsername(String username) {
        LearnsRecordExample example = new LearnsRecordExample();
        example.createCriteria().andUsernameEqualTo(username);
        return learnsRecordMapper.selectByExample(example);
    }

    private LearnsRecord findLearnRecord(LearnsRecordVo learnsRecordVo) {
        LearnsRecordExample example = new LearnsRecordExample();
        example.createCriteria()
                .andUsernameEqualTo(learnsRecordVo.getUsername())
                .andTopicsCodeEqualTo(learnsRecordVo.getTopicsCode())
                .andSectionsCodeEqualTo(learnsRecordVo.getSectionsCode())
                .andKnowledgesCodeEqualTo(learnsRecordVo.getKnowledgesCode())
                .andKnowledgePointsCodeEqualTo(learnsRecordVo.getKnowledgePointsCode());
        List<LearnsRecord> learnsRecords = learnsRecordMapper.selectByExample(example);
        if (learnsRecords.isEmpty()) {
            return null;
        } else {
            return learnsRecords.get(0);
        }
    }

    @Override
    public List<TopicsProgressVo> findTopicsProgress(String username) {
        List<LearnsRecord> learnsRecords = findLearnsByUsername(username);
        Map<String, List<LearnsRecord>> map = learnsRecords.stream().collect(groupingBy(LearnsRecord::getTopicsCode));
        return map.entrySet().stream().map(entry -> {
            String topicsCode = entry.getKey();
            int size = extMapper.findKnowledgePointsCount(topicsCode, "");
            int learnSize = entry.getValue().size();
            int progress = (int) (1.0 * learnSize / size * 100);
            TopicsProgressVo topicsProgressVo = new TopicsProgressVo();
            topicsProgressVo.setUsername(username);
            topicsProgressVo.setTopicsCode(topicsCode);
            topicsProgressVo.setProgress(progress);
            return topicsProgressVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SectionsProgressVo> findSectionsProgress(String username) {
        List<LearnsRecord> learnsRecords = findLearnsByUsername(username);
        Map<String, List<LearnsRecord>> map = learnsRecords.stream().collect(groupingBy(LearnsRecord::getSectionsCode));
        return map.entrySet().stream().map(entry -> {
            String sectionsCode = entry.getKey();
            int size = extMapper.findKnowledgePointsCount("", sectionsCode);
            int learnSize = entry.getValue().size();
            int progress = (int) (1.0 * learnSize / size * 100);
            SectionsProgressVo sectionsProgressVo = new SectionsProgressVo();
            sectionsProgressVo.setUsername(username);
            sectionsProgressVo.setSectionsCode(sectionsCode);
            sectionsProgressVo.setProgress(progress);
            return sectionsProgressVo;
        }).collect(Collectors.toList());
    }

}