package com.openclassrooms.mareu.testUtils;

import android.util.Log;

import com.openclassrooms.mareu.model.Meeting;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class MainFragmentViewStateOrderedList {

    public String tolog(Meeting meeting) {
        return "new Meeting(" + meeting.getId() +
                ", \"" + meeting.getOwner() + '"' +
                ", " + fromHashSet(meeting.getParticipants()) +// todo replace this with print hashset
                ", \"" + meeting.getSubject() + '"' +
                ", " + fromLocaldateTime(meeting.getStart()) +
                ", " + fromLocaldateTime(meeting.getStop()) +
                ", MeetingRoom.values()[" + meeting.getRoom().ordinal() + "]" +
                ");";
    }

    public String fromLocaldateTime(LocalDateTime ldt){
        return "LocalDateTime.of(" +
                ldt.getYear() +
                ", " + ldt.getMonthValue() +
                ", " + ldt.getDayOfMonth() +
                ", " + ldt.getHour() +
                ", " + ldt.getMinute() +
                ", " + ldt.getSecond() +
                ')';
    }

    public String fromHashSet(Set<String> set){
        if (set.isEmpty()) return "new HashSet<>()";
        String string = "new HashSet<>(Arrays.asList(";
        for (String sub : set)
            string = string.concat('"' + sub + "\", ");
        return string.substring(0, string.length() - 2).concat("))");
    }


    public MeetingsRepository() {
        while (mMeetings.size() < 20) {
            // createMeeting() runs isValidMeeting(), returns boolean
            createMeeting(DummyMeetingGenerator.generateMeeting());
            // todo: this loop updates the livedata 20+ times at launch
        }
        Collections.sort(mMeetings, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));

        for (Meeting meeting : mMeetings)
            Log.e("Arnaud", tolog(meeting));

        mNextMeetingId = mMeetings.get(mMeetings.size() - 1).getId();
        sortMeetings();
    }


}
