package core.aop;

@FunctionalInterface
public interface MethodInvocation {
    Object proceed() throws Throwable;
}
