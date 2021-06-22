package com.openclassrooms.mareu;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

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

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AddMeetingFragmentViewModelUnitTests {

    private AddMeetingFragmentViewModel viewModel;

    private static final int NEXT_MEETING_ID = 44;

    private static final String PHONE_OWNER_EMAIL = "PHONE_OWNER_EMAIL";
    private static final String SELECT_ROOM = "SELECT_ROOM";
    private static final String STOP_BEFORE_START = "STOP_BEFORE_START";
    private static final String EMAIL_ERROR = "EMAIL_ERROR";
    private static final String DO_NOT_ADD_YOURSELF = "DO_NOT_ADD_YOURSELF";
    private static final String ALREADY_AN_ATTENDEE = "ALREADY_AN_ATTENDEE";
    private static final String TOPIC_ERROR = "TOPIC_ERROR";
    private static final String NO_MEETING_ROOM = "NO_MEETING_ROOM";
    private static final String ROOM_TOO_SMALL = "ROOM_TOO_SMALL";
    private static final String ROOM_NOT_FREE = "ROOM_NOT_FREE";


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MainApplication application;

    @Mock
    MeetingsRepository meetingsRepository;

    @Before
    public void setUp() {
        given(application.getString(R.string.phone_owner_email)).willReturn(PHONE_OWNER_EMAIL);
        given(utils.getNextRoundTime()).willReturn(LocalDateTime.of(2021,6,14,10,12));
        /*given(application.getString(R.string.select_room)).willReturn(SELECT_ROOM);
        given(application.getString(R.string.stop_before_start)).willReturn(STOP_BEFORE_START);
        given(application.getString(R.string.email_error)).willReturn(EMAIL_ERROR);
        given(application.getString(R.string.do_not_add_yourself)).willReturn(DO_NOT_ADD_YOURSELF);
        given(application.getString(R.string.already_an_attendee)).willReturn(ALREADY_AN_ATTENDEE);
        given(application.getString(R.string.topic_error)).willReturn(TOPIC_ERROR);
        given(application.getString(R.string.no_meeting_room)).willReturn(NO_MEETING_ROOM);
        given(application.getString(R.string.room_too_small)).willReturn(ROOM_TOO_SMALL);
        given(application.getString(R.string.room_not_free)).willReturn(ROOM_NOT_FREE);*/

        given(meetingsRepository.getNextMeetingId()).willReturn(NEXT_MEETING_ID);
        given(meetingsRepository.getMeetings()).willReturn(new MutableLiveData<>(TestsMeetingsList.MEETING_LIST));

        viewModel = new AddMeetingFragmentViewModel(application, meetingsRepository);
    }

    @Test
    public void nominalCase() throws InterruptedException {
        // when
        AddMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewState());

        // then
        verify(meetingsRepository).getMeetings();
        verify(meetingsRepository).getNextMeetingId();
        verifyNoMoreInteractions(meetingsRepository);

        assertEquals("44", viewState.getId());
        assertEquals(PHONE_OWNER_EMAIL, viewState.getOwner());
        // todo all other fields to be tested here.
        assertNull(viewState.getParticipantError());
        assertNull(viewState.getRoomError());
        assertNull(viewState.getTimeError());
        assertNull(viewState.getRoomError());
    }

}