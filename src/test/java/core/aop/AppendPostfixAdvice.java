package core.aop;

public class AppendPostfixAdvice implements Advice {
    public static final String POSTFIX = "postFix";

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String result = (String) invocation.proceed();
        return result + POSTFIX;
    }
}
