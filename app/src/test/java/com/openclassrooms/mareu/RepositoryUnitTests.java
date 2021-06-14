package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class RepositoryUnitTests {

    private MeetingsRepository repo;

    // for testing LiveData
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        repo = new MeetingsRepository();
    }

    @Test
    public void createAMeeting() {
        // given
        Meeting meeting1 = new Meeting(1, "marc@lamzone.fr", new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(2021, 6, 14, 8, 30, 0), LocalDateTime.of(2021, 6, 14, 9, 35, 0), MeetingRoom.values()[3]);

        // when
        repo.createMeeting(meeting1);

        // then
        assertEquals(meeting1, repo.getMeetingById(1));
    }


    @Test
    public void createTwoMeetings() {
        // given
        Meeting meeting1 = new Meeting(1, "marc@lamzone.fr", new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(2021, 6, 14, 8, 30, 0), LocalDateTime.of(2021, 6, 14, 9, 35, 0), MeetingRoom.values()[3]);
        Meeting meeting2 = new Meeting(2, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(2021, 6, 14, 16, 15, 0), LocalDateTime.of(2021, 6, 14, 16, 40, 0), MeetingRoom.values()[1]);

        // when
        repo.createMeeting(meeting1);
        repo.createMeeting(meeting2);

        // then
        repo.getMeetings();
    }


}
