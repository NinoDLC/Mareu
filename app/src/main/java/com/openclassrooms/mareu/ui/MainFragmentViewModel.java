package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentMeetingIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils.utils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.copyOf;

public class MainFragmentViewModel extends ViewModel {

    private final MeetingsRepository mMeetingsRepository;
    private final CurrentMeetingIdRepository mCurrentMeetingIdRepository;
    // todo when going with livedata on repo, remove all setvalues()

    private final MutableLiveData<List<MeetingsRecyclerViewAdapterItem>> mMutableMeetingsLiveData = new MutableLiveData<>();

    private final HashMap<Integer, MeetingRoom> mMeetingRooms;

    private final boolean[] mSelectedRooms;

    @Nullable
    private LocalDateTime mTimeFilter;

    private final MutableLiveData<boolean[]> mMutableSelectedRoomsLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(
            @NonNull MeetingsRepository meetingsRepository,
            @NonNull CurrentMeetingIdRepository currentMeetingIdRepository) {
        mMeetingsRepository = meetingsRepository;
        mCurrentMeetingIdRepository = currentMeetingIdRepository;

        mMeetingRooms = mMeetingsRepository.getMeetingRooms();
        mSelectedRooms = new boolean[mMeetingRooms.size()];
        resetRoomFilter();
    }

    public LiveData<List<MeetingsRecyclerViewAdapterItem>> getMeetingsLiveData() {
        return mMutableMeetingsLiveData;
    }

    public void setDetailId(int id) {
        mCurrentMeetingIdRepository.setCurrentId(id);
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
        mMeetingsRepository.removeMeetingById(id);
        updateMeetingsList();
    }

    private void updateMeetingsList() {
        // TODO new ArrayList no longer necessary when repo is liveData.
        List<MeetingsRecyclerViewAdapterItem> itemsList = new ArrayList<>();
        for (Meeting meeting : mMeetingsRepository.getMeetings()) {
            if (mSelectedRooms[meeting.getMeetingRoomId() - 1] && meetsTimeConditions(meeting))
                itemsList.add(makeRecyclerViewItem(meeting));
        }
        mMutableMeetingsLiveData.setValue(new ArrayList<>(itemsList));
    }

    private MeetingsRecyclerViewAdapterItem makeRecyclerViewItem(@NonNull Meeting meeting) {
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
                mr.getTextColor()  // todo could I store the color itself?
        );
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

    public int getTimeFilterHour() {
        return mTimeFilter == null ? LocalDateTime.now().getHour() : mTimeFilter.getHour();
    }

    public int getTimeFilterMinute() {
        return mTimeFilter == null ? LocalDateTime.now().getMinute() : mTimeFilter.getMinute();
    }

}
