package com.openclassrooms.mareu.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class utils {

    public static String niceTimeFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("kk'h'mm"));
    }

}
