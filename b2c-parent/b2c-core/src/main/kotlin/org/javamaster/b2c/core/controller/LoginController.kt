package org.javamaster.b2c.core.controller

import com.fasterxml.jackson.databind.JsonNode
import org.javamaster.b2c.core.model.Result
import org.javamaster.b2c.core.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 扫码登录
 *
 * @author yudong
 * @date 2019/10/18
 */
@RestController
@RequestMapping("/public/qrCode")
class LoginController {

    @Autowired
    private lateinit var loginService: LoginService

    @PostMapping("/generateQrCodeContent")
    fun generateQrCodeContent(): Result<Map<String, String>> {
        return Result(loginService.generateQrCodeContent())
    }

    @PostMapping("/qrCodeLogin")
    fun qrCodeLogin(uuid: String, rememberMeInfo: String, request: HttpServletRequest, response: HttpServletResponse)
            : Result<Map<String, Any>> {
        return Result(loginService.qrCodeLogin(uuid, rememberMeInfo, request, response))
    }

    @PostMapping("/checkScanStatus")
    fun checkScanStatus(uuid: String, response: HttpServletResponse): Result<JsonNode> {
        return Result(loginService.checkScanStatus(uuid, response))
    }

}