package com.example.lesson4.aspect;

import com.example.lesson4.Authorized;
import com.example.lesson4.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthorizationAspect {
    @Before("@annotation(authorized)")
    public void beforeMethodWithAnnotation(JoinPoint joinPoint, Authorized authorized) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException("Something broken");
        }

        HttpServletRequest request = attributes.getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || authorization.isEmpty()) {
            throw new UnauthorizedException();
        }
    }
}
