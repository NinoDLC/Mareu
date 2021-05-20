package com.openclassrooms.mareu.repository;

import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.Meeting;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;

public abstract class DummyMeetingGenerator {

    private static final String[] mFirstNames = {"Chuck", "Hans", "Franck", "Marc", "Tedy", "Joe", "Jack", "Fred", "Henry", "Jasmine", "Claire", "Morgan"};

    private static final String[] mCompanies = {"LamZone", "Pepsi", "NerdzHerdz", "Buy More"};

    private static final String[] mDomains = {"fr", "com", "org", "net"};

    private static final String[] mSubjects = {"coffee break", "code red", "daily meetup", "agile sprint", "project X"};

    private static final Random mRand = new Random();

    private static final LocalDateTime mRoundedNow = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0);

    private static int i = 0;

    private static int getNextId() {
    /* can't use DependencyInjection.getMeetingsRepository().getNextMeetingId()
        as I am creating the fucking repo : it does not exist yet => nullPointerException
     */
        i++;
        return i;
    }

    protected static Meeting generateMeeting() {
        LocalDateTime start = generateStart();
        return (new Meeting(
                getNextId(),
                generateEmail(),
                generateParticipants(),
                mSubjects[mRand.nextInt(mSubjects.length)],
                start,
                generateStop(start),
                mRand.nextInt(10) + 1)
        );
    }

    private static String generateEmail() {
        String name = mFirstNames[mRand.nextInt(mFirstNames.length)];
        String company = mCompanies[mRand.nextInt(mCompanies.length)];
        String domain = mDomains[mRand.nextInt(mDomains.length)];
        return name + "@" + company + "." + domain;
    }

    private static HashSet<String> generateParticipants() {
        int participantsNumber = mRand.nextInt(4);
        HashSet<String> participants = new HashSet<>();
        while (participants.size() < participantsNumber) {
            participants.add(generateEmail());
        }
        return participants;
    }

    private static LocalDateTime generateStart() {
        /*
        LocalDateTime localDateTime = new LocalDateTime();
        localDateTime.adjustInto(mRoundedNow);
         */
        return mRoundedNow.plusHours(mRand.nextInt(10)).withMinute(mRand.nextInt(12) * 5);
    }

    private static LocalDateTime generateStop(LocalDateTime start) {
        return start.plusMinutes(mRand.nextInt(18) * 5);
    }

}
