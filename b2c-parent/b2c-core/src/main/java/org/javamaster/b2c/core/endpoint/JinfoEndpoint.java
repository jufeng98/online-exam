package org.javamaster.b2c.core.endpoint;

import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.JinfoResVo;
import org.javamaster.b2c.core.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

/**
 * 系统管理
 *
 * @author yudong
 * @date 2019/7/25
 */
@Component
@Endpoint(id = "jinfo")
public class JinfoEndpoint {

    @Autowired
    private SystemService systemService;

    @ReadOperation
    public Result<JinfoResVo> jinfo() {
        JinfoResVo resVo = systemService.jinfo();
        Result<JinfoResVo> result = new Result(resVo);
        return result;
    }

}