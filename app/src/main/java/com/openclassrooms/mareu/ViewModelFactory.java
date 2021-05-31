package com.openclassrooms.mareu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.mareu.repository.LocalMeetingsRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                        new LocalMeetingsRepository()
                    );
                }
            }
        }

        return factory;
    }

    @NonNull
    private final MeetingsRepository meetingRepository;

    private ViewModelFactory(@NonNull MeetingsRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(meetingRepository);
        } else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(meetingRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}