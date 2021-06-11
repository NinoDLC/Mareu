package com.openclassrooms.mareu;

import androidx.annotation.NonNull;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Pattern;

public final class utils {

    private static final String REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private static final LocalDateTime now = LocalDateTime.now();

    @NonNull
    public static String niceTimeFormat(@NonNull LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("kk'h'mm"));
    }

    public static boolean isValidEmail(@NonNull String string) {
        return PATTERN.matcher(string).matches();
    }

    public static void initMeetings(@NonNull MeetingsRepository repo) {
        repo.createMeeting(new Meeting(1, "marc@lamzone.fr", new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 30, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 35, 0), MeetingRoom.values()[3]));
        repo.createMeeting(new Meeting(2, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 15, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 40, 0), MeetingRoom.values()[1]));
        repo.createMeeting(new Meeting(3, "jack@lamzone.org", new HashSet<>(Arrays.asList("hans@buymore.fr", "fred@nerdzherdz.com")), "Project xXx", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 15, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 18, 5, 0), MeetingRoom.values()[6]));
        repo.createMeeting(new Meeting(4, "fred@lamzone.net", new HashSet<>(Arrays.asList("morgan@buymore.com", "fred@buymore.fr")), "Project xXx", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 11, 10, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 11, 10, 0), MeetingRoom.values()[1]));
        repo.createMeeting(new Meeting(5, "jack@buymore.net", new HashSet<>(Arrays.asList("tedy@nerdzherdz.com", "marc@buymore.com")), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 40, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 40, 0), MeetingRoom.values()[3]));
        repo.createMeeting(new Meeting(6, "morgan@lamzone.fr", new HashSet<>(Collections.singletonList("franck@buymore.net")), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 11, 50, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 10, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(7, "fred@nerdzherdz.org", new HashSet<>(Collections.singletonList("joe@buymore.fr")), "Agile sprint", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 0, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 11, 25, 0), MeetingRoom.values()[2]));
        repo.createMeeting(new Meeting(8, "morgan@nerdzherdz.com", new HashSet<>(Arrays.asList("fred@nerdzherdz.fr", "chuck@nerdzherdz.fr")), "Global warming", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 10, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 25, 0), MeetingRoom.values()[6]));
        repo.createMeeting(new Meeting(9, "marc@nerdzherdz.org", new HashSet<>(), "Global warming", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 40, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 45, 0), MeetingRoom.values()[7]));
        repo.createMeeting(new Meeting(10, "jack@lamzone.net", new HashSet<>(Collections.singletonList("henry@lamzone.com")), "Agile sprint", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 13, 15, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 13, 30, 0), MeetingRoom.values()[2]));
        repo.createMeeting(new Meeting(11, "chuck@lamzone.fr", new HashSet<>(Collections.singletonList("joe@lamzone.org")), "Agile sprint", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 13, 25, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 14, 40, 0), MeetingRoom.values()[1]));
        repo.createMeeting(new Meeting(12, "fred@lamzone.com", new HashSet<>(Arrays.asList("marc@buymore.net", "henry@buymore.com")), "Code red", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 50, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 45, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(13, "tedy@lamzone.com", new HashSet<>(), "Code red", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 14, 40, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 15, 25, 0), MeetingRoom.values()[7]));
        repo.createMeeting(new Meeting(14, "claire@nerdzherdz.fr", new HashSet<>(Collections.singletonList("jasmine@nerdzherdz.fr")), "Coffee break", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 10, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 45, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(15, "marc@buymore.com", new HashSet<>(Collections.singletonList("hans@buymore.org")), "Coffee break", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 45, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 55, 0), MeetingRoom.values()[5]));
        repo.createMeeting(new Meeting(16, "jasmine@buymore.org", new HashSet<>(), "Coffee break", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 50, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 18, 30, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(17, "henry@lamzone.net", new HashSet<>(Collections.singletonList("fred@lamzone.net")), "Code red", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 20, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 40, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(18, "joe@nerdzherdz.fr", new HashSet<>(), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 5, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 45, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(19, "fred@lamzone.fr", new HashSet<>(), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 10, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 15, 0), MeetingRoom.values()[0]));
        repo.createMeeting(new Meeting(20, "marc@lamzone.fr", new HashSet<>(Arrays.asList("hans@nerdzherdz.com", "marc@lamzone.com", "henry@lamzone.com")), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 20, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 20, 0), MeetingRoom.values()[5]));
    }
}
