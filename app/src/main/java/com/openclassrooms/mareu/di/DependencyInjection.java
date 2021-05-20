package com.openclassrooms.mareu.di;

import com.openclassrooms.mareu.repository.LocalMeetingsRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;

public class DependencyInjection {

    private static final MeetingsRepository repo = new LocalMeetingsRepository();

    public static MeetingsRepository getMeetingsRepository() {
        return repo;
    }

    public static MeetingsRepository getNewInstanceApiService() {
        return new LocalMeetingsRepository();
    }
}
