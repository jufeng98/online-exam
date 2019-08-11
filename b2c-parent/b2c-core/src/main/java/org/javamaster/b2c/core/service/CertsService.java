package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Certs;
import org.javamaster.b2c.core.model.vo.CreateCertsReqVo;
import org.javamaster.b2c.core.model.vo.CreateCertsResVo;
import org.javamaster.b2c.core.model.vo.DelCertsReqVo;
import org.javamaster.b2c.core.model.vo.EditCertsReqVo;
import org.javamaster.b2c.core.model.vo.FindCertsListReqVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 证书管理
 *
 * @author yudong
 * @date 2019/08/11
 */
public interface CertsService {

    /**
     * 获取证书样图
     *
     * @param id
     * @return
     */
    byte[] findCertsExamplePdf(Integer id);


    /**
     * 获取证书列表
     *
     * @param reqVo
     * @return
     */
    PageInfo<Certs> findCertsList(FindCertsListReqVo reqVo);

    /**
     * 创建证书
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    CreateCertsResVo createCerts(CreateCertsReqVo reqVo, UserDetails userDetails);

    /**
     * 编辑证书
     *
     * @param reqVo
     * @return
     */
    Integer editCerts(EditCertsReqVo reqVo);

    /**
     * 删除证书
     *
     * @param reqVo
     * @return
     */
    Integer delCerts(DelCertsReqVo reqVo);

}