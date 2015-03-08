package com.truecaller.di;

import com.truecaller.di.annotated.*;
import org.picocontainer.Startable;

import javax.inject.Inject;
import java.util.Set;

public class GuiceServerRegistrar implements Startable {

    private final Server server;

    private final Set<Resource> resources;

    // automatic injecton of all resources
    @Inject
    public GuiceServerRegistrar(Server server, Set<Resource> resources) {
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
}
