package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.copyOf;

public class MainFragmentViewModel extends ViewModel {

    private final MeetingsRepository mRepository;
    // todo : repo is to be injected, for this I must customize my factory
    //  but it will only hand me the repo, and I made a DI that can return the instance...
    //  then remove all setvalues()

    private final MutableLiveData<List<MeetingsRecyclerViewAdapterItem>> mMutableMeetingsLiveData = new MutableLiveData<>();

    private final HashMap<Integer, MeetingRoom> mMeetingRooms;

    private final boolean[] mSelectedRooms;

    private LocalDateTime mTimeFilter;

    private final MutableLiveData<boolean[]> mMutableSelectedRoomsLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(@NonNull MeetingsRepository meetingsRepository) {
        mRepository = meetingsRepository;
        mMeetingRooms = mRepository.getMeetingRooms();
        mSelectedRooms = new boolean[mMeetingRooms.size()];
        resetRoomFilter();
    }

    public LiveData<List<MeetingsRecyclerViewAdapterItem>> getMeetingsLiveData() {
        return mMutableMeetingsLiveData;
    }

    public HashMap<Integer, MeetingRoom> getMeetingRooms() {
        return mMeetingRooms;
    }

    public CharSequence[] getMeetingRoomNames() {
        List<CharSequence> meetingRoomNames = new ArrayList<>();
        for (MeetingRoom meetingRoom : mMeetingRooms.values()) {
            meetingRoomNames.add(meetingRoom.getName());
        }
        return meetingRoomNames.toArray(new CharSequence[0]);
    }

    public LiveData<boolean[]> getSelectedRooms() {
        return mMutableSelectedRoomsLiveData;
    }

    public void toggleRoomSelection(int position) {
        mSelectedRooms[position] = !mSelectedRooms[position];
        mMutableSelectedRoomsLiveData.setValue(copyOf(mSelectedRooms, mSelectedRooms.length));
        updateMeetingsList();
    }

    public void resetRoomFilter() {
        Arrays.fill(mSelectedRooms, true);
        mMutableSelectedRoomsLiveData.setValue(copyOf(mSelectedRooms, mSelectedRooms.length));
        updateMeetingsList();
    }

    protected void deleteButtonClicked(int id) {
        mRepository.removeMeetingById(id);
        updateMeetingsList();
    }

    private void updateMeetingsList() {
        // TODO new ArrayList no longer necessary when repo is liveData.
        List<MeetingsRecyclerViewAdapterItem> itemsList = new ArrayList<>();
        for (Meeting meeting : mRepository.getMeetings()) {
            if (mSelectedRooms[meeting.getMeetingRoomId() - 1] && meetsTimeConditions(meeting))
                itemsList.add(makeRecyclerViewItem(meeting));
        }
        mMutableMeetingsLiveData.setValue(new ArrayList<>(itemsList));
    }

    MeetingsRecyclerViewAdapterItem makeRecyclerViewItem(Meeting meeting) {
        MeetingRoom mr = mMeetingRooms.get(meeting.getMeetingRoomId());
        if (mr == null)
            throw new NullPointerException("null MeetingRoom object");
        return new MeetingsRecyclerViewAdapterItem(
                meeting.getId(),
                MessageFormat.format("{0} - {1}",
                        utils.niceTimeFormat(meeting.getStart()),
                        meeting.getSubject()
                ),
                meeting.getOwner(),
                MessageFormat.format("+{0}", meeting.getParticipants().size()),
                mr.getName(),
                mr.getTextColor()
        );

                // todo could I store the color itself?
    }


    private boolean meetsTimeConditions(@NonNull Meeting meeting) {
        if (mTimeFilter == null) return true;
        return meeting.getStart().isBefore(mTimeFilter) && meeting.getStop().isAfter(mTimeFilter);
    }

    public void setTimeFilter(int hourOfDay, int minute) {
        mTimeFilter = LocalDateTime.now().withHour(hourOfDay).withMinute(minute).withSecond(1);
        updateMeetingsList();
    }

    public void resetTimeFilter() {
        mTimeFilter = null;
        updateMeetingsList();
    }

}
