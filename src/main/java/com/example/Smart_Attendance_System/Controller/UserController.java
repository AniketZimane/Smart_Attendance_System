package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.StudentRepo;
import com.example.Smart_Attendance_System.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    StudentRepo studentRepo;

    @GetMapping("/UserDashBoard/")
    public String userdashboard()
    {
        return "index";
    }

//    @GetMapping("/UserLoginPage/")
//    public String dashBoard()
//    {
//        return "UserEntryPage";
//    }

    @GetMapping("/Userlogin")
    public String login()
    {

        return "UserLogin";
    }
}
