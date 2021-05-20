package com.openclassrooms.mareu.repository;

import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public abstract class DummyMeetingGenerator {

    private static int mId = 0;

    private static final String[] mFirstNames = {"Chuck", "Hans", "Franck", "Marc", "Tedy", "Joe", "Jack", "Fred", "Henry", "Jasmine", "Claire", "Morgan"};

    private static final String[] mCompany = {"LamZone", "Pepsi", "NerdzHerdz", "Buy More"};

    private static Random mRand = new Random();

    public static Meeting generateMeeting(){
        mId++;
        return(new Meeting(mId, generateEmail(),
    }

    private static String getAFirstName(){
        mRand.nextInt(mFirstNames.length);
    }



    private static Email generateEmail(){

    }

    private static HashSet<Email> generateParticipants(){

    }



}
