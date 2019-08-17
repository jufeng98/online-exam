package org.javamaster.b2c.core.service.impl;

import cn.com.bluemoon.handypoi.excel.enums.ExcelType;
import cn.com.bluemoon.handypoi.excel.resolve.ExcelReader;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.javamaster.b2c.core.entity.Options;
import org.javamaster.b2c.core.entity.OptionsExample;
import org.javamaster.b2c.core.entity.Questions;
import org.javamaster.b2c.core.entity.QuestionsExample;
import org.javamaster.b2c.core.enums.QuestionsTypeEnum;
import org.javamaster.b2c.core.helper.CodeHelper;
import org.javamaster.b2c.core.mapper.OptionsMapper;
import org.javamaster.b2c.core.mapper.QuestionsMapper;
import org.javamaster.b2c.core.model.vo.BatchImportQuestionsResVo;
import org.javamaster.b2c.core.model.vo.CreateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.CreateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.DelQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.EditQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.FindOptionsListReqVo;
import org.javamaster.b2c.core.model.vo.FindOptionsListResVo;
import org.javamaster.b2c.core.model.vo.FindQuestionsListReqVo;
import org.javamaster.b2c.core.model.vo.QuestionsVo;
import org.javamaster.b2c.core.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author yudong
 * @date 2019/08/08
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private QuestionsMapper questionsMapper;
    @Autowired
    private OptionsMapper optionsMapper;
    @Autowired
    private CodeHelper codeHelper;

    private static final String JUDGE = "对;错";
    private Map<String, Integer> map = new HashMap<>();
    private Map<String, Integer> map1 = new HashMap<>();

    {
        IntStream.rangeClosed(1, 10).forEach(i -> map.put((char) (64 + i) + "", i));
        map1.put("对", 1);
        map1.put("错", 2);
    }

    @Override
    public PageInfo<Questions> findQuestionsList(FindQuestionsListReqVo reqVo) {
        QuestionsExample questionsExample = new QuestionsExample();
        QuestionsExample.Criteria criteria = questionsExample.createCriteria();
        if (StringUtils.isNotBlank(reqVo.getQuestionsForm().getQuestionsCode())) {
            criteria.andQuestionsCodeEqualTo(reqVo.getQuestionsForm().getQuestionsCode());
        }
        if (StringUtils.isNotBlank(reqVo.getQuestionsForm().getQuestionsTitle())) {
            criteria.andQuestionsTitleLike(reqVo.getQuestionsForm().getQuestionsTitle() + "%");
        }
        if (reqVo.getQuestionsForm().getQuestionsType() != null) {
            criteria.andQuestionsTypeEqualTo(reqVo.getQuestionsForm().getQuestionsType());
        }
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "questions_type,sort_order,create_time desc");
        List<Questions> topics = questionsMapper.selectByExample(questionsExample);
        PageInfo<Questions> pageInfo = new PageInfo<>(topics);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateQuestionsResVo createQuestions(CreateQuestionsReqVo reqVo, UserDetails userDetails) {
        String code = codeHelper.generateCode("questions_code", "questions", "Q");
        List<String> optionsVo = reqVo.getQuestionsForm().getOptions();
        for (int i = 0; i < optionsVo.size(); i++) {
            Options options = new Options();
            options.setOptionName(optionsVo.get(i));
            if (reqVo.getQuestionsForm().getQuestionsType() == QuestionsTypeEnum.SINGLE
                    || reqVo.getQuestionsForm().getQuestionsType() == QuestionsTypeEnum.JUDGE) {
                options.setCorrect(i == reqVo.getQuestionsForm().getRadio() ? true : false);
            } else if (reqVo.getQuestionsForm().getQuestionsType() == QuestionsTypeEnum.MULTIPLY) {
                options.setCorrect(reqVo.getQuestionsForm().getSelects().get(i));
            } else {
                options.setSort(i);
            }
            options.setQuestionsCode(code);
            options.setCreateUsename(userDetails.getUsername());
            optionsMapper.insertSelective(options);
        }
        Questions questions = new Questions();
        questions.setQuestionsCode(code);
        questions.setCreateUsename(userDetails.getUsername());
        questions.setQuestionsTitle(reqVo.getQuestionsForm().getQuestionsTitle());
        questions.setQuestionsType((byte) reqVo.getQuestionsForm().getQuestionsType().getCode());
        questions.setQuestionsScore(reqVo.getQuestionsForm().getQuestionsScore());
        questions.setSortOrder(reqVo.getQuestionsForm().getSortOrder());
        questions.setAnswerAnalysis(reqVo.getQuestionsForm().getAnswerAnalysis());
        questionsMapper.insertSelective(questions);

        CreateQuestionsResVo createQuestionsResVo = new CreateQuestionsResVo();
        createQuestionsResVo.setQuestionsForm(questions);
        return createQuestionsResVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer editQuestions(EditQuestionsReqVo reqVo, UserDetails userDetails) {
        delOptions(reqVo.getQuestionsForm().getQuestionsCode());
        List<String> optionsVo = reqVo.getQuestionsForm().getOptions();
        for (int i = 0; i < optionsVo.size(); i++) {
            Options options = new Options();
            options.setOptionName(optionsVo.get(i));
            options.setCorrect(i == reqVo.getQuestionsForm().getRadio() ? true : false);
            options.setSort(i);
            options.setQuestionsCode(reqVo.getQuestionsForm().getQuestionsCode());
            options.setCreateUsename(userDetails.getUsername());
            optionsMapper.insertSelective(options);
        }
        Questions questions = new Questions();
        questions.setId(reqVo.getQuestionsForm().getId());
        questions.setQuestionsTitle(reqVo.getQuestionsForm().getQuestionsTitle());
        questions.setQuestionsScore(reqVo.getQuestionsForm().getQuestionsScore());
        questions.setSortOrder(reqVo.getQuestionsForm().getSortOrder());
        questions.setAnswerAnalysis(reqVo.getQuestionsForm().getAnswerAnalysis());
        return questionsMapper.updateByPrimaryKeySelective(questions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delQuestions(DelQuestionsReqVo reqVo) {
        Questions questions = questionsMapper.selectByPrimaryKey(reqVo.getId());
        delOptions(questions.getQuestionsCode());
        return questionsMapper.deleteByPrimaryKey(reqVo.getId());
    }


    private void delOptions(String questionsCode) {
        OptionsExample optionsExample = new OptionsExample();
        OptionsExample.Criteria criteria = optionsExample.createCriteria();
        criteria.andQuestionsCodeEqualTo(questionsCode);
        optionsMapper.deleteByExample(optionsExample);
    }

    @Override
    @SneakyThrows
    public byte[] downloadQuestionsTemplate() {
        File file = ResourceUtils.getFile("classpath:business-template/试题导入模板.xls");
        return FileCopyUtils.copyToByteArray(file);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows
    public BatchImportQuestionsResVo batchImportQuestions(MultipartFile multipartFile, UserDetails userDetails) {
        ExcelType excelType = multipartFile.getOriginalFilename().endsWith("xls") ? ExcelType.XLS : ExcelType.XLSX;
        @Cleanup InputStream inputStream = multipartFile.getInputStream();
        ExcelReader excelReader = new ExcelReader(excelType, inputStream, QuestionsVo.class, 3, 0);
        excelReader.read();
        List<QuestionsVo> questionsVos = excelReader.getResultList();
        List<Questions> questionsList = questionsVos.stream()
                .map(questionsVo -> {
                    String code = codeHelper.generateCode("questions_code", "questions", "Q");
                    Questions questions = new Questions();
                    QuestionsTypeEnum questionsTypeEnum = QuestionsTypeEnum.getEnumByMsg(questionsVo.getQuestionsType());
                    questions.setQuestionsType((byte) questionsTypeEnum.getCode());
                    questions.setQuestionsTitle(questionsVo.getQuestionsTitle());
                    questions.setAnswerAnalysis(questionsVo.getAnswerAnalysis());
                    questions.setQuestionsCode(code);
                    questions.setCreateUsename(userDetails.getUsername());
                    if (questionsTypeEnum == QuestionsTypeEnum.JUDGE) {
                        questionsVo.setOptions(JUDGE);
                    }
                    String[] optionStrings = questionsVo.getOptions().split(";");
                    int[] i = new int[]{0};
                    questions.setSortOrder(i[0] + 1);
                    Arrays.stream(optionStrings).forEach(optionString -> {
                        Options options = resolveOptions(questionsTypeEnum, optionString, questionsVo.getAnswer(), i[0]++);
                        options.setCreateUsename(userDetails.getUsername());
                        options.setQuestionsCode(code);
                        optionsMapper.insertSelective(options);
                    });
                    questionsMapper.insertSelective(questions);
                    return questions;
                })
                .collect(Collectors.toList());
        BatchImportQuestionsResVo batchImportQuestionsResVo = new BatchImportQuestionsResVo();
        batchImportQuestionsResVo.setQuestionsList(questionsList);
        return batchImportQuestionsResVo;
    }

    private Options resolveOptions(QuestionsTypeEnum questionsTypeEnum, String optionString, String answer, int i) {
        int index = i + 1;
        Options options = new Options();
        options.setOptionName(optionString);
        options.setCorrect(false);
        switch (questionsTypeEnum) {
            case SINGLE:
                if (index == map.get(answer)) {
                    options.setCorrect(true);
                }
                break;
            case JUDGE:
                System.out.println(answer + " " + map1.get(answer));
                if (index == map1.get(answer)) {
                    options.setCorrect(true);
                }
                break;
            case MULTIPLY:
                char[] multiAnswers = answer.toCharArray();
                for (char multiAnswer : multiAnswers) {
                    if (index == map.get(multiAnswer + "")) {
                        options.setCorrect(true);
                        break;
                    }
                }
                break;
            case SORT:
                char[] sortAnswers = answer.toCharArray();
                int sort = map.get(sortAnswers[i] + "");
                options.setSort(sort);
                break;
            default:
                break;
        }
        return options;
    }

    @Override
    public FindOptionsListResVo findOptionsList(FindOptionsListReqVo reqVo) {
        OptionsExample optionsExample = new OptionsExample();
        OptionsExample.Criteria criteria = optionsExample.createCriteria();
        criteria.andQuestionsCodeEqualTo(reqVo.getQuestionsCode());
        List<Options> options = optionsMapper.selectByExample(optionsExample);
        FindOptionsListResVo findOptionsListResVo = new FindOptionsListResVo();
        findOptionsListResVo.setRadio(0);
        List<Boolean> selects = new ArrayList<>();
        findOptionsListResVo.setSelects(selects);
        List<String> optionsVo = new ArrayList<>();
        findOptionsListResVo.setOptions(optionsVo);
        for (int i = 0; i < options.size(); i++) {
            optionsVo.add(options.get(i).getOptionName());
            if (reqVo.getQuestionsType() == QuestionsTypeEnum.SINGLE
                    || reqVo.getQuestionsType() == QuestionsTypeEnum.JUDGE) {
                if (options.get(i).getCorrect()) {
                    findOptionsListResVo.setRadio(i);
                }
            } else if (reqVo.getQuestionsType() == QuestionsTypeEnum.MULTIPLY) {
                selects.add(options.get(i).getCorrect());
            }
        }
        return findOptionsListResVo;
    }
}