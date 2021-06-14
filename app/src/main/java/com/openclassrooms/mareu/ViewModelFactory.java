package com.openclassrooms.mareu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.mareu.repository.MasterDetailRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.add.AddMeetingFragmentViewModel;
import com.openclassrooms.mareu.ui.main.MainFragmentViewModel;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragmentViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                            new MeetingsRepository(),
                            new MasterDetailRepository()
                    );
                }
            }
        }

        return factory;
    }

    @NonNull
    private final MeetingsRepository meetingRepository;
    @NonNull
    private final MasterDetailRepository masterDetailRepository;

    private ViewModelFactory(@NonNull MeetingsRepository meetingRepository,
                             @NonNull MasterDetailRepository masterDetailRepository) {
        this.meetingRepository = meetingRepository;
        this.masterDetailRepository = masterDetailRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel(meetingRepository, masterDetailRepository);
        } else if (modelClass.isAssignableFrom(ShowMeetingFragmentViewModel.class)) {
            return (T) new ShowMeetingFragmentViewModel(meetingRepository, masterDetailRepository);
        } else if (modelClass.isAssignableFrom(AddMeetingFragmentViewModel.class)) {
            return (T) new AddMeetingFragmentViewModel(meetingRepository, masterDetailRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    // todo: Nino, t'as mieux?
    @NonNull
    public MasterDetailRepository getMasterDetailRepositoryInstance() {
        return masterDetailRepository;
    }

    @NonNull
    public MeetingsRepository getMeetingRepository() {
        return meetingRepository;
    }
}