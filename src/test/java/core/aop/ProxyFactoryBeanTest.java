package core.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static core.aop.AppendPostfixAdvice.POSTFIX;
import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryBeanTest {

    private ProxyFactoryBean proxyFactoryBean;

    @BeforeEach
    public void setup() {
        proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new AopTestService());
    }


    @DisplayName("toUpperCaseAdvice 테스트")
    @Test
    void testToUpperCaseAdvice() {
        String name = "sungjun";
        Pointcut pointcut = new MethodNamePrefixMatchPointCut("sayHello");
        Advice advice = new ConvertToUpperCaseAdvice();

        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, advice));
        AopTestService aopTestService = (AopTestService) proxyFactoryBean.getObject();

        String result = aopTestService.sayHello(name);

        assertThat(result).isEqualTo("HELLO " + name.toUpperCase());
    }

    @DisplayName("복합 Advice 테스트")
    @Test
    void testMultipleAdvices() {
        String name = "sungjun";
        Pointcut pointcut = new MethodNamePrefixMatchPointCut("sayHello");

        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new ConvertToUpperCaseAdvice()));
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new AppendPostfixAdvice()));

        AopTestService aopTestService = (AopTestService) proxyFactoryBean.getObject();

        String result = aopTestService.sayHello(name);

        String expected = "HELLO " + name.toUpperCase() + POSTFIX;
        assertThat(result).isEqualTo(expected.toUpperCase());
    }
}
