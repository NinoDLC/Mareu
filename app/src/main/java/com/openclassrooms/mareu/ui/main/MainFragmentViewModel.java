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

public class MainFragmentViewModel extends ViewModel {

    @NonNull
    private static final LocalDateTime ARBITRARY_DAY = LocalDateTime.of(2021, 6, 14, 8, 50);
    // to use LocalDateTime.now(), one must inject a clock, so tests pass, or in my case, not use a DateTime to store only hours.

    @NonNull
    private final MeetingsRepository mMeetingsRepository;

    @NonNull
    private final CurrentIdRepository mCurrentIdRepository;

    @NonNull
    private final MediatorLiveData<List<MainFragmentViewState>> mMutableViewStateLiveData = new MediatorLiveData<>();

    @NonNull
    private final LiveData<List<Meeting>> mMeetingListMutableLivedata;

    @NonNull
    private final MutableLiveData<boolean[]> mSelectedRoomsMutableLivedata = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<LocalDateTime> mSelectedTimeMutableLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(
            @NonNull MeetingsRepository meetingsRepository,
            @NonNull CurrentIdRepository currentIdRepository) {
        mMeetingsRepository = meetingsRepository;
        mCurrentIdRepository = currentIdRepository;

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
    private List<MainFragmentViewState> filterMeetings(
            List<Meeting> meetings,
            LocalDateTime timeFilter,
            boolean[] roomFilter) {
        // todo Nino : RequirableMutableLiveData?
        // todo Nino : sinon on marque rarement une livedata @NonNull, non ?
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
        mSelectedTimeMutableLiveData.setValue(ARBITRARY_DAY.withHour(hourOfDay).withMinute(minute).withSecond(1));
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
