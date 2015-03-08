package com.truecaller.di;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.truecaller.di.annotated.*;

/**
 * Created by me on 07.03.2015.
 */
public class AppGuice {

    static class SetupModule extends AbstractModule {
        private final Configuration configuration;

        public SetupModule(Configuration configuration) {
            this.configuration = configuration;
        }

        @Override
        protected void configure() {
            // add simple instance
            bind(Configuration.class).toInstance(configuration);
            // add implementations (any order)
            bind(DaoFactory.class).in(Singleton.class);
            bind(ProfileService.class).in(Singleton.class);
            bind(SpamService.class).in(Singleton.class);
            bind(SearchService.class).in(Singleton.class);
            bind(SomeFilterService.class).in(Singleton.class);
            bind(Server.class).in(Singleton.class);

            // we need extra step to provide collection of Resources as injectable
            Multibinder<Resource> multiBinder = Multibinder.newSetBinder(binder(), Resource.class);
            // we have to add involved resources explicitly
            multiBinder.addBinding().to(ProfileResource.class).in(Singleton.class);
            multiBinder.addBinding().to(SearchResource.class).in(Singleton.class);
            // add convenience class to register server resources
            bind(GuiceServerRegistrar.class).in(Singleton.class);
        }
    }

    public void init() {
        Injector injector = Guice.createInjector(new SetupModule(new Configuration()));
        GuiceServerRegistrar registrar = injector.getInstance(GuiceServerRegistrar.class);
        // lifecycles are harder to implement in guice
        registrar.start();
    }

    public static void main(String[] arg) {
        new AppGuice().init();
    }
}
