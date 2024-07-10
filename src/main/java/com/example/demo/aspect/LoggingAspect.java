package com.example.demo.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Entity.Logs;
import com.example.demo.Repo.LogRepository;

@Aspect
@Component
public class LoggingAspect {
    @Autowired
    private LogRepository logRepo;
    
    @Pointcut("execution(* com.example.demo.Controller.*.*(..))[")
    public void controllerMethods() {}
    
    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String endpoint = className + "/" + methodName;
        Object[] args = joinPoint.getArgs();
        String request = args.length > 0 ? args[0].toString() : "No request body";
        System.out.println(endpoint);
        Logs log = new Logs();
        log.setEndpoint(endpoint);
        log.setRequest(request);
        log.setLogTime(LocalDateTime.now());
        
        logRepo.save(log);
    }
    @AfterReturning(pointcut = "execution(* com.example.demo.Controller.*.*(..))", returning = "response")
    public void logAfterReturning(JoinPoint joinPoint, Object response) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String endpoint = className + "/" + methodName;
        
        // Check if response is null
        String responseBody;
        if (response == null) {
            responseBody = "No response body";
        } else {
            try {
                responseBody = response.toString();
            } catch (Exception e) {
                responseBody = "Error retrieving response body: " + e.getMessage();
            }
        } 
        Logs log = new Logs();
        log.setEndpoint(endpoint);
        log.setResponse(responseBody);
        log.setLogTime(LocalDateTime.now());
//        System.out.println(responseBody);
        logRepo.save(log);
        
        
       
    }

}
