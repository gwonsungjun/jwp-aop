package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.SayPrefixMethodMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class CglibProxyTest {

    private Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
    }

    @Test
    @DisplayName("반환 값을 같도록 구현하는 Proxy")
    void same_return() {
        enhancer.setCallback((FixedValue) () -> "Hello JavaJiGi!");
        PersonService proxy = (PersonService) enhancer.create();

        String res = proxy.sayHello(null);

        assertThat(res).isEqualTo("Hello JavaJiGi!");
    }

    @Test
    @DisplayName("메소드 signature에 따라 다르게 동작하는 Proxy")
    void method_signature() {
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                return "Hello JavaJiGi!";
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });

        PersonService proxy = (PersonService) enhancer.create();

        assertThat(proxy.sayHello(null)).isEqualTo("Hello JavaJiGi!");
        assertThat(proxy.lengthOfName("SanJiGi")).isEqualTo(7);
    }

    @DisplayName("HelloTarget 대문자 변환 값을 확인 할 수 있다")
    @Test
    void helloTargetTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            String result = (String) proxy.invokeSuper(obj, args);
            if (new SayPrefixMethodMatcher().matches(method, obj.getClass(), args)) {
                return result.toUpperCase();
            }
            return result;
        });

        HelloTarget actual = (HelloTarget) enhancer.create();

        assertThat(actual.sayHello("sungjun")).isEqualTo("HELLO SUNGJUN");
        assertThat(actual.sayHi("sungjun")).isEqualTo("HI SUNGJUN");
        assertThat(actual.sayThankYou("sungjun")).isEqualTo("THANK YOU SUNGJUN");
        assertThat(actual.pingpong("sungjun")).isEqualTo("Pong sungjun");
    }
}
