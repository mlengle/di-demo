package com.truecaller.di.annotated;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by me on 07.03.2015.
 */
@Component
public class Server {
    private List resources = new ArrayList();

    public void register(Object resource) {
        resources.add(resource);
    }

    public void start() {
        for (Object resource : resources) {
            System.out.println("Starting resource: " + resource.toString());
        }
    }

    public void stop() {

    }
}
