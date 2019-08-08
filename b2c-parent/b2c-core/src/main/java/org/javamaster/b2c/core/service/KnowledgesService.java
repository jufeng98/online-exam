package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Knowledges;
import org.javamaster.b2c.core.model.vo.CreateKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.CreateKnowledgesResVo;
import org.javamaster.b2c.core.model.vo.DelKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.EditKnowledgesReqVo;
import org.javamaster.b2c.core.model.vo.FindKnowledgesListReqVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 知识管理
 *
 * @author yudong
 * @date 2019/08/07
 */
public interface KnowledgesService {

    /**
     * 获取知识列表
     *
     * @param reqVo
     * @return
     */
    PageInfo<Knowledges> findKnowledgesList(FindKnowledgesListReqVo reqVo);

    /**
     * 创建知识
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    CreateKnowledgesResVo createKnowledges(CreateKnowledgesReqVo reqVo, UserDetails userDetails);

    /**
     * 编辑知识
     *
     * @param reqVo
     * @return
     */
    Integer editKnowledges(EditKnowledgesReqVo reqVo);

    /**
     * 删除知识
     *
     * @param reqVo
     * @return
     */
    Integer delKnowledges(DelKnowledgesReqVo reqVo);

}