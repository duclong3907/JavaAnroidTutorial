package com.example.appyogademo.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static String timeAgo(LocalDateTime dateTime) {
        Duration difference = Duration.between(dateTime, LocalDateTime.now());

        if (difference.getSeconds() < 60) {
            return difference.getSeconds() + " seconds ago";
        } else if (difference.toMinutes() < 60) {
            return difference.toMinutes() + " minutes ago";
        } else if (difference.toHours() < 24) {
            return difference.toHours() + " hours ago";
        } else {
            return difference.toDays() + " days ago";
        }
    }

    public static String convertDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
            Date date = inputFormat.parse(dateStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertTime(String time) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(time, inputFormatter);
        return localTime.format(outputFormatter);
    }

    public static String dayOfWeek(int day) {
        switch (day) {
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            default:
                return "Unknown";
        }
    }
}