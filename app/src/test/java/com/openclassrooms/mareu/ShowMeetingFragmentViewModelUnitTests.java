package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragmentViewModel;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragmentViewState;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ShowMeetingFragmentViewModelUnitTests {

    private static final int REQUESTED_MEETING_ID = 44;
    private ShowMeetingFragmentViewModel viewModel;

    @Mock
    private CurrentIdRepository currentIdRepository;

    @Mock
    private MeetingsRepository meetingsRepository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        given(currentIdRepository.getCurrentIdLiveData()).willReturn(new MutableLiveData<>(REQUESTED_MEETING_ID));
        // todo seems given() calls can happen after meetingsRepository is passed to VM?
        viewModel = new ShowMeetingFragmentViewModel(meetingsRepository, currentIdRepository);
    }

    @Test
    public void initialMeetingFound() throws InterruptedException {
        // given
        String owner = "marc@lamzone.fr";
        HashSet<String> participants = new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net"));
        String topic = "Daily meetup";
        LocalDateTime start = LocalDateTime.of(2021, 6, 14, 8, 30, 0);
        LocalDateTime end = LocalDateTime.of(2021, 6, 14, 9, 35, 0);
        MeetingRoom room = MeetingRoom.ROOM_2;

        String[] viewStateParticipants = {"claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net"};

        Meeting meeting1 = new Meeting(REQUESTED_MEETING_ID, owner, participants, topic, start, end, room);
        given(meetingsRepository.getMeetings()).willReturn(new MutableLiveData<>(new ArrayList<>(Collections.singleton(meeting1))));

        // when
        ShowMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getShowMeetingFragmentItem());

        // then
        assertEquals("44", viewState.getId());
        assertEquals(owner, viewState.getOwner());
        assertArrayEquals(viewStateParticipants, viewState.getParticipants());
        assertEquals(topic, viewState.getTopic());
        assertEquals("08h30", viewState.getStartText());
        assertEquals("09h35", viewState.getEndText());
        assertEquals(MeetingRoom.ROOM_2.getName(), viewState.getRoomName());
    }

    @Test
    public void initialMeetingNotFound() {
        // given
        String dumbString = "xXx";
        HashSet<String> participants = new HashSet<>();
        LocalDateTime start = LocalDateTime.of(2021, 6, 14, 8, 30, 0);
        MeetingRoom room = MeetingRoom.ROOM_2;
        int nonExistentMeetingId = 55;

        Meeting meeting1 = new Meeting(nonExistentMeetingId, dumbString, participants, dumbString, start, start, room);
        given(meetingsRepository.getMeetings()).willReturn(new MutableLiveData<>(new ArrayList<>(Collections.singleton(meeting1))));

        // then
        // todo: super weird to use a lambda
        assertThrows("no such meeting", IllegalStateException.class, () -> LiveDataTestUtils.getOrAwaitValue(viewModel.getShowMeetingFragmentItem()));
    }

    @Test
    public void nullLiveData() {
        // given
        given(meetingsRepository.getMeetings()).willReturn(new MutableLiveData<>());

        // then
        // todo: super weird to use a lambda
        assertThrows("null livedata", IllegalStateException.class, () -> LiveDataTestUtils.getOrAwaitValue(viewModel.getShowMeetingFragmentItem()));
    }
}
