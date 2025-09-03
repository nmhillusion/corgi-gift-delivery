package tech.nmhillusion.corgi_gift_delivery.helper;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-09-03
 */
@Component
public class BeanHelper {
    private final BeanFactory beanFactory;

    public BeanHelper(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public <T> T injectForService(Class<T> class2Inject) {
        return beanFactory.getBean(class2Inject);
    }
}
