package com.openclassrooms.mareu.model;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import com.openclassrooms.mareu.R;

public enum MeetingRoom {

    ROOM_0("Braque-de-Weimar", "1st floor, 1st door on the left", 3, R.color.dog_1),
    ROOM_1("Doberman", "1st floor, 2nd door on the left", 4, R.color.dog_2),
    ROOM_2("Dogue-Allemand", "1st floor, 1st door on the right", 5, R.color.dog_3),
    ROOM_3("Caniche", "1st floor, 2nd door on the right", 2, R.color.dog_4),
    ROOM_4("Jack-Russel", "2nd floor, 1st door on the left", 4, R.color.dog_5),
    ROOM_5("Fox-Terrier", "2nd floor, 2nd door on the left", 5, R.color.dog_6),
    ROOM_6("Labrador", "2nd floor, 1st door on the right", 2, R.color.dog_7),
    ROOM_7("Basset", "2rd floor, 2nd door on the right", 4, R.color.dog_8),
    ROOM_8("Cocker", "3rd floor, 1st door on the left", 2, R.color.dog_9),
    ROOM_9("Epagnol", "3rd floor, 2nd door on the left", 6, R.color.dog_10);

    @NonNull
    private final String mName;

    @NonNull
    private final String mLocation;

    private final int mCapacity;

    @ColorRes
    private final int mTextColor;

    MeetingRoom(@NonNull String name, @NonNull String location, int capacity, @ColorRes int textColor) {
        this.mName = name;
        this.mLocation = location;
        this.mCapacity = capacity;
        this.mTextColor = textColor;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getLocation() {
        return mLocation;
    }

    public int getCapacity() {
        return mCapacity;
    }

    @ColorRes
    public int getTextColor() {
        return mTextColor;
    }
}
