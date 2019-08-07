package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Topics;
import org.javamaster.b2c.core.model.vo.CreateTopicsReqVo;
import org.javamaster.b2c.core.model.vo.CreateTopicsResVo;
import org.javamaster.b2c.core.model.vo.DelTopicsReqVo;
import org.javamaster.b2c.core.model.vo.EditTopicsReqVo;
import org.javamaster.b2c.core.model.vo.FindTopicsListReqVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 主题管理
 *
 * @author yudong
 * @date 2019/08/07
 */
public interface TopicsService {

    /**
     * 获取主题列表
     *
     * @param reqVo
     * @return
     */
    PageInfo<Topics> findTopicsList(FindTopicsListReqVo reqVo);

    /**
     * 创建主题
     *
     * @param reqVo
     * @return
     */
    CreateTopicsResVo createTopics(CreateTopicsReqVo reqVo, UserDetails userDetails);

    /**
     * 编辑主题
     *
     * @param reqVo
     * @return
     */
    Integer editTopics(EditTopicsReqVo reqVo);

    /**
     * 删除主题
     *
     * @param reqVo
     * @return
     */
    Integer delTopics(DelTopicsReqVo reqVo);

}