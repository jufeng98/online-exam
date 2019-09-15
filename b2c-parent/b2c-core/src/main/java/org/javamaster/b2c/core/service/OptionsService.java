package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.entity.Options;
import org.javamaster.b2c.core.model.vo.FindAssociateOptionsReqVo;

import java.util.List;

/**
 * 选项管理
 *
 * @author yudong
 * @date 2019/09/14
 */
public interface OptionsService {

    /**
     * 获取关联的选项
     *
     * @param reqVo
     * @return
     */
    List<Options> findAssociateOptions(FindAssociateOptionsReqVo reqVo);

}