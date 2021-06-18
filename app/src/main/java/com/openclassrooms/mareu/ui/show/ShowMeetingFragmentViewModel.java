package com.openclassrooms.mareu.ui.show;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils;

import java.util.List;

public class ShowMeetingFragmentViewModel extends ViewModel {

    private final LiveData<ShowMeetingFragmentViewState> mShowMeetingFragmentItemLiveData;
    private final MeetingsRepository mMeetingsRepository;

    public ShowMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull CurrentIdRepository currentIdRepository) {

        mMeetingsRepository = meetingRepository;
        mShowMeetingFragmentItemLiveData = Transformations.map(
                currentIdRepository.getCurrentIdLiveData(),
                id -> meetingToViewState(idToMeetingLiveData(id))
        );
    }

    @NonNull
    public LiveData<ShowMeetingFragmentViewState> getShowMeetingFragmentItem() {
        return mShowMeetingFragmentItemLiveData;
    }

    // master detail would be complex here, as we can suppress the Meeting we see in detail !
    @NonNull
    private Meeting idToMeetingLiveData(int id) {
        List<Meeting> meetingList = mMeetingsRepository.getMeetings().getValue();
        if (meetingList == null)
            throw new IllegalStateException("null livedata");

        for (Meeting meeting : meetingList) {
            if (meeting.getId() == id)
                return meeting;
        }
        throw new IllegalStateException("no such meeting");
    }

    @NonNull
    private ShowMeetingFragmentViewState meetingToViewState(@NonNull Meeting meeting) {
        return new ShowMeetingFragmentViewState(
                String.valueOf(meeting.getId()),
                meeting.getOwner(),
                meeting.getTopic(),
                utils.niceTimeFormat(meeting.getStart()),
                utils.niceTimeFormat(meeting.getEnd()),
                meeting.getRoom().getName(),
                meeting.getParticipants().toArray(new String[0])
        );
    }

}


