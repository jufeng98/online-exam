package org.javamaster.b2c.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.javamaster.b2c.core.annos.AopLock;
import org.javamaster.b2c.core.entity.AnswersRecord;
import org.javamaster.b2c.core.entity.ExamsRecord;
import org.javamaster.b2c.core.entity.Messages;
import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.enums.QuestionsTypeEnum;
import org.javamaster.b2c.core.enums.ReadEnum;
import org.javamaster.b2c.core.exception.BizException;
import org.javamaster.b2c.core.mapper.AnswersRecordMapper;
import org.javamaster.b2c.core.mapper.ExamsRecordMapper;
import org.javamaster.b2c.core.mapper.MessagesMapper;
import org.javamaster.b2c.core.model.Page;
import org.javamaster.b2c.core.model.vo.AnswerDetail;
import org.javamaster.b2c.core.model.vo.ExamQuestionsVo;
import org.javamaster.b2c.core.model.vo.FindExamsListReqVo;
import org.javamaster.b2c.core.model.vo.OptionsVo;
import org.javamaster.b2c.core.model.vo.SubmitAnswersReqVo;
import org.javamaster.b2c.core.model.vo.SubmitAnswersResVo;
import org.javamaster.b2c.core.service.ExamsRecordService;
import org.javamaster.b2c.core.service.ExamsService;
import org.javamaster.b2c.core.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yudong
 * @date 2019/09/19
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Slf4j
public class ExamsRecordServiceImpl implements ExamsRecordService {

    @Autowired
    private ExamsService examsService;
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private AnswersRecordMapper answersRecordMapper;
    @Autowired
    private ExamsRecordMapper examsRecordMapper;
    @Autowired
    private MessagesMapper messagesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AopLock(spEL = "#userDetails.username")
    public SubmitAnswersResVo submitAnswers(SubmitAnswersReqVo reqVo, UserDetails userDetails) {
        List<ExamQuestionsVo> examQuestionsVos = questionsService.findQuestionsByExamsCode(reqVo.getExamsCode());
        if (examQuestionsVos.isEmpty()) {
            log.error("examsCode not exists:{},username:{}", reqVo.getExamsCode(), userDetails.getUsername());
            throw new BizException(BizExceptionEnum.INVALID_REQ_PARAM);
        }
        Map<String, ExamQuestionsVo> questionsMap = new HashMap<>(examQuestionsVos.size(), 1);
        for (ExamQuestionsVo examQuestionsVo : examQuestionsVos) {
            questionsMap.put(examQuestionsVo.getQuestionsCode(), examQuestionsVo);
        }

        List<AnswerDetail> answerDetails = reqVo.getExamsAnswers().stream()
                .map(examsUserAnswer -> {
                    ExamQuestionsVo examQuestionsVo = questionsMap.get(examsUserAnswer.getQuestionsCode());
                    if (examQuestionsVo == null) {
                        log.error("questionsCode not exists:{}", examsUserAnswer.getQuestionsCode());
                        throw new BizException(BizExceptionEnum.INVALID_REQ_PARAM);
                    }

                    AnswerDetail answerDetail = new AnswerDetail();
                    answerDetail.setQuestionsCode(examQuestionsVo.getQuestionsCode());
                    answerDetail.setAnswerAnalysis(examQuestionsVo.getAnswerAnalysis());
                    answerDetail.setQuestionsNum(examsUserAnswer.getQuestionsNum());

                    Set<Integer> rightAnswers = examQuestionsVo.getOptionsVos().stream()
                            .filter(OptionsVo::getCorrect)
                            .map(OptionsVo::getId)
                            .collect(Collectors.toSet());
                    if (QuestionsTypeEnum.SINGLE.getCode() == examQuestionsVo.getQuestionsType()
                            || QuestionsTypeEnum.JUDGE.getCode() == examQuestionsVo.getQuestionsType()
                            || QuestionsTypeEnum.MULTIPLY.getCode() == examQuestionsVo.getQuestionsType()) {
                        answerDetail.setExamsRightAnswers(rightAnswers);
                        if (rightAnswers.equals(examsUserAnswer.getAnswers())) {
                            answerDetail.setCorrect(true);
                            answerDetail.setScore(examQuestionsVo.getQuestionsScore());
                        } else {
                            answerDetail.setCorrect(false);
                            answerDetail.setScore(0);
                        }

                        AnswersRecord answersRecord = new AnswersRecord();
                        answersRecord.setUsername(userDetails.getUsername());
                        answersRecord.setExamsCode(reqVo.getExamsCode());
                        answersRecord.setQuestionsCode(examQuestionsVo.getQuestionsCode());
                        answersRecord.setScore(answerDetail.getScore().byteValue());
                        String answer = examsUserAnswer.getAnswers().stream()
                                .map(String::valueOf).collect(Collectors.joining(","));
                        answersRecord.setAnswer(answer);
                        answersRecordMapper.insertSelective(answersRecord);

                        return answerDetail;
                    }
                    throw new RuntimeException("未知的题目类型");

                })
                .collect(Collectors.toList());
        int examsScore = answerDetails.stream().mapToInt(AnswerDetail::getScore).sum();
        ExamsRecord examsRecord = new ExamsRecord();
        examsRecord.setUsername(userDetails.getUsername());
        examsRecord.setExamsCode(reqVo.getExamsCode());
        examsRecord.setScore((short) examsScore);
        examsRecordMapper.insertSelective(examsRecord);

        FindExamsListReqVo findExamsListReqVo = new FindExamsListReqVo();
        findExamsListReqVo.setExamsCode(reqVo.getExamsCode());
        findExamsListReqVo.setPage(new Page(1, 1));
        String examsName = examsService.findExamsList(findExamsListReqVo).getList().get(0).getExamsName();
        Messages messages = new Messages();
        messages.setUsername(userDetails.getUsername());
        messages.setContent("您已完成" + examsName + ",本次得分:" + examsScore);
        messages.setAlreadyRead((byte) ReadEnum.UNREAD.getCode());
        messagesMapper.insertSelective(messages);

        SubmitAnswersResVo submitAnswersResVo = new SubmitAnswersResVo();
        submitAnswersResVo.setScore(examsScore);
        submitAnswersResVo.setAnswerDetails(answerDetails);

        return submitAnswersResVo;
    }

}