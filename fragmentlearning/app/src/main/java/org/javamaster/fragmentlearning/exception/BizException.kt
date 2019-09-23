package org.javamaster.fragmentlearning.exception

import org.javamaster.fragmentlearning.data.model.ResultVo
import java.io.IOException

/**
 * @author yudong
 * @date 2019/9/22
 */
class BizException(val resultVo: ResultVo<out Any>) : IOException(resultVo.errorMsg) {
    var errorCode: Int? = resultVo.errorCode
}