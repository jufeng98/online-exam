package org.javamaster.b2c.core.service

import com.fasterxml.jackson.databind.JsonNode
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author yudong
 * @date 2019/10/18
 */
interface LoginService {
    /**
     * 生成扫码登录二维码内容
     */
    fun generateQrCodeContent(): Map<String, String>

    /**
     * 二维码登录
     */
    fun qrCodeLogin(uuid: String, rememberMeInfo: String, request: HttpServletRequest, response: HttpServletResponse): Map<String, Any>

    /**
     * 检查扫码登录情况
     */
    fun checkScanStatus(uuid: String, response: HttpServletResponse): JsonNode
}