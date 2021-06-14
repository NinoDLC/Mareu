package com.openclassrooms.mareu;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MasterDetailRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragmentViewModel;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragmentViewState;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShowMeetingFragmentViewModelUnitTests {

    private MeetingsRepository meetingsRepository;
    private MasterDetailRepository masterDetailRepository;
    private ShowMeetingFragmentViewModel viewModel;

    @Before
    public void setUp() {
        meetingsRepository = new MeetingsRepository();
        masterDetailRepository = new MasterDetailRepository();
        viewModel = new ShowMeetingFragmentViewModel(meetingsRepository, masterDetailRepository);
    }

    @Test
    public void initialMeetingFound() throws InterruptedException {
        // given
        int id = 1;
        String owner = "marc@lamzone.fr";
        HashSet<String> participants = new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net"));
        String topic = "Daily meetup";
        LocalDateTime start = LocalDateTime.of(2021, 6, 14, 8, 30, 0);
        LocalDateTime end =  LocalDateTime.of(2021, 6, 14, 9, 35, 0);
        MeetingRoom room = MeetingRoom.ROOM_2;

        String[] viewStateParticipants = {"claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net"};

        Meeting meeting1 = new Meeting(id, owner, participants, topic, start ,end, room);
        meetingsRepository.createMeeting(meeting1);

        // when
        masterDetailRepository.setCurrentId(1);
        ShowMeetingFragmentViewState viewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getShowMeetingFragmentItem());

        // then
        assertEquals("1", viewState.getId());
        assertEquals(owner, viewState.getOwner());
        assertTrue(Arrays.deepEquals(viewStateParticipants, viewState.getParticipants()));
        assertEquals(topic, viewState.getTopic());
        assertEquals("08h30", viewState.getStartText());
        assertEquals("09h35", viewState.getEndText());
    }


    Meeting meeting2 = new Meeting(2, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(2021, 6, 14, 16, 15, 0), LocalDateTime.of(2021, 6, 14, 16, 40, 0), MeetingRoom.values()[1]);


}
