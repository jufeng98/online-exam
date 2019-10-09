package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.entity.Options;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.FindAssociateOptionsReqVo;
import org.javamaster.b2c.core.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 选项管理
 *
 * @author yudong
 * @date 2019/09/14
 */
@RestController
@RequestMapping("/core/options")
public class OptionsController {

    @Autowired
    private OptionsService optionsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findAssociateOptions")
    public Result<List<Options>> findAssociateOptions(@Validated @RequestBody FindAssociateOptionsReqVo reqVo) {
        List<Options> resVo = optionsService.findAssociateOptions(reqVo);
        return new Result<>(resVo);
    }

}