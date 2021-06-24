package com.openclassrooms.mareu.testUtils;

import androidx.annotation.NonNull;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.main.MainFragmentViewState;
import com.openclassrooms.mareu.utils;

import org.junit.Test;

import java.text.MessageFormat;

public class MainFragmentViewStateOrderedList {

    public static final MainFragmentViewState[] VIEW_STATE_LIST = {
            new MainFragmentViewState(1, "08h30 - Daily meetup", "marc@lamzone.fr", "+1", "Caniche", 2131034208),
            new MainFragmentViewState(9, "08h40 - Global warming", "marc@nerdzherdz.org", "+0", "Basset", 2131034212),
            new MainFragmentViewState(12, "08h50 - Code red", "fred@lamzone.com", "+2", "Cocker", 2131034213),
            new MainFragmentViewState(20, "09h20 - Daily meetup", "marc@lamzone.fr", "+3", "Fox-Terrier", 2131034210),
            new MainFragmentViewState(5, "09h40 - Daily meetup", "jack@buymore.net", "+2", "Caniche", 2131034208),
            new MainFragmentViewState(7, "10h00 - Agile sprint", "fred@nerdzherdz.org", "+1", "Dogue-Allemand", 2131034207),
            new MainFragmentViewState(8, "10h10 - Global warming", "morgan@nerdzherdz.com", "+2", "Labrador", 2131034211),
            new MainFragmentViewState(14, "10h10 - Coffee break", "claire@nerdzherdz.fr", "+1", "Epagnol", 2131034205),
            new MainFragmentViewState(4, "11h10 - Project xXx", "fred@lamzone.net", "+2", "Doberman", 2131034206),
            new MainFragmentViewState(6, "11h50 - Daily meetup", "morgan@lamzone.fr", "+1", "Cocker", 2131034213),
            new MainFragmentViewState(17, "12h20 - Code red", "henry@lamzone.net", "+1", "Epagnol", 2131034205),
            new MainFragmentViewState(10, "13h15 - Agile sprint", "jack@lamzone.net", "+1", "Dogue-Allemand", 2131034207),
            new MainFragmentViewState(11, "13h25 - Agile sprint", "chuck@lamzone.fr", "+1", "Doberman", 2131034206),
            new MainFragmentViewState(13, "14h40 - Code red", "tedy@lamzone.com", "+0", "Basset", 2131034212),
            new MainFragmentViewState(18, "16h05 - Daily meetup", "joe@nerdzherdz.fr", "+0", "Cocker", 2131034213),
            new MainFragmentViewState(19, "16h10 - Daily meetup", "fred@lamzone.fr", "+0", "Braque-de-Weimar", 2131034204),
            new MainFragmentViewState(2, "16h15 - Project xXx", "tedy@buymore.fr", "+3", "Doberman", 2131034206),
            new MainFragmentViewState(3, "17h15 - Project xXx", "jack@lamzone.org", "+2", "Labrador", 2131034211),
            new MainFragmentViewState(15, "17h45 - Coffee break", "marc@buymore.com", "+1", "Fox-Terrier", 2131034210),
            new MainFragmentViewState(16, "17h50 - Coffee break", "jasmine@buymore.org", "+0", "Epagnol", 2131034205),
    };

    public String toString(MainFragmentViewState viewState) {
        return "new MainFragmentViewState(" + viewState.getId() +
                ", \"" + viewState.getUpLine() + '"' +
                ", \"" + viewState.getOwner() + '"' +
                ", \"" + viewState.getParticipantsNumber() + '"' +
                ", \"" + viewState.getMeetingRoomName() + '"' +
                ", " + viewState.getMeetingRoomColor() +
                "),";
    }

//    public String fromLocaldateTime(LocalDateTime ldt){
//        return "LocalDateTime.of(" +
//                ldt.getYear() +
//                ", " + ldt.getMonthValue() +
//                ", " + ldt.getDayOfMonth() +
//                ", " + ldt.getHour() +
//                ", " + ldt.getMinute() +
//                ", " + ldt.getSecond() +
//                ')';
//    }
//
//    public String fromHashSet(Set<String> set){
//        if (set.isEmpty()) return "new HashSet<>()";
//        String string = "new HashSet<>(Arrays.asList(";
//        for (String sub : set)
//            string = string.concat('"' + sub + "\", ");
//        return string.substring(0, string.length() - 2).concat("))");
//    }

    @Test
    public void MeetingsRepository() {
        int[] array = {1, 9, 12, 20, 5, 7, 8, 14, 4, 6, 17, 10, 11, 13, 18, 19, 2, 3, 15, 16};
        for (int i : array) {
            Meeting meeting = TestsMeetingsList.MEETING_LIST.get(i - 1);
            System.out.println(toString(toViewState(meeting)));
        }
    }

    private MainFragmentViewState toViewState(@NonNull Meeting meeting) {
        return new MainFragmentViewState(
                meeting.getId(),
                MessageFormat.format("{0} - {1}",
                        utils.niceTimeFormat(meeting.getStart()),
                        meeting.getTopic()
                ),
                meeting.getOwner(),
                MessageFormat.format("+{0}", meeting.getParticipants().size()),
                meeting.getRoom().getName(),
                meeting.getRoom().getTextColor()
        );
    }


}
