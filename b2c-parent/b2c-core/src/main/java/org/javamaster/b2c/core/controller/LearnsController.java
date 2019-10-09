package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.entity.LearnsRecord;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.SaveLearnsReqVo;
import org.javamaster.b2c.core.model.vo.SectionsProgressVo;
import org.javamaster.b2c.core.model.vo.TopicsProgressVo;
import org.javamaster.b2c.core.service.LearnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 学习管理
 *
 * @author yudong
 * @date 2019/09/18
 */
@Validated
@RestController
@RequestMapping("/core/learns")
public class LearnsController {

    @Autowired
    private LearnsService learnsService;

    @PostMapping("/saveLearns")
    public Result<Integer> saveLearns(@Validated @RequestBody SaveLearnsReqVo reqVo) {
        Integer resVo = learnsService.saveLearns(reqVo);
        return new Result<>(resVo);
    }

    @PostMapping("/findLearnsByUsername")
    public Result<List<LearnsRecord>> findLearnsByUsername(@NotBlank String username) {
        List<LearnsRecord> resVo = learnsService.findLearnsByUsername(username);
        return new Result<>(resVo);
    }

    @PostMapping("/findTopicsProgress")
    public Result<List<TopicsProgressVo>> findTopicsProgress(@NotBlank String username) {
        List<TopicsProgressVo> resVo = learnsService.findTopicsProgress(username);
        return new Result<>(resVo);
    }

    @PostMapping("/findSectionsProgress")
    public Result<List<SectionsProgressVo>> findSectionsProgress(@NotBlank String username) {
        List<SectionsProgressVo> resVo = learnsService.findSectionsProgress(username);
        return new Result<>(resVo);
    }

}