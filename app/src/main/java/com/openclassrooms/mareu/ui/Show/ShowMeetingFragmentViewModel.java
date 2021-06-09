package com.openclassrooms.mareu.ui.Show;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.repository.CurrentMeetingIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.utils;

public class ShowMeetingFragmentViewModel extends ViewModel {

    private final MeetingsRepository mRepository;

    private final MediatorLiveData<ShowMeetingFragmentViewState> mShowMeetingFragmentItemLiveData = new MediatorLiveData<>();

    public ShowMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull CurrentMeetingIdRepository currentMeetingIdRepository) {
        mRepository = meetingRepository;

        mShowMeetingFragmentItemLiveData.addSource(
                currentMeetingIdRepository.getCurrentIdMutableLiveData(),
                id -> {
                    Meeting meeting = mRepository.getMeetingById(id);
                    if (meeting == null)
                        throw new NullPointerException("Inexistent meeting, id " + id);

                    mShowMeetingFragmentItemLiveData.setValue(new ShowMeetingFragmentViewState(
                            String.valueOf(meeting.getId()),
                            meeting.getOwner(),
                            meeting.getSubject(),
                            utils.niceTimeFormat(meeting.getStart()),
                            utils.niceTimeFormat(meeting.getStop()),
                            meeting.getRoom() != null ? meeting.getRoom().getName() : String.valueOf(R.string.hint_meeting_room),
                            meeting.getParticipants().toArray(new String[0])
                    ));
                });

    }

    public LiveData<ShowMeetingFragmentViewState> getShowMeetingFragmentItem() {
        return mShowMeetingFragmentItemLiveData;
    }
}
