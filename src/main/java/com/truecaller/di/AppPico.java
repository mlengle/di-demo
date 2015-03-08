package com.truecaller.di;

import com.truecaller.di.plain.*;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;

/**
 * Created by me on 07.03.2015.
 */
public class AppPico {

    private MutablePicoContainer setUpContainer(Configuration configuration) {
        // Caching is analog of singleton scope in other containers
        MutablePicoContainer container = new DefaultPicoContainer(new Caching());

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
        MutablePicoContainer container = setUpContainer(new Configuration());
        // start all Startable within the container
        container.start();
    }

    public static void main(String[] arg) {
        new AppPico().init();
    }
}
