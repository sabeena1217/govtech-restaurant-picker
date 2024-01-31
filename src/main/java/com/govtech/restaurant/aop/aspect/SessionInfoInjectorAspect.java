package com.govtech.restaurant.aop.aspect;

import com.govtech.restaurant.aop.annotation.SessionInfoInjector;
import com.govtech.restaurant.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Aspect
@Component
public class SessionInfoInjectorAspect {

    private final ExpressionParser parser;

    public SessionInfoInjectorAspect() {
        this.parser = new SpelExpressionParser();
    }

    @Around("@annotation(com.govtech.restaurant.aop.annotation.SessionInfoInjector)")
    public Object inject(ProceedingJoinPoint joinPoint) throws Throwable {
        SessionInfoInjector sessionInfoInjector = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SessionInfoInjector.class);
        Object[] args = joinPoint.getArgs();
        injectToMDC(Constants.SESSION_ID, sessionInfoInjector.sessionId(), args);
        return joinPoint.proceed();
    }

    private void injectToMDC(String key, String valueHolder, Object[] args) {
        String sessionId = getMdcParameter(valueHolder, args);
        if (StringUtils.hasText(sessionId)) {
            MDC.put(key, sessionId);
        }
    }

    private String getMdcParameter(String key, Object[] args) {
        try {
            if (StringUtils.hasText(key)) {
                Object value = parser.parseExpression(key).getValue(args);
                if (value != null) {
                    return value.toString();
                }
                return Constants.EMPTY_STRING;
            }
        } catch (Exception ex) {
            log.debug("Could not able to parse");
            return key;
        }
        return Constants.EMPTY_STRING;
    }
}
