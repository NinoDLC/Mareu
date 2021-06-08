package com.openclassrooms.mareu.ui.Main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentMeetingIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.utils;

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
    private final HashMap<Integer, MeetingRoom> mMeetingRooms;

    private final MediatorLiveData<List<MainFragmentViewState>> mMutableMeetingsLiveData = new MediatorLiveData<>();

    private final LiveData<List<Meeting>> mMeetingListMutableLivedata;
    private final MutableLiveData<boolean[]> mSelectedRoomsMutableLivedata = new MutableLiveData<>();
    private final MutableLiveData<LocalDateTime> mSelectedTimeMutableLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(
            @NonNull MeetingsRepository meetingsRepository,
            @NonNull CurrentMeetingIdRepository currentMeetingIdRepository) {
        mMeetingsRepository = meetingsRepository;
        mCurrentMeetingIdRepository = currentMeetingIdRepository;
        mMeetingRooms = mMeetingsRepository.getMeetingRooms();
        mMeetingListMutableLivedata = mMeetingsRepository.getMeetings();

        resetRoomFilter();

        mMutableMeetingsLiveData.addSource(
                mMeetingListMutableLivedata,
                meetings -> mMutableMeetingsLiveData.setValue(filterMeetings(
                        meetings,
                        mSelectedTimeMutableLiveData.getValue(),
                        mSelectedRoomsMutableLivedata.getValue()
                )));
        mMutableMeetingsLiveData.addSource(
                mSelectedTimeMutableLiveData,
                localDateTime -> mMutableMeetingsLiveData.setValue(filterMeetings(
                        mMeetingListMutableLivedata.getValue(),
                        localDateTime,
                        mSelectedRoomsMutableLivedata.getValue()
                )));
        mMutableMeetingsLiveData.addSource(
                mSelectedRoomsMutableLivedata,
                booleans -> mMutableMeetingsLiveData.setValue(filterMeetings(
                        mMeetingListMutableLivedata.getValue(),
                        mSelectedTimeMutableLiveData.getValue(),
                        booleans
                )));
    }

    public LiveData<List<MainFragmentViewState>> getViewStateListLiveData() {
        return mMutableMeetingsLiveData;
    }

    private List<MainFragmentViewState> filterMeetings(List<Meeting> meetings, LocalDateTime timeFilter, boolean[] roomFilter) {
        if (meetings == null || roomFilter == null)
            throw new IllegalStateException("uninitialized LiveData");

        List<MainFragmentViewState> itemsList = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (roomFilter[meeting.getMeetingRoomId() - 1] && (
                    timeFilter == null ||
                            (meeting.getStart().isBefore(timeFilter) && meeting.getStop().isAfter(timeFilter))))
                itemsList.add(toViewState(meeting));
        }
        return itemsList;
    }

    private MainFragmentViewState toViewState(@NonNull Meeting meeting) {
        MeetingRoom mr = mMeetingRooms.get(meeting.getMeetingRoomId());
        if (mr == null)
            throw new NullPointerException("null MeetingRoom object");
        return new MainFragmentViewState(
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

    public void setDetailId(int id) {
        mCurrentMeetingIdRepository.setCurrentId(id);
    }

    protected void deleteButtonClicked(int id) {
        mMeetingsRepository.removeMeetingById(id);
    }

    public CharSequence[] getMeetingRoomNames() {
        List<CharSequence> meetingRoomNames = new ArrayList<>();
        for (MeetingRoom meetingRoom : mMeetingRooms.values()) {
            meetingRoomNames.add(meetingRoom.getName());
        }
        return meetingRoomNames.toArray(new CharSequence[0]);
    }

    public LiveData<boolean[]> getRoomFilter() {
        return mSelectedRoomsMutableLivedata;
    }

    public void resetRoomFilter() {
        boolean[] state = new boolean[mMeetingRooms.size()];
        Arrays.fill(state, true);
        mSelectedRoomsMutableLivedata.setValue(state);
    }

    public void setRoomFilter(int position, boolean checked) {
        boolean[] temp = mSelectedRoomsMutableLivedata.getValue();
        if (temp == null) throw new IllegalStateException("uninitialized room filter");
        boolean[] state = copyOf(temp,mMeetingRooms.size());
        state[position] = checked;
        mSelectedRoomsMutableLivedata.setValue(state);
    }

    public void setTimeFilter(int hourOfDay, int minute) {
        mSelectedTimeMutableLiveData.setValue(LocalDateTime.now().withHour(hourOfDay).withMinute(minute).withSecond(1));
    }

    public void resetTimeFilter() {
        mSelectedTimeMutableLiveData.setValue(null);
    }

    public int getTimeFilterHour() {
        LocalDateTime dateTime = mSelectedTimeMutableLiveData.getValue();
        return dateTime == null ? LocalDateTime.now().getHour() : dateTime.getHour();
    }

    public int getTimeFilterMinute() {
        LocalDateTime dateTime = mSelectedTimeMutableLiveData.getValue();
        return dateTime == null ? LocalDateTime.now().getMinute() : dateTime.getMinute();
    }
}