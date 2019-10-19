package org.javamaster.b2c.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.javamaster.b2c.core.entity.Users;
import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.UserVo;
import org.javamaster.b2c.core.service.UsersService;
import org.javamaster.b2c.core.service.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author yudong
 * @date 2019/6/10
 */
@Component
public class LoginHandler {
    private static Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        logger.info("username:{} login in,login time:{}", request.getParameter("username")
                , format.format(LocalDateTime.now()));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        UserVo userVo = new UserVo();
        Users users = usersService.findUsersByUsername(request.getParameter("username"));
        BeanUtils.copyProperties(users, userVo);
        userVo.setAuthorities(userDetails.getAuthorities());
        Map<String, Object> map = new HashMap<>(10);
        map.put("success", true);
        map.put("data", userVo);
        response.getWriter().print(objectMapper.writeValueAsString(map));
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException {
        logger.error("username:{} login failed,login time:{}", request.getParameter("username")
                , format.format(LocalDateTime.now()), exception);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().print(objectMapper.writeValueAsString(new Result<>(BizExceptionEnum.INVALID_USER.getErrorCode(),
                BizExceptionEnum.INVALID_USER.getErrorMsg())));
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        String username = "";
        if (authentication.getPrincipal() != null) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        val cookieValue = WebUtils.getCookie(request, LoginServiceImpl.cookieName);
        if (cookieValue != null) {
            redisTemplate.delete(LoginServiceImpl.sessionKey + cookieValue.getValue());
            val cookie = new Cookie(LoginServiceImpl.cookieName, "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        logger.info("username:{} logout,logout time:{}", username, format.format(LocalDateTime.now()));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().print(objectMapper.writeValueAsString(new Result<>("登出成功")));
    }

    public void onAuthenticationEntryPoint(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(HttpStatus.UNAUTHORIZED.toString());
    }
}
