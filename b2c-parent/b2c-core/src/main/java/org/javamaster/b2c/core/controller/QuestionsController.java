package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.entity.Questions;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.BatchImportQuestionsResVo;
import org.javamaster.b2c.core.model.vo.CreateQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.CreateQuestionsResVo;
import org.javamaster.b2c.core.model.vo.DelQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.EditQuestionsReqVo;
import org.javamaster.b2c.core.model.vo.ExamQuestionsVo;
import org.javamaster.b2c.core.model.vo.FindOptionsListReqVo;
import org.javamaster.b2c.core.model.vo.FindOptionsListResVo;
import org.javamaster.b2c.core.model.vo.FindQuestionsListReqVo;
import org.javamaster.b2c.core.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 试题管理
 *
 * @author yudong
 * @date 2019/08/08
 */
@Validated
@RestController
@RequestMapping("/core/questions")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findQuestionsList")
    public Result<List<Questions>> findQuestionsList(@Validated @RequestBody FindQuestionsListReqVo reqVo) {
        PageInfo<Questions> resVo = questionsService.findQuestionsList(reqVo);
        Result<List<Questions>> result = new Result<>(resVo.getList(), resVo.getTotal());
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/createQuestions")
    public Result<CreateQuestionsResVo> createQuestions(@Validated @RequestBody CreateQuestionsReqVo reqVo,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        CreateQuestionsResVo resVo = questionsService.createQuestions(reqVo, userDetails);
        Result<CreateQuestionsResVo> result = new Result<>(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/editQuestions")
    public Result<Integer> editQuestions(@Validated @RequestBody EditQuestionsReqVo reqVo,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        Integer resVo = questionsService.editQuestions(reqVo, userDetails);
        Result<Integer> result = new Result<>(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/delQuestions")
    public Result<Integer> delQuestions(@Validated @RequestBody DelQuestionsReqVo reqVo) {
        Integer resVo = questionsService.delQuestions(reqVo);
        Result<Integer> result = new Result<>(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @GetMapping("/downloadQuestionsTemplate")
    @SneakyThrows
    public ResponseEntity<byte[]> downloadQuestionsTemplate() {
        byte[] bytes = questionsService.downloadQuestionsTemplate();
        HttpHeaders headers = new HttpHeaders();
        String fileName = URLEncoder.encode("试题导入模板", StandardCharsets.UTF_8.name());
        headers.setContentDispositionFormData("attachment", fileName + ".xls");
        headers.setContentType(new MediaType("application", "vnd.ms-excel"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/batchImportQuestions")
    public Result<BatchImportQuestionsResVo> batchImportQuestions(@RequestPart("file") MultipartFile[] multipartFiles,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {
        BatchImportQuestionsResVo resVo = questionsService.batchImportQuestions(multipartFiles[0], userDetails);
        Result<BatchImportQuestionsResVo> result = new Result<>(resVo);
        return result;
    }

    @PostMapping("/findOptionsList")
    public Result<FindOptionsListResVo> findOptionsList(@Validated @RequestBody FindOptionsListReqVo reqVo) {
        FindOptionsListResVo resVo = questionsService.findOptionsList(reqVo);
        Result<FindOptionsListResVo> result = new Result<>(resVo);
        return result;
    }

    @PostMapping("/findQuestionsByExamsCode")
    public Result<List<ExamQuestionsVo>> findQuestionsByExamsCode(@Validated @NotBlank String examsCode) {
        List<ExamQuestionsVo> resVo = questionsService.findQuestionsByExamsCode(examsCode);
        Result<List<ExamQuestionsVo>> result = new Result<>(resVo);
        return result;
    }

}