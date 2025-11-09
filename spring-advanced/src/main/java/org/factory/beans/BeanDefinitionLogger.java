package org.factory.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Slf4j
@Component
public class BeanDefinitionLogger implements BeanDefinitionRegistryPostProcessor {

    private static final String ROOT_PACKAGE = "org.manuaway.factory";
    private static final Set<String> IMPORTANT_BEAN_NAMES = Set.of(
            "paymentConfig",
            "paymentGateway",
            "paymentProcessor",
            "stripeGateway",
            "paypalGateway"
    );

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
            throws BeansException {

        log.info("=== [BeanDefinitionRegistry] Selected bean definitions ===");

        Arrays.stream(registry.getBeanDefinitionNames())
                .sorted()
                .forEach(name -> {
                    BeanDefinition bd = registry.getBeanDefinition(name);
                    String className = bd.getBeanClassName();

                    // Decide if this bean is relevant enough to log
                    if (!shouldLogBean(name, className)) {
                        return;
                    }

                    String scope = (bd.getScope() == null || bd.getScope().isEmpty())
                            ? "singleton"
                            : bd.getScope();

                    log.info("beanName={}, class={}, scope={}, lazyInit={}",
                            name, className, scope, bd.isLazyInit());
                });
    }

    private boolean shouldLogBean(String beanName, String className) {
        // Always log the important beans for the example
        if (IMPORTANT_BEAN_NAMES.contains(beanName)) {
            return true;
        }
        // Also log any bean whose class is in *your* packages
        return className != null && className.startsWith(ROOT_PACKAGE);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        // nothing for now
    }
}
