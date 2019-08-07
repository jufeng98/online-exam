package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Sections;
import org.javamaster.b2c.core.model.vo.CreateSectionsReqVo;
import org.javamaster.b2c.core.model.vo.CreateSectionsResVo;
import org.javamaster.b2c.core.model.vo.DelSectionsReqVo;
import org.javamaster.b2c.core.model.vo.EditSectionsReqVo;
import org.javamaster.b2c.core.model.vo.FindSectionsListReqVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 章节管理
 *
 * @author yudong
 * @date 2019/08/07
 */
public interface SectionsService {

    /**
     * 获取章节列表
     *
     * @param reqVo
     * @return
     */
    PageInfo<Sections> findSectionsList(FindSectionsListReqVo reqVo);

    /**
     * 新增章节
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    CreateSectionsResVo createSections(CreateSectionsReqVo reqVo, UserDetails userDetails);

    /**
     * 编辑章节
     *
     * @param reqVo
     * @return
     */
    Integer editSections(EditSectionsReqVo reqVo);

    /**
     * 删除章节
     *
     * @param reqVo
     * @return
     */
    Integer delSections(DelSectionsReqVo reqVo);

}