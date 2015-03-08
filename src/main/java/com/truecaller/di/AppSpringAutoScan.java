package com.truecaller.di;

import com.truecaller.di.annotated.Configuration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Collections;
import java.util.List;

/**
 * Created by me on 07.03.2015.
 */
public class AppSpringAutoScan {

    @org.springframework.context.annotation.Configuration
    @ComponentScan(basePackages = {"com.truecaller.di.annotated"})
    static class SpringContainerConfiguration {
        // just for example, wasn't really needed in this app
        @Bean
        public Integer createSomeBean() {
            return Integer.valueOf(42);
        }
        // just for example, wasn't really needed in this app
        @Bean
        public List<String> createSomeListBean() {
            return Collections.emptyList();
        }
    }

    private AbstractApplicationContext setUpContainer(Configuration configuration) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // autoscan can be directly performed at this level, but we use register with @Configuration class to demonstrate few features
        // ctx.scan("com.truecaller.di.annotated");
        ctx.register(SpringContainerConfiguration.class);
        ctx.getBeanFactory().registerSingleton(configuration.getClass().getName(), configuration);
        ctx.refresh();
        return ctx;
    }

    public void init() {
        AbstractApplicationContext container = setUpContainer(new Configuration());
        // start lifecycle methods
        container.start();
    }

    public static void main(String[] arg) {
        new AppSpringAutoScan().init();
    }
}
