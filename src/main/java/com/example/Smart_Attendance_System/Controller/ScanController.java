package com.example.Smart_Attendance_System.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ScanController {
    @GetMapping("/entry/")
    public String entryFill()
    {

        return "lectureEntry";
    }
    @PostMapping("/mark/")
    public String markAtt()
    {
        return "ScanQr";
    }
}
