package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.entity.LearnsRecord;
import org.javamaster.b2c.core.model.vo.SaveLearnsReqVo;
import org.javamaster.b2c.core.model.vo.SectionsProgressVo;
import org.javamaster.b2c.core.model.vo.TopicsProgressVo;

import java.util.List;

/**
 * 学习管理
 *
 * @author yudong
 * @date 2019/09/18
 */
public interface LearnsService {

    /**
     * 保存学习记录
     *
     * @param reqVo
     * @return
     */
    Integer saveLearns(SaveLearnsReqVo reqVo);

    /**
     * 获取学习记录
     *
     * @param username
     * @return
     */
    List<LearnsRecord> findLearnsByUsername(String username);

    /**
     * 获取主题学习进度
     *
     * @param username
     * @return
     */
    List<TopicsProgressVo> findTopicsProgress(String username);

    /**
     * 获取章节学习进度
     *
     * @param username
     * @return
     */
    List<SectionsProgressVo> findSectionsProgress(String username);

}