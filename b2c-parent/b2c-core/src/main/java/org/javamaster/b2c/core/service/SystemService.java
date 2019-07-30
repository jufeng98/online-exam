package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.model.vo.JinfoResVo;
import org.javamaster.b2c.core.model.vo.JpsResVo;
import org.javamaster.b2c.core.model.vo.PrintFlagsFinalResVo;

/**
 * 系统管理
 *
 * @author yudong
 * @date 2019/7/25
 */
public interface SystemService {

    /**
     * jps
     *
     * @return
     */
    JpsResVo jps();

    /**
     * jinfo
     *
     * @return
     */
    JinfoResVo jinfo();

    /**
     * PrintFlagsFinal
     *
     * @return
     */
    PrintFlagsFinalResVo printFlagsFinal();

}