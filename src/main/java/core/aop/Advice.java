package core.aop;

public interface Advice {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
