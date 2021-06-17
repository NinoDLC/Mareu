package com.openclassrooms.mareu.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.openclassrooms.mareu.utils.initMeetings;

public class MainFragmentViewModel extends ViewModel {

    private final MeetingsRepository mMeetingsRepository;
    private final CurrentIdRepository mCurrentIdRepository;

    private final MediatorLiveData<List<MainFragmentViewState>> mMutableViewStateLiveData = new MediatorLiveData<>();

    private final LiveData<List<Meeting>> mMeetingListMutableLivedata;
    private final MutableLiveData<boolean[]> mSelectedRoomsMutableLivedata = new MutableLiveData<>();
    private final MutableLiveData<LocalDateTime> mSelectedTimeMutableLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(
            @NonNull MeetingsRepository meetingsRepository,
            @NonNull CurrentIdRepository currentIdRepository) {
        mMeetingsRepository = meetingsRepository;
        mCurrentIdRepository = currentIdRepository;

        // todo : squash initMeetings()?
        initMeetings(meetingsRepository);  // Unsorted but valid Meetings list.

        mMeetingListMutableLivedata = Transformations.map(
                mMeetingsRepository.getMeetings(),
                input -> {
                    Collections.sort(input, (o1, o2) -> o1.getStart().compareTo(o2.getStart()));
                    return input;
                });

        resetRoomFilter();

        mMutableViewStateLiveData.addSource(
                mMeetingListMutableLivedata,
                meetings -> mMutableViewStateLiveData.setValue(filterMeetings(
                        meetings,
                        mSelectedTimeMutableLiveData.getValue(),
                        mSelectedRoomsMutableLivedata.getValue()
                )));
        mMutableViewStateLiveData.addSource(
                mSelectedTimeMutableLiveData,
                localDateTime -> mMutableViewStateLiveData.setValue(filterMeetings(
                        mMeetingListMutableLivedata.getValue(),
                        localDateTime,
                        mSelectedRoomsMutableLivedata.getValue()
                )));
        mMutableViewStateLiveData.addSource(
                mSelectedRoomsMutableLivedata,
                booleans -> mMutableViewStateLiveData.setValue(filterMeetings(
                        mMeetingListMutableLivedata.getValue(),
                        mSelectedTimeMutableLiveData.getValue(),
                        booleans
                )));
    }

    public LiveData<List<MainFragmentViewState>> getViewStateListLiveData() {
        return mMutableViewStateLiveData;
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
                        && meeting.getEnd().isAfter(timeFilter))
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
                        meeting.getTopic()
                ),
                meeting.getOwner(),
                MessageFormat.format("+{0}", meeting.getParticipants().size()),
                meeting.getRoom().getName(),
                meeting.getRoom().getTextColor()
        );
    }

    public void setDetailId(int id) {
        mCurrentIdRepository.setCurrentId(id);
    }

    public void deleteButtonClicked(int id) {
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
