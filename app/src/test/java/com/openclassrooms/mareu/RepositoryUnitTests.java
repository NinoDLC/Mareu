package com.openclassrooms.mareu;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class RepositoryUnitTests {



    void initMeetings(MeetingsRepository repo){
        repo.createMeeting(new Meeting(1, "marc@lamzone.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(2021, 6, 9, 8, 30, 0), LocalDateTime.of(2021, 6, 9, 9, 35, 0), MeetingRoom.values()[3]));
        repo.createMeeting(new Meeting(2, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(2021, 6, 9, 16, 15, 0), LocalDateTime.of(2021, 6, 9, 16, 40, 0), MeetingRoom.values()[1]));
        repo.createMeeting(new Meeting(3, "jack@lamzone.org", new HashSet<>(Arrays.asList("hans@buymore.fr", "fred@nerdzherdz.com")), "Project xXx", LocalDateTime.of(2021, 6, 9, 17, 15, 0), LocalDateTime.of(2021, 6, 9, 18, 5, 0), MeetingRoom.values()[6]));
        repo.createMeeting(new Meeting(5, "fred@lamzone.net", new HashSet<>(Arrays.asList("morgan@buymore.com", "fred@buymore.fr")), "Project xXx", LocalDateTime.of(2021, 6, 9, 11, 10, 0), LocalDateTime.of(2021, 6, 9, 11, 10, 0), MeetingRoom.values()[1]));
        repo.createMeeting(new Meeting(7, "jack@buymore.net", new HashSet<>(Arrays.asList("tedy@nerdzherdz.com", "marc@buymore.com")), "Daily meetup", LocalDateTime.of(2021, 6, 9, 9, 40, 0), LocalDateTime.of(2021, 6, 9, 9, 40, 0), MeetingRoom.values()[3]));
        repo.createMeeting(new Meeting(8, "morgan@lamzone.fr", new HashSet<>(Arrays.asList("franck@buymore.net")), "Daily meetup", LocalDateTime.of(2021, 6, 9, 11, 50, 0), LocalDateTime.of(2021, 6, 9, 12, 10, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(9, "fred@nerdzherdz.org", new HashSet<>(Arrays.asList("joe@buymore.fr")), "Agile sprint", LocalDateTime.of(2021, 6, 9, 10, 0, 0), LocalDateTime.of(2021, 6, 9, 11, 25, 0), MeetingRoom.values()[2]));
        repo.createMeeting(new Meeting(10, "morgan@nerdzherdz.com", new HashSet<>(Arrays.asList("fred@nerdzherdz.fr", "chuck@nerdzherdz.fr")), "Global warming", LocalDateTime.of(2021, 6, 9, 10, 10, 0), LocalDateTime.of(2021, 6, 9, 10, 25, 0), MeetingRoom.values()[6]));
        repo.createMeeting(new Meeting(11, "marc@nerdzherdz.org", new HashSet<>(), "Global warming", LocalDateTime.of(2021, 6, 9, 8, 40, 0), LocalDateTime.of(2021, 6, 9, 8, 45, 0), MeetingRoom.values()[7]));
        repo.createMeeting(new Meeting(12, "jack@lamzone.net", new HashSet<>(Arrays.asList("henry@lamzone.com")), "Agile sprint", LocalDateTime.of(2021, 6, 9, 13, 15, 0), LocalDateTime.of(2021, 6, 9, 13, 30, 0), MeetingRoom.values()[2]));
        repo.createMeeting(new Meeting(13, "chuck@lamzone.fr", new HashSet<>(Arrays.asList("joe@lamzone.org")), "Agile sprint", LocalDateTime.of(2021, 6, 9, 13, 25, 0), LocalDateTime.of(2021, 6, 9, 14, 40, 0), MeetingRoom.values()[1]));
        repo.createMeeting(new Meeting(14, "fred@lamzone.com", new HashSet<>(Arrays.asList("marc@buymore.net", "henry@buymore.com")), "Code red", LocalDateTime.of(2021, 6, 9, 8, 50, 0), LocalDateTime.of(2021, 6, 9, 9, 45, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(15, "tedy@lamzone.com", new HashSet<>(), "Code red", LocalDateTime.of(2021, 6, 9, 14, 40, 0), LocalDateTime.of(2021, 6, 9, 15, 25, 0), MeetingRoom.values()[7]));
        repo.createMeeting(new Meeting(16, "claire@nerdzherdz.fr", new HashSet<>(Arrays.asList("jasmine@nerdzherdz.fr")), "Coffee break", LocalDateTime.of(2021, 6, 9, 10, 10, 0), LocalDateTime.of(2021, 6, 9, 10, 45, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(18, "marc@buymore.com", new HashSet<>(Arrays.asList("hans@buymore.org")), "Coffee break", LocalDateTime.of(2021, 6, 9, 17, 45, 0), LocalDateTime.of(2021, 6, 9, 17, 55, 0), MeetingRoom.values()[5]));
        repo.createMeeting(new Meeting(19, "jasmine@buymore.org", new HashSet<>(), "Coffee break", LocalDateTime.of(2021, 6, 9, 17, 50, 0), LocalDateTime.of(2021, 6, 9, 18, 30, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(20, "henry@lamzone.net", new HashSet<>(Arrays.asList("fred@lamzone.net")), "Code red", LocalDateTime.of(2021, 6, 9, 12, 20, 0), LocalDateTime.of(2021, 6, 9, 12, 40, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(22, "joe@nerdzherdz.fr", new HashSet<>(), "Daily meetup", LocalDateTime.of(2021, 6, 9, 16, 5, 0), LocalDateTime.of(2021, 6, 9, 16, 45, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(23, "fred@lamzone.fr", new HashSet<>(), "Daily meetup", LocalDateTime.of(2021, 6, 9, 16, 10, 0), LocalDateTime.of(2021, 6, 9, 17, 15, 0), MeetingRoom.values()[0]));
        repo.createMeeting(new Meeting(24, "marc@lamzone.fr", new HashSet<>(Arrays.asList("hans@nerdzherdz.com", "marc@lamzone.com", "henry@lamzone.com")), "Daily meetup", LocalDateTime.of(2021, 6, 9, 9, 20, 0), LocalDateTime.of(2021, 6, 9, 9, 20, 0), MeetingRoom.values()[5]));
    }


}
