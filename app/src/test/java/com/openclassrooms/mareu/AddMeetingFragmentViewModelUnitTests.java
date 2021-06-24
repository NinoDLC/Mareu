package com.openclassrooms.mareu;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;
import com.openclassrooms.mareu.testUtils.TestsMeetingsList;
import com.openclassrooms.mareu.ui.add.AddMeetingFragmentViewModel;
import com.openclassrooms.mareu.ui.add.AddMeetingFragmentViewState;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AddMeetingFragmentViewModelUnitTests {

    private AddMeetingFragmentViewModel viewModel;

    private static final int NEXT_MEETING_ID = 44;
    private static final String ADD_EMAIL = "foo@bar.baz";
    private static final String TOPIC = "TOPIC";
    private static final Clock CLOCK = Clock.fixed(utils.ARBITRARY_DAY.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

    private static final String PHONE_OWNER_EMAIL = "chuck@buymore.com";
    private static final String SELECT_ROOM = "SELECT_ROOM";
    private static final String STOP_BEFORE_START = "STOP_BEFORE_START";
    private static final String EMAIL_ERROR = "EMAIL_ERROR";
    private static final String DO_NOT_ADD_YOURSELF = "DO_NOT_ADD_YOURSELF";
    private static final String ALREADY_AN_ATTENDEE = "ALREADY_AN_ATTENDEE";
    private static final String TOPIC_ERROR = "TOPIC_ERROR";
    private static final String NO_MEETING_ROOM = "NO_MEETING_ROOM";
    private static final String ROOM_TOO_SMALL = "ROOM_TOO_SMALL";
    private static final String ROOM_NOT_FREE = "ROOM_NOT_FREE";

    private static final AddMeetingFragmentViewState DEFAULT_VIEWSTATE = new AddMeetingFragmentViewState(
            "44",
            PHONE_OWNER_EMAIL,
            "09h00",
            "09h30",
            "Labrador",
            new String[]{},
            9,
            0,
            9,
            30,
            new CharSequence[]{"Labrador", "Braque-de-Weimar", "Doberman", "Jack-Russel", "Basset", "Dogue-Allemand", "Epagnol"},
            null,
            null,
            null,
            null
    );

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MainApplication application;

    @Mock
    MeetingsRepository meetingsRepository;

    @Before
    public void setUp() {
        given(application.getString(R.string.phone_owner_email)).willReturn(PHONE_OWNER_EMAIL);
        given(application.getString(R.string.topic_error)).willReturn(TOPIC_ERROR);
        given(application.getString(R.string.stop_before_start)).willReturn(STOP_BEFORE_START);
        given(application.getString(R.string.email_error)).willReturn(EMAIL_ERROR);
        given(application.getString(R.string.do_not_add_yourself)).willReturn(DO_NOT_ADD_YOURSELF);
        given(application.getString(R.string.already_an_attendee)).willReturn(ALREADY_AN_ATTENDEE);
        given(application.getString(R.string.room_too_small)).willReturn(ROOM_TOO_SMALL);
        given(application.getString(R.string.no_meeting_room)).willReturn(NO_MEETING_ROOM);
        given(application.getString(R.string.select_room)).willReturn(SELECT_ROOM);
        given(application.getString(R.string.room_not_free)).willReturn(ROOM_NOT_FREE);
        given(meetingsRepository.getNextMeetingId()).willReturn(NEXT_MEETING_ID);
        given(meetingsRepository.getMeetings()).willReturn(new MutableLiveData<>(TestsMeetingsList.MEETING_LIST));

        viewModel = new AddMeetingFragmentViewModel(application, meetingsRepository, CLOCK);
    }

    @Test
    public void nominalCase() throws InterruptedException {
        // when
        viewModel.setTopic(TOPIC);
        viewModel.validate();
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // then
        verify(meetingsRepository).createMeeting(
                new Meeting(
                        NEXT_MEETING_ID,
                        PHONE_OWNER_EMAIL,
                        new HashSet<>(),
                        TOPIC,
                        utils.ARBITRARY_DAY.withHour(9).withMinute(0),
                        utils.ARBITRARY_DAY.withHour(9).withMinute(30),
                        MeetingRoom.ROOM_6
                        ));
        verify(meetingsRepository).getMeetings();
        verify(meetingsRepository).getNextMeetingId();
        verifyNoMoreInteractions(meetingsRepository);

        assertEquals(DEFAULT_VIEWSTATE, viewState);
    }

    @Test
    public void errorOnEmptyTopic() throws InterruptedException {
        // when
        viewModel.setTopic("");
        viewModel.validate();

        //then
        assertEquals(TOPIC_ERROR, LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState()).getTopicError());
    }

    @Test
    public void newTopicRemovesError() throws InterruptedException {
        // when
        viewModel.setTopic("");
        viewModel.validate();
        viewModel.setTopic(TOPIC);

        //then
        assertNull(LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState()).getTopicError());
    }

    @Test
    public void setRoom() throws InterruptedException {
        // when
        viewModel.setRoom(2);

        // then
        assertEquals("Doberman", LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState()).getRoomName());
    }

    @Test
    public void setStartTimeEarlier() throws InterruptedException {
        // when
        viewModel.setTime(true, 8, 30);
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // then
        assertNull(viewState.getRoomError());
        assertNull(viewState.getTimeError());
    }

    @Test
    public void setEndTimeEarlier() throws InterruptedException {
        // when
        viewModel.setTime(false, 8, 30);
        viewModel.validate();
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // then
        assertEquals(ROOM_NOT_FREE, viewState.getRoomError());
        assertEquals(STOP_BEFORE_START, viewState.getTimeError());
    }

    @Test
    public void addParticipant() throws InterruptedException {
        // given
        String[] expectedParticipants = {ADD_EMAIL};

        // when
        viewModel.addParticipant(ADD_EMAIL);
        viewModel.validate();
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());


        // Then
        assertArrayEquals(expectedParticipants, viewState.getParticipants());
    }

    @Test
    public void addParticipantTwice() throws InterruptedException {
        // given
        String[] expectedParticipants = {ADD_EMAIL};

        // when
        viewModel.addParticipant(ADD_EMAIL);
        viewModel.addParticipant(ADD_EMAIL);
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // Then
        assertArrayEquals(expectedParticipants, viewState.getParticipants());
        assertEquals(ALREADY_AN_ATTENDEE, viewState.getParticipantError());
    }

    @Test
    public void addEmptyParticipant() throws InterruptedException {
        // when
        viewModel.addParticipant("");
        viewModel.validate();
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // Then
        assertEquals(0, viewState.getParticipants().length);
        assertNull(viewState.getParticipantError());
    }

    @Test
    public void addInvalidParticipant() throws InterruptedException {
        // when
        viewModel.addParticipant("foo");
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // Then
        assertEquals(0, viewState.getParticipants().length);
        assertEquals(EMAIL_ERROR, viewState.getParticipantError());
    }

    @Test
    public void addSelfAsParticipant() throws InterruptedException {
        // when
        viewModel.addParticipant(PHONE_OWNER_EMAIL);
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // Then
        assertEquals(0, viewState.getParticipants().length);
        assertEquals(DO_NOT_ADD_YOURSELF, viewState.getParticipantError());
    }

    @Test
    public void removeParticipant() throws InterruptedException {
        // when
        viewModel.addParticipant(ADD_EMAIL);
        viewModel.removeParticipant(ADD_EMAIL);
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // Then
        assertEquals(0, viewState.getParticipants().length);
    }

    @Test
    public void tooManyParticipants() throws InterruptedException {
        // when
        viewModel.setTopic(TOPIC);
        viewModel.addParticipant(ADD_EMAIL);
        viewModel.addParticipant("foobar@bar.baz");
        viewModel.addParticipant("foobaz@bar.baz");
        viewModel.addParticipant("barfoo@bar.baz");
        viewModel.addParticipant("bazfoo@bar.baz");
        viewModel.addParticipant("barbaz@bar.baz");
        viewModel.addParticipant("bazbar@bar.baz");
        viewModel.validate();
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        //then
        assertEquals("Labrador", viewState.getRoomName());
        assertEquals(7, viewState.getParticipants().length);
        assertEquals(ROOM_TOO_SMALL, viewState.getRoomError());
    }

    @Test
    public void setValidRoom() throws InterruptedException {
        // when
        viewModel.setRoom(1);

        // Then
        assertEquals("Braque-de-Weimar", LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState()).getRoomName());
    }

    @Test
    public void allRoomsTakenOnTimeSpan() throws InterruptedException {
        // when
        LocalDateTime start = utils.ARBITRARY_DAY.withHour(2);
        LocalDateTime end = utils.ARBITRARY_DAY.withHour(10);
        given(meetingsRepository.getMeetings()).willReturn(new MutableLiveData<>(new ArrayList<>(Arrays.asList(
                new Meeting(1, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_0),
                new Meeting(2, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_1),
                new Meeting(3, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_2),
                new Meeting(4, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_3),
                new Meeting(5, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_4),
                new Meeting(6, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_5),
                new Meeting(7, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_6),
                new Meeting(8, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_7),
                new Meeting(9, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_8),
                new Meeting(10, PHONE_OWNER_EMAIL, new HashSet<>(), TOPIC, start, end, MeetingRoom.ROOM_9)
        ))));
        viewModel = new AddMeetingFragmentViewModel(application, meetingsRepository, CLOCK);
        viewModel.validate();
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // then
        assertEquals(SELECT_ROOM, viewState.getRoomName());
        assertEquals(NO_MEETING_ROOM, viewState.getRoomError());
    }

    @Test
    public void setRoomButThenChangeToIncompatibleTimeSpan() throws InterruptedException {
        // when
        viewModel.setTime(true, 17,20);
        viewModel.setTime(false, 18,0);
        viewModel.validate();
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // then
        assertEquals(ROOM_NOT_FREE, viewState.getRoomError());
    }
}
