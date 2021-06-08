package com.openclassrooms.mareu.repository;

import com.openclassrooms.mareu.model.Meeting;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;

public abstract class DummyMeetingGenerator {

    private static final String[] FIRST_NAMES = {"Chuck", "Hans", "Franck", "Marc", "Tedy", "Joe", "Jack", "Fred", "Henry", "Jasmine", "Claire", "Morgan"};

    private static final String[] COMPANIES = {"LamZone", "NerdzHerdz", "Buy More"};

    private static final String[] DOMAINS = {"fr", "com", "org", "net"};

    private static final String[] SUBJECTS = {"Coffee break", "Code red", "Daily meetup", "Agile sprint", "Project xXx", "Global warming"};

    private static final Random RANDOM = new Random();

    private static int i = 1;

    private static int getNextId() {
    /* can't use DependencyInjection.getMeetingsRepository().getNextMeetingId()
        as I am creating the fucking repo : it does not exist yet => nullPointerException
     */
        return i++;
    }

    protected static Meeting generateMeeting() {
        LocalDateTime start = generateStart();
        return (new Meeting(
                getNextId(),
                generateEmail(),
                generateParticipants(),
                SUBJECTS[RANDOM.nextInt(SUBJECTS.length)],
                start,
                generateStop(start),
                RANDOM.nextInt(10) + 1)
        );
    }

    private static String generateEmail() {
        String name = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
        String company = COMPANIES[RANDOM.nextInt(COMPANIES.length)];
        String domain = DOMAINS[RANDOM.nextInt(DOMAINS.length)];
        String email = name + "@" + company + "." + domain;
        return email.replaceAll("\\s+", "").toLowerCase();
    }

    private static HashSet<String> generateParticipants() {
        int participantsNumber = RANDOM.nextInt(4);
        HashSet<String> participants = new HashSet<>();
        while (participants.size() < participantsNumber) {
            participants.add(generateEmail());
        }
        return participants;
    }

    private static LocalDateTime generateStart() {
        LocalDateTime roundedNow = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0);
        return roundedNow.plusHours(RANDOM.nextInt(10)).withMinute(RANDOM.nextInt(12) * 5);
    }

    private static LocalDateTime generateStop(LocalDateTime start) {
        return start.plusMinutes(RANDOM.nextInt(18) * 5);
    }

}
