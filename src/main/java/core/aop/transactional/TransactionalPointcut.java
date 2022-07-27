package core.aop.transactional;

import core.annotation.Transactional;
import core.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.Objects;

public class TransactionalPointcut implements Pointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        if (Objects.isNull(method)) {
            return false;
        }

        return method.getDeclaringClass().isAnnotationPresent(Transactional.class) || method.isAnnotationPresent(Transactional.class);
    }
}
