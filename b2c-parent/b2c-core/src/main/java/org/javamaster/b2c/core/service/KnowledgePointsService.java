package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.KnowledgePoints;
import org.javamaster.b2c.core.model.vo.CreateKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.CreateKnowledgePointsResVo;
import org.javamaster.b2c.core.model.vo.DelKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.EditKnowledgePointsReqVo;
import org.javamaster.b2c.core.model.vo.FindKnowledgePointsListReqVo;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * 知识点管理
 *
 * @author yudong
 * @date 2019/08/07
 */
public interface KnowledgePointsService {

    /**
     * 获取知识点列表
     *
     * @param reqVo
     * @return
     */
    PageInfo<KnowledgePoints> findKnowledgePointsList(FindKnowledgePointsListReqVo reqVo);

    /**
     * 创建知识点
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    CreateKnowledgePointsResVo createKnowledgePoints(CreateKnowledgePointsReqVo reqVo, UserDetails userDetails);

    /**
     * 编辑知识点
     *
     * @param reqVo
     * @return
     */
    Integer editKnowledgePoints(EditKnowledgePointsReqVo reqVo);

    /**
     * 删除知识点
     *
     * @param reqVo
     * @return
     */
    Integer delKnowledgePoints(DelKnowledgePointsReqVo reqVo);

    /**
     * 查找知识下所有知识点关联的试题数量
     *
     * @return key是知识编码, value是试题数量
     */
    Map<String, Integer> findKnowledgesQuestionNum();

}