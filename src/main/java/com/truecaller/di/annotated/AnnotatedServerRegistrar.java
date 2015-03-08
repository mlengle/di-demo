package com.truecaller.di.annotated;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class AnnotatedServerRegistrar implements Lifecycle {

    private final Server server;

    private final Resource[] resources;

    @Inject
    public AnnotatedServerRegistrar(Server server, Resource[] resources) {
        this.server = server;
        this.resources = resources;
    }

    @Override
    public void start() {
        for (Resource resource : resources) {
            server.register(resource);
        }
        server.start();
    }

    @Override
    public void stop() {
        server.stop();
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
