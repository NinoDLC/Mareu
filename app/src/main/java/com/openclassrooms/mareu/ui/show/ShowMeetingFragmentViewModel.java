package com.openclassrooms.mareu.ui.show;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.repository.MasterDetailRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils;

import java.util.List;

public class ShowMeetingFragmentViewModel extends ViewModel {

    private final LiveData<ShowMeetingFragmentViewState> mShowMeetingFragmentItemLiveData;
    private final MeetingsRepository mMeetingsRepository;

    public ShowMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull MasterDetailRepository masterDetailRepository) {

        mMeetingsRepository = meetingRepository;
        mShowMeetingFragmentItemLiveData = Transformations.map(
                Transformations.switchMap(
                        masterDetailRepository.getCurrentDetailIdLiveData(),
                        this::idToMeetingLiveData
                ), this::meetingToViewState);
    }

    @NonNull
    public LiveData<ShowMeetingFragmentViewState> getShowMeetingFragmentItem() {
        return mShowMeetingFragmentItemLiveData;
    }

    // master detail would be complex here, as we can suppress the Meeting we see in detail !
    @NonNull
    private LiveData<Meeting> idToMeetingLiveData(int id) {
        List<Meeting> meetingList = mMeetingsRepository.getMeetings().getValue();
        if (meetingList == null)
            throw new IllegalStateException("null livedata");

        MutableLiveData<Meeting> liveData = new MutableLiveData<>();
        for (Meeting meeting : meetingList) {
            if (meeting != null && meeting.getId() == id)
                liveData.setValue(meeting);
        }
        return liveData;
    }

    @NonNull
    private ShowMeetingFragmentViewState meetingToViewState(@NonNull Meeting meeting){
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


