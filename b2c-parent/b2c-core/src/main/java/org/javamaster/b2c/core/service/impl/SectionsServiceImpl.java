package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.javamaster.b2c.core.entity.Sections;
import org.javamaster.b2c.core.entity.SectionsExample;
import org.javamaster.b2c.core.helper.CodeHelper;
import org.javamaster.b2c.core.mapper.SectionsMapper;
import org.javamaster.b2c.core.model.vo.CreateSectionsReqVo;
import org.javamaster.b2c.core.model.vo.CreateSectionsResVo;
import org.javamaster.b2c.core.model.vo.DelSectionsReqVo;
import org.javamaster.b2c.core.model.vo.EditSectionsReqVo;
import org.javamaster.b2c.core.model.vo.FindSectionsListReqVo;
import org.javamaster.b2c.core.service.SectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yudong
 * @date 2019/08/07
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SectionsServiceImpl implements SectionsService {

    @Autowired
    private SectionsMapper sectionsMapper;
    @Autowired
    private CodeHelper codeHelper;

    @Override
    public PageInfo<Sections> findSectionsList(FindSectionsListReqVo reqVo) {
        SectionsExample sectionsExample = new SectionsExample();
        SectionsExample.Criteria criteria = sectionsExample.createCriteria();
        if (StringUtils.isNotBlank(reqVo.getSectionsForm().getSectionsName())) {
            criteria.andSectionsNameLike(reqVo.getSectionsForm().getSectionsName() + "%");
        }
        if (StringUtils.isNotBlank(reqVo.getSectionsForm().getTopicsCode())) {
            criteria.andTopicsCodeEqualTo(reqVo.getSectionsForm().getTopicsCode());
        }
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "sort_order,create_time desc");
        List<Sections> sections = sectionsMapper.selectByExampleWithBLOBs(sectionsExample);
        PageInfo<Sections> pageInfo = new PageInfo<>(sections);
        return pageInfo;
    }

    @Override
    public CreateSectionsResVo createSections(CreateSectionsReqVo reqVo, UserDetails userDetails) {
        Sections sections = new Sections();
        String code = codeHelper.generateCode("sections_code", "sections", "S");
        sections.setSectionsCode(code);
        sections.setSectionsCoverImage(reqVo.getSectionsForm().getSectionsCoverImage());
        sections.setSectionsName(reqVo.getSectionsForm().getSectionsName());
        sections.setCreateUsename(userDetails.getUsername());
        sections.setTopicsCode(reqVo.getSectionsForm().getTopicsCode());
        sections.setSortOrder(reqVo.getSectionsForm().getSortOrder());
        sectionsMapper.insertSelective(sections);
        CreateSectionsResVo createSectionsResVo = new CreateSectionsResVo();
        createSectionsResVo.setSections(sections);
        return createSectionsResVo;
    }

    @Override
    public Integer editSections(EditSectionsReqVo reqVo) {
        Sections sections = new Sections();
        sections.setId(reqVo.getSectionsForm().getId());
        sections.setSectionsCoverImage(reqVo.getSectionsForm().getSectionsCoverImage());
        sections.setSectionsName(reqVo.getSectionsForm().getSectionsName());
        sections.setTopicsCode(reqVo.getSectionsForm().getTopicsCode());
        sections.setSortOrder(reqVo.getSectionsForm().getSortOrder());
        return sectionsMapper.updateByPrimaryKeySelective(sections);
    }

    @Override
    public Integer delSections(DelSectionsReqVo reqVo) {
        return sectionsMapper.deleteByPrimaryKey(reqVo.getId());
    }

}