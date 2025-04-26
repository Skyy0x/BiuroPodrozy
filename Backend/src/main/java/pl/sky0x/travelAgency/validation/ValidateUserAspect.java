package pl.sky0x.travelAgency.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
@Component
public class ValidateUserAspect {

    @Before("@annotation(pl.sky0x.travelAgency.validation.ValidateUser)")
    public void validateUserLoggedIn(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Method method = Arrays.stream(joinPoint.getSignature()
                        .getDeclaringType()
                        .getDeclaredMethods())
                .filter(m -> m.getName().equals(joinPoint.getSignature().getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Method not found."));

        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(AuthenticationPrincipal.class)) {
                if (args[i] == null) {
                    throw new BadCredentialsException("User isn't logged in. GOOD CLASS");
                }
                break;
            }
        }
    }
}