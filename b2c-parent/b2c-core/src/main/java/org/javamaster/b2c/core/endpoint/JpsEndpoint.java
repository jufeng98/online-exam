package org.javamaster.b2c.core.endpoint;

import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.JpsResVo;
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
@Endpoint(id = "jps")
public class JpsEndpoint {

    @Autowired
    private SystemService systemService;

    @ReadOperation
    public Result<JpsResVo> jps() {
        JpsResVo resVo = systemService.jps();
        Result<JpsResVo> result = new Result(resVo);
        return result;
    }

}