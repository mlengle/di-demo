package com.truecaller.di.annotated;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by me on 07.03.2015.
 */
@Component
public class SearchService {
    @Inject
    public SearchService(DaoFactory daoFactory, SpamService spamService, SomeFilterService filterService) {
    }
}
