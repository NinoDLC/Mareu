package com.openclassrooms.mareu.ui.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MasterDetailRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    private final MeetingsRepository mMeetingsRepository;
    private final MasterDetailRepository mMasterDetailRepository;

    private final MediatorLiveData<List<MainFragmentViewState>> mMutableMeetingsLiveData = new MediatorLiveData<>();

    private final LiveData<List<Meeting>> mMeetingListMutableLivedata;
    private final MutableLiveData<boolean[]> mSelectedRoomsMutableLivedata = new MutableLiveData<>();
    private final MutableLiveData<LocalDateTime> mSelectedTimeMutableLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(
            @NonNull MeetingsRepository meetingsRepository,
            @NonNull MasterDetailRepository masterDetailRepository) {
        mMeetingsRepository = meetingsRepository;
        mMasterDetailRepository = masterDetailRepository;
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

    @NonNull
    private List<MainFragmentViewState> filterMeetings(List<Meeting> meetings, LocalDateTime timeFilter, boolean[] roomFilter) {
        if (meetings == null || roomFilter == null)
            throw new IllegalStateException("uninitialized LiveData");

        List<MainFragmentViewState> itemsList = new ArrayList<>();

        if (timeFilter == null) {
            for (Meeting meeting : meetings) {
                if (roomFilter[meeting.getRoom().ordinal()])
                    itemsList.add(toViewState(meeting));
            }
        } else {
            for (Meeting meeting : meetings) {
                if (roomFilter[meeting.getRoom().ordinal()]
                        && meeting.getStart().isBefore(timeFilter)
                        && meeting.getStop().isAfter(timeFilter))
                    itemsList.add(toViewState(meeting));
            }
        }
        return itemsList;
    }

    @NonNull
    private MainFragmentViewState toViewState(@NonNull Meeting meeting) {
        return new MainFragmentViewState(
                meeting.getId(),
                MessageFormat.format("{0} - {1}",
                        utils.niceTimeFormat(meeting.getStart()),
                        meeting.getSubject()
                ),
                meeting.getOwner(),
                MessageFormat.format("+{0}", meeting.getParticipants().size()),
                meeting.getRoom().getName(),
                meeting.getRoom().getTextColor()
        );
    }

    public void setDetailId(int id) {
        mMasterDetailRepository.setCurrentId(id);
    }

    protected void deleteButtonClicked(int id) {
        mMeetingsRepository.removeMeetingById(id);
    }

    @NonNull
    public CharSequence[] getMeetingRoomNames() {
        CharSequence[] names = new CharSequence[MeetingRoom.values().length];
        for (MeetingRoom meetingRoom : MeetingRoom.values())
            names[meetingRoom.ordinal()] = meetingRoom.getName();
        return names;
    }

    @NonNull
    public LiveData<boolean[]> getRoomFilter() {
        return mSelectedRoomsMutableLivedata;
    }

    public void resetRoomFilter() {
        boolean[] state = new boolean[MeetingRoom.values().length];
        Arrays.fill(state, true);
        mSelectedRoomsMutableLivedata.setValue(state);
    }

    public void setRoomFilter(int position, boolean checked) {
        boolean[] state = mSelectedRoomsMutableLivedata.getValue();
        if (state == null) throw new IllegalStateException("uninitialized room filter");
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
