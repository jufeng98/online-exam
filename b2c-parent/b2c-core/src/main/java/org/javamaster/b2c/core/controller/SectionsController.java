package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.entity.Sections;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.CreateSectionsReqVo;
import org.javamaster.b2c.core.model.vo.CreateSectionsResVo;
import org.javamaster.b2c.core.model.vo.DelSectionsReqVo;
import org.javamaster.b2c.core.model.vo.EditSectionsReqVo;
import org.javamaster.b2c.core.model.vo.FindSectionsListReqVo;
import org.javamaster.b2c.core.service.SectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 章节管理
 *
 * @author yudong
 * @date 2019/08/07
 */
@RestController
@RequestMapping("/core/sections")
public class SectionsController {

    @Autowired
    private SectionsService sectionsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findSectionsList")
    public Result<List<Sections>> findSectionsList(@Validated @RequestBody FindSectionsListReqVo reqVo) {
        PageInfo<Sections> resVo = sectionsService.findSectionsList(reqVo);
        return new Result<>(resVo.getList(), resVo.getTotal());
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/createSections")
    public Result<CreateSectionsResVo> createSections(@Validated @RequestBody CreateSectionsReqVo reqVo,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        CreateSectionsResVo resVo = sectionsService.createSections(reqVo, userDetails);
        return new Result<>(resVo);
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/editSections")
    public Result<Integer> editSections(@Validated @RequestBody EditSectionsReqVo reqVo) {
        Integer resVo = sectionsService.editSections(reqVo);
        return new Result<>(resVo);
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/delSections")
    public Result<Integer> delSections(@Validated @RequestBody DelSectionsReqVo reqVo) {
        Integer resVo = sectionsService.delSections(reqVo);
        return new Result<>(resVo);
    }

}