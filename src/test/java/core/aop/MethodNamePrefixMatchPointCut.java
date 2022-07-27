package core.aop;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class MethodNamePrefixMatchPointCut implements Pointcut {
    private final Set<String> methodNamePrefixes = Sets.newHashSet();

    public MethodNamePrefixMatchPointCut(String... targetMethodNames) {
        methodNamePrefixes.addAll(new HashSet<>(getNonEmptyPrefixes(targetMethodNames)));
    }

    private Set<String> getNonEmptyPrefixes(String[] targetMethodNames) {
        return Arrays.stream(targetMethodNames)
                .filter(StringUtils::isNotEmpty)
                .collect(toSet());
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        if (Objects.isNull(method) || CollectionUtils.isEmpty(methodNamePrefixes)) {
            return false;
        }

        return methodNamePrefixes.stream()
                .filter(StringUtils::isNotEmpty)
                .anyMatch(methodNamePrefix -> method.getName().startsWith(methodNamePrefix));
    }
}
