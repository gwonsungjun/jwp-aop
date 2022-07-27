package core.aop;

import com.google.common.collect.Lists;
import net.sf.cglib.proxy.Enhancer;

import java.util.List;

public class ProxyFactoryBean implements FactoryBean<Object>{

    private Object target;
    private final List<Advisor> advisors = Lists.newArrayList();


    public void setTarget(Object target) {
        this.target = target;
    }

    public void addAdvisor(Advisor advisor) {
        advisors.add(advisor);
    }

    @Override
    public Object getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getObjectType());
        enhancer.setCallback(new ProxyMethodInterceptor(advisors));
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }
}
