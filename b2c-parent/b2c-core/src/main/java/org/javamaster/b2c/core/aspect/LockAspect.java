package org.javamaster.b2c.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.javamaster.b2c.core.annos.AopLock;
import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.exception.BizException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author yudong
 * @date 2019/7/11
 */
@Aspect
@Component
public class LockAspect {
    public static final String LOCK_KEY_PREFIX = "core:lock:";

    @Autowired
    private RedissonClient redisson;
    @Autowired
    private ParameterNameDiscoverer parameterNameDiscoverer;
    @Autowired
    private ExpressionParser parser;

    @Pointcut("@annotation(org.javamaster.b2c.core.annos.AopLock)")
    public void lockPointCut() {
    }

    @Around("lockPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            String paramName = parameterNames[i];
            context.setVariable(paramName, args[i]);
        }

        AopLock aopLock = method.getAnnotation(AopLock.class);
        String spEl = aopLock.spEL();
        String lockKey = LOCK_KEY_PREFIX + parser.parseExpression(spEl).getValue(context);
        RLock lock = redisson.getLock(lockKey);
        try {
            boolean getterLock = lock.tryLock(3, TimeUnit.SECONDS);
            if (!getterLock) {
                throw new BizException(BizExceptionEnum.OPERATION_TOO_FREQUENT);
            }
            return joinPoint.proceed(args);
        } finally {
            lock.unlock();
        }
    }


}
