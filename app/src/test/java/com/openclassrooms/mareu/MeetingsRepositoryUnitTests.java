package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MeetingsRepositoryUnitTests {

    private MeetingsRepository repo;

    // for testing LiveData
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        repo = new MeetingsRepository();
    }

    @Test
    public void nonZeroFirstId() {
        //given
        //when
        //then
        assertEquals(1, repo.getNextMeetingId());
    }

    @Test
    public void incrementNextId() {
        //given
        //when
        repo.getNextMeetingId();

        //then
        assertEquals(2, repo.getNextMeetingId());
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
    public void sortedList() throws InterruptedException {
        // given
        Meeting meeting1 = new Meeting(1, "marc@lamzone.fr", new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(2021, 6, 14, 8, 30, 0), LocalDateTime.of(2021, 6, 14, 9, 35, 0), MeetingRoom.values()[3]);
        Meeting meeting2 = new Meeting(2, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(2021, 6, 14, 16, 15, 0), LocalDateTime.of(2021, 6, 14, 16, 40, 0), MeetingRoom.values()[1]);

        // when
        repo.createMeeting(meeting2);
        repo.createMeeting(meeting1); // added after, though occuring before
        List<Meeting> list = LiveDataTestUtils.getOrAwaitValue(repo.getMeetings());

        // then
        assertEquals(meeting1, list.get(0));
        assertEquals(meeting2, list.get(1));
    }

    @Test
    public void returnNullOnNonExistentMeetingId() {
        // given
        Meeting meeting1 = new Meeting(1, "marc@lamzone.fr", new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(2021, 6, 14, 8, 30, 0), LocalDateTime.of(2021, 6, 14, 9, 35, 0), MeetingRoom.values()[3]);
        int nonExistentMeetingId = 44;

        // when
        repo.createMeeting(meeting1);

        // then
        assertNull(repo.getMeetingById(nonExistentMeetingId));
    }

    @Test
    public void meetingInLiveData() throws InterruptedException {
        // given
        Meeting meeting1 = new Meeting(1, "marc@lamzone.fr", new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(2021, 6, 14, 8, 30, 0), LocalDateTime.of(2021, 6, 14, 9, 35, 0), MeetingRoom.values()[3]);

        // when
        repo.createMeeting(meeting1);
        List<Meeting> list = LiveDataTestUtils.getOrAwaitValue(repo.getMeetings());

        // then
        assertEquals(1, list.size());
        assertEquals(meeting1, list.get(0));
    }

    @Test
    public void RemoveMeeting() throws InterruptedException {
        // given
        int meetingId = 2;
        Meeting meeting2 = new Meeting(meetingId, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(2021, 6, 14, 16, 15, 0), LocalDateTime.of(2021, 6, 14, 16, 40, 0), MeetingRoom.values()[1]);
        repo.createMeeting(meeting2);
        List<Meeting> list = LiveDataTestUtils.getOrAwaitValue(repo.getMeetings());

        // when
        repo.removeMeetingById(meetingId);

        // then
        assertNull(repo.getMeetingById(meetingId));
        assertEquals(0, list.size());
    }

    @Test
    public void DoNotRemoveNonExistentMeeting() throws InterruptedException {
        // given
        int meetingId = 2;
        Meeting meeting2 = new Meeting(meetingId, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(2021, 6, 14, 16, 15, 0), LocalDateTime.of(2021, 6, 14, 16, 40, 0), MeetingRoom.values()[1]);
        repo.createMeeting(meeting2);
        List<Meeting> list = LiveDataTestUtils.getOrAwaitValue(repo.getMeetings());

        // when
        repo.removeMeetingById(44);

        // then
        assertEquals(1, list.size());
    }
}
