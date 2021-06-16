package com.openclassrooms.mareu.ui.show;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.repository.MasterDetailRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils;

public class ShowMeetingFragmentViewModel extends ViewModel {

    private final MediatorLiveData<ShowMeetingFragmentViewState> mShowMeetingFragmentItemLiveData = new MediatorLiveData<>();

    public ShowMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull MasterDetailRepository masterDetailRepository) {

        mShowMeetingFragmentItemLiveData.addSource(
                masterDetailRepository.getCurrentDetailIdLiveData(),
                id -> {
                    Meeting meeting = meetingRepository.getMeetingById(id);
                    if (meeting == null)
                        throw new NullPointerException(String.format("Non-existent meeting, id %d", id));

                    mShowMeetingFragmentItemLiveData.setValue(new ShowMeetingFragmentViewState(
                            String.valueOf(meeting.getId()),
                            meeting.getOwner(),
                            meeting.getTopic(),
                            utils.niceTimeFormat(meeting.getStart()),
                            utils.niceTimeFormat(meeting.getEnd()),
                            meeting.getRoom().getName(),
                            meeting.getParticipants().toArray(new String[0])
                    ));
                });
    }

    @NonNull
    public LiveData<ShowMeetingFragmentViewState> getShowMeetingFragmentItem() {
        return mShowMeetingFragmentItemLiveData;
    }
}
