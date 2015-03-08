package com.truecaller.di;

import com.truecaller.di.plain.*;
import org.picocontainer.Startable;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.Collection;
import java.util.Map;

/**
 * Created by me on 07.03.2015.
 */
public class AppSpring {

    private static class SpringContainer implements Startable {
        private DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        public SpringContainer addComponent(Object component) {
            if (component instanceof Class) {
                Class<?> clazz = (Class<?>) component;
                GenericBeanDefinition beanDef = new GenericBeanDefinition();
                beanDef.setBeanClass(clazz);
                beanDef.setAutowireCandidate(true);
                beanDef.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
                String beanName = clazz.getName();
                beanFactory.registerBeanDefinition(beanName, beanDef);
            } else {
                beanFactory.registerSingleton(component.getClass().getName(), component);
            }
            return this;
        }

        public <T> T getComponent(Class<T> componentType) {
            return beanFactory.getBean(componentType);
        }

        public <T> Collection<T> getComponents(Class<T> componentType) {
            Map<String, T> beans = beanFactory.getBeansOfType(componentType);
            return beans.values();
        }

        @Override
        public void start() {
            Collection<Startable> startables = getComponents(Startable.class);
            for (Startable startable : startables) {
                startable.start();
            }
        }

        @Override
        public void stop() {
            Collection<Startable> startables = getComponents(Startable.class);
            for (Startable startable : startables) {
                startable.stop();
            }
        }
    }

    private SpringContainer setUpContainer(Configuration configuration) {
        SpringContainer container = new SpringContainer();
        // add simple instance
        container.addComponent(configuration);
        // add implementations (any order)
        container.addComponent(DaoFactory.class);
        container.addComponent(ProfileService.class);
        container.addComponent(SearchService.class);
        container.addComponent(SpamService.class);
        container.addComponent(SomeFilterService.class);
        container.addComponent(ProfileResource.class);
        container.addComponent(SearchResource.class);
        container.addComponent(Server.class);
        // add convenience class to register server resources
        container.addComponent(ServerRegistrar.class);

        return container;
    }

    public void init() {
        SpringContainer container = setUpContainer(new Configuration());
        // start lifecycle methods
        container.start();
    }

    public static void main(String[] arg) {
        new AppSpring().init();
    }
}
