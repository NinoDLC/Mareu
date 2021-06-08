package com.openclassrooms.mareu.ui.Show;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentMeetingIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.utils;

import java.util.HashMap;

public class ShowMeetingFragmentViewModel extends ViewModel {

    private final MeetingsRepository mRepository;

    private final HashMap<Integer, MeetingRoom> mMeetingRooms;

    private final MediatorLiveData<ShowMeetingFragmentViewState> mShowMeetingFragmentItemLiveData = new MediatorLiveData<>();

    public ShowMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull CurrentMeetingIdRepository currentMeetingIdRepository) {
        mRepository = meetingRepository;
        mMeetingRooms = mRepository.getMeetingRooms();

        mShowMeetingFragmentItemLiveData.addSource(
                currentMeetingIdRepository.getCurrentIdMutableLiveData(),
                id -> {
                    Meeting meeting = mRepository.getMeetingById(id);
                    if (meeting == null)
                        throw new NullPointerException("Inexistent meeting, id " + id);

                    MeetingRoom meetingRoom = mMeetingRooms.get(meeting.getMeetingRoomId());
                    // get() indeed returns null when key is not mapped to something.

                    mShowMeetingFragmentItemLiveData.setValue(new ShowMeetingFragmentViewState(
                            String.valueOf(meeting.getId()),
                            meeting.getOwner(),
                            meeting.getSubject(),
                            utils.niceTimeFormat(meeting.getStart()),
                            utils.niceTimeFormat(meeting.getStop()),
                            meetingRoom != null ? meetingRoom.getName() : String.valueOf(R.string.hint_meeting_room),
                            meeting.getParticipants().toArray(new String[0])
                    ));
                });

    }

    public LiveData<ShowMeetingFragmentViewState> getShowMeetingFragmentItem() {
        return mShowMeetingFragmentItemLiveData;
    }
}
