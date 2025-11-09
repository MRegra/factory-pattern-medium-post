package org.factory.beans;

import lombok.extern.slf4j.Slf4j;
import org.factory.domain.PaymentProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TracingBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof PaymentProcessor) {
            log.info("[BeanPostProcessor] Before init: {} ({})",
                    beanName, bean.getClass().getSimpleName());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof PaymentProcessor) {
            log.info("[BeanPostProcessor] After init: {} ({})",
                    beanName, bean.getClass().getSimpleName());
        }
        return bean;
    }
}
