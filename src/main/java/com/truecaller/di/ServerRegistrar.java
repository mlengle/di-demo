package com.truecaller.di;

import org.picocontainer.Startable;
import com.truecaller.di.plain.*;

// works for both Pico and Spring
public class ServerRegistrar implements Startable {

    private final Server server;

    private final Resource[] resources;

    // automatic injecton of all resources
    public ServerRegistrar(Server server, Resource[] resources) {
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
