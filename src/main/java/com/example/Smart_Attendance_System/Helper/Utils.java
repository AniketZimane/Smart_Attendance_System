package com.example.Smart_Attendance_System.Helper;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
    public static SimpleDateFormat sdfUtc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");
}
