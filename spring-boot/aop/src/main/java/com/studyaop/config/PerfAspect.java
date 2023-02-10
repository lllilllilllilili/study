package com.studyaop.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerfAspect {

    @Around("@annotation(PerLogging)")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable{
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }
}
