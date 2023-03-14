package kg.tech.tradebackend.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    @Before("execution (* kg.tech.tradebackend.controllers.*.*(..))")
    public void logging(JoinPoint joinPoint) {
        log.info("CONTROLLER: {}, ARGS: {}", joinPoint.getSignature().toShortString(), Arrays.asList(joinPoint.getArgs()));
    }
}
