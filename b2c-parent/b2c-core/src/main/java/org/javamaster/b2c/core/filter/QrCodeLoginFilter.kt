package org.javamaster.b2c.core.filter

import org.javamaster.b2c.core.service.impl.LoginServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

/**
 * @author yudong
 * @date 2019/10/19
 */
@Component
class QrCodeLoginFilter : Filter {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        if (req.requestURI.startsWith(req.contextPath + "/public/qrCode")) {
            chain.doFilter(request, response)
            return
        }
        val cookieValue = WebUtils.getCookie(req, LoginServiceImpl.cookieName)
        if (cookieValue == null) {
            chain.doFilter(request, response)
            return
        }
        try {
            val context = redisTemplate.opsForValue().get(LoginServiceImpl.sessionKey + cookieValue.value)
            if (context != null) {
                redisTemplate.expire(LoginServiceImpl.sessionKey + cookieValue.value, 30, TimeUnit.MINUTES)
                SecurityContextHolder.setContext(context as SecurityContext)
            }
            chain.doFilter(request, response)
        } finally {
            SecurityContextHolder.clearContext()
        }
    }

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig?) {

    }

    override fun destroy() {

    }

}
