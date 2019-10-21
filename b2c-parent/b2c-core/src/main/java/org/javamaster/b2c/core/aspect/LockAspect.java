package org.javamaster.b2c.core.aspect;

import static java.util.stream.Collectors.toList;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.exception.BizException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author yudong
 * @date 2019/7/11
 */
@Aspect
@Component
public class LockAspect {

    @Autowired
    private RedissonClient redisson;

    @Pointcut("@annotation(org.javamaster.b2c.core.annos.AopLock)")
    public void lockPointCut() {
    }

    @Around("lockPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object resObject;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String cookieValue = Arrays.stream(Objects.requireNonNull(requestAttributes).getRequest().getCookies())
                .filter(cookie -> {
                    String sessionKey = "SESSION";
                    return sessionKey.equals(cookie.getName());
                })
                .map(Cookie::getValue)
                .collect(toList())
                .get(0);
        RLock lock = redisson.getLock(cookieValue);
        try {
            boolean locked = lock.tryLock(3, TimeUnit.SECONDS);
            if (!locked) {
                throw new BizException(BizExceptionEnum.OPERATION_TOO_FREQUENT);
            }
            resObject = joinPoint.proceed(joinPoint.getArgs());
        } finally {
            lock.unlock();
        }
        return resObject;
    }


}
