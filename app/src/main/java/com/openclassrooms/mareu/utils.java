package com.openclassrooms.mareu;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public final class utils {

    private static final String REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @NonNull
    public static final LocalDateTime ARBITRARY_DAY = LocalDateTime.of(2021, 6, 14, 8, 50);
    // to use LocalDateTime.now(), one must inject a clock, so tests pass, or in my case, not use a DateTime to store only hours.

    public static LocalDateTime getNextRoundTime() {
        LocalDateTime now = LocalDateTime.now();
        return ARBITRARY_DAY.withHour(now.getHour()).withMinute(now.getMinute() / 15 * 15).plusMinutes(15).withSecond(0);
    }

    @NonNull
    public static String niceTimeFormat(@NonNull LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("kk'h'mm"));
    }

    public static boolean isValidEmail(@NonNull String string) {
        return PATTERN.matcher(string).matches();
    }
}
