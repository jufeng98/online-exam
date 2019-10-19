package org.javamaster.b2c.core.service.impl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.javamaster.b2c.core.enums.BizExceptionEnum
import org.javamaster.b2c.core.exception.BizException
import org.javamaster.b2c.core.model.UserVo
import org.javamaster.b2c.core.service.LoginService
import org.javamaster.b2c.core.service.UsersService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.net.InetAddress
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class LoginServiceImpl : LoginService {
    companion object {
        const val loginKey = "qrCode:login:"
        const val sessionKey = "qrCode:session:"
        const val cookieName = "QRCODESESSION"
    }

    @Autowired
    lateinit var tokenRepository: PersistentTokenRepository
    @Autowired
    @Qualifier("userDetailsService")
    lateinit var userDetailsService: UserDetailsService
    @Autowired
    lateinit var usersService: UsersService
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>
    @Autowired
    lateinit var objectMapper: ObjectMapper

    override fun generateQrCodeContent(): Map<String, String> {
        val ip = InetAddress.getLocalHost().hostAddress
        val uuid = UUID.randomUUID().toString()
        redisTemplate.opsForValue().set(loginKey + uuid, "", 60, TimeUnit.SECONDS)
        return mapOf("url" to "http://$ip:9888/onlineExam/public/qrCode/qrCodeLogin?uuid=$uuid", "uuid" to uuid)
    }

    override fun qrCodeLogin(uuid: String, rememberMeInfo: String, request: HttpServletRequest, response: HttpServletResponse): Map<String, Any> {
        val exists = redisTemplate.hasKey(loginKey + uuid)
        if (!exists) {
            throw BizException(BizExceptionEnum.QR_CODE_EXPIRED)
        }
        var cookieValue = ""
        val strings = rememberMeInfo.split(Regex(";"))
        for (string in strings) {
            val tmp = string.split(Regex("="))
            if ("CORE_REMEMBER_ME" == tmp[0]) {
                cookieValue = tmp[1]
                break
            }
        }
//        for (j in 0 until cookieValue.length % 4) {
//            cookieValue = "$cookieValue="
//        }
        val cookieAsPlainText = String(Base64.getDecoder().decode(cookieValue.toByteArray()))
        val tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, ":")
        for (i in 0 until tokens.size) {
            tokens[i] = URLDecoder.decode(tokens[i], StandardCharsets.UTF_8.toString())
        }
        val presentedSeries = tokens[0]
        val presentedToken = tokens[1]
        val token = tokenRepository.getTokenForSeries(presentedSeries)
        if (token == null || presentedToken != token.tokenValue) {
            throw BizException(BizExceptionEnum.INVALID_REQ_PARAM)
        }
        val userDetails = userDetailsService.loadUserByUsername(token.username)
        val users = usersService.findUsersByUsername(token.username)
        val userVo = UserVo()
        BeanUtils.copyProperties(users, userVo)
        userVo.authorities = userDetails.authorities
        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userVo.authorities)
        val context = SecurityContextHolder.getContext()
        context.authentication = authentication
        redisTemplate.opsForValue().set(sessionKey + uuid, context, 30, TimeUnit.MINUTES)
        redisTemplate.opsForValue().set(loginKey + uuid, objectMapper.writeValueAsString(userVo))
        val map = HashMap<String, Any>(10)
        map["success"] = true
        map["data"] = userVo
        return map
    }

    override fun checkScanStatus(uuid: String, response: HttpServletResponse): JsonNode {
        var times = redisTemplate.opsForValue().increment(uuid)!!
        var value = ""
        while (times <= 10) {
            value = redisTemplate.opsForValue().get(loginKey + uuid) as String
            if (value == "") {
                times = redisTemplate.opsForValue().increment(uuid)!!
                TimeUnit.SECONDS.sleep(5)
            } else {
                break
            }
        }
        redisTemplate.delete(uuid)
        if (value == "") {
            throw BizException(BizExceptionEnum.QR_CODE_EXPIRED)
        }
        val userVo = objectMapper.readValue(value, JsonNode::class.java)
        val cookie = Cookie(cookieName, uuid)
        cookie.path = "/onlineExam/"
        cookie.isHttpOnly = true
        response.addCookie(cookie)
        redisTemplate.delete(loginKey + uuid)
        return userVo
    }
}