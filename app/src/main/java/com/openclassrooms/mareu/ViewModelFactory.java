package com.openclassrooms.mareu;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.add.AddMeetingFragmentViewModel;
import com.openclassrooms.mareu.ui.main.MainActivityViewModel;
import com.openclassrooms.mareu.ui.main.MainFragmentViewModel;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragmentViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                            MainApplication.getApplication(),
                            new MeetingsRepository(),
                            new CurrentIdRepository()
                    );
                }
            }
        }

        return factory;
    }

    @NonNull
    private final Application application;
    @NonNull
    private final MeetingsRepository meetingRepository;
    @NonNull
    private final CurrentIdRepository mCurrentIdRepository;

    private ViewModelFactory(@NonNull Application application,
                             @NonNull MeetingsRepository meetingRepository,
                             @NonNull CurrentIdRepository currentIdRepository) {
        this.application = application;
        this.meetingRepository = meetingRepository;
        this.mCurrentIdRepository = currentIdRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(mCurrentIdRepository);
        } else if (modelClass.isAssignableFrom(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel(meetingRepository, mCurrentIdRepository);
        } else if (modelClass.isAssignableFrom(ShowMeetingFragmentViewModel.class)) {
            return (T) new ShowMeetingFragmentViewModel(meetingRepository, mCurrentIdRepository);
        } else if (modelClass.isAssignableFrom(AddMeetingFragmentViewModel.class)) {
            return (T) new AddMeetingFragmentViewModel(application, meetingRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}