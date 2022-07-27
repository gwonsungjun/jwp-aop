package core.aop;

public class ConvertToUpperCaseAdvice implements Advice {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String result = (String) invocation.proceed();
        return result.toUpperCase();
    }
}
