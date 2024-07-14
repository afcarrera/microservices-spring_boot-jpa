package com.carrera.bank.account.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TransactionAuditInterceptor {

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransaction(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Starting transaction for method: " + methodName);
    }

    @AfterReturning("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void afterSuccessfulTransaction(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Successfully completed transaction for method: " + methodName);
    }

    @AfterThrowing(value = "@annotation(org.springframework.transaction.annotation.Transactional)", throwing = "ex")
    public void afterFailingTransaction(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("Failed transaction for method: " + methodName + ", Exception: " + ex.getMessage());
    }
}