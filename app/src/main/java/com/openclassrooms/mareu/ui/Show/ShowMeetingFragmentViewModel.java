package com.openclassrooms.mareu.ui.Show;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentMeetingIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils.utils;

import java.util.HashMap;

public class ShowMeetingFragmentViewModel extends ViewModel {

    private final MeetingsRepository mRepository;

    private final HashMap<Integer, MeetingRoom> mMeetingRooms;
    private final CurrentMeetingIdRepository mCurrentMeetingIdRepository;

    private final MutableLiveData<ShowMeetingFragmentItem> mShowMeetingFragmentItemLiveData = new MutableLiveData<>();

    public ShowMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull CurrentMeetingIdRepository currentMeetingIdRepository) {
        mRepository = meetingRepository;
        mCurrentMeetingIdRepository = currentMeetingIdRepository;
        mMeetingRooms = mRepository.getMeetingRooms();
    }

    public LiveData<ShowMeetingFragmentItem> getShowMeetingFragmentItem() {
        // Maps work for now because MeetingRepo doesn't use LiveData
        return Transformations.switchMap(
                mCurrentMeetingIdRepository.getCurrentIdMutableLiveData(),
                id -> {
                    Meeting meeting = mRepository.getMeetingById(id);
                    if (meeting == null)
                        throw new NullPointerException("Inexistent meeting, id " + id);

                    MeetingRoom meetingRoom = mMeetingRooms.get(meeting.getMeetingRoomId());
                    // get() indeed returns null when key is not mapped to something.

                    mShowMeetingFragmentItemLiveData.setValue(new ShowMeetingFragmentItem(
                            String.valueOf(meeting.getId()),
                            meeting.getOwner(),
                            meeting.getSubject(),
                            utils.niceTimeFormat(meeting.getStart()),
                            utils.niceTimeFormat(meeting.getStop()),
                            meetingRoom != null ? meetingRoom.getName() : String.valueOf(R.string.hint_meeting_room),
                            meeting.getParticipants().toArray(new String[0]),
                            meeting.getStart().getHour(),
                            meeting.getStart().getMinute(),
                            meeting.getStop().getHour(),
                            meeting.getStop().getMinute()));
                    return mShowMeetingFragmentItemLiveData;
                });
    }
}
