package com.example.Smart_Attendance_System.Helper;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat sdfUtc = new SimpleDateFormat("HH:mm");

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
}
