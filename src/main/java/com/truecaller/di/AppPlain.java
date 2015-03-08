package com.truecaller.di;

import com.truecaller.di.plain.*;

/**
 * Created by me on 07.03.2015.
 */
public class AppPlain {
    public void init() {
        DaoFactory daoFactory = new DaoFactory(new Configuration());

        SpamService spamService = new SpamService(daoFactory);
        SomeFilterService filterService = new SomeFilterService(daoFactory);
        SearchService searchService = new SearchService(daoFactory, spamService, filterService);
        ProfileService profileService = new ProfileService(daoFactory, filterService);

        SearchResource searchResource = new SearchResource(searchService);
        ProfileResource profileResource = new ProfileResource(profileService);

        Server server = new Server();
        server.register(searchResource);
        server.register(profileResource);
        server.start();
    }


    public static void main(String[] arg) {
        new AppPlain().init();
    }
}
