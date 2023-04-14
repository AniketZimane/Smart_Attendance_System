package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.AttendanceRepo;
import com.example.Smart_Attendance_System.Dao.StudentRepo;
import com.example.Smart_Attendance_System.Entity.Attendance;
import com.example.Smart_Attendance_System.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student/")
public class UserController {

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    AttendanceRepo attendanceRepo;


//    @GetMapping("/UserLoginPage/")
//    public String dashBoard()
//    {
//        return "UserEntryPage";
//    }

    @GetMapping("/login/")
    public String login()
    {
        return "studentlogin";
    }
    @PostMapping("/validate/")
    public String validationUser(Model model,Long enrollno,String password)
    {
        Student stu = null;
        String originalpassword=null;
        try{
            stu=studentRepo.getReferenceById(enrollno);
            originalpassword=stu.getPassword();

        }
        catch (Exception e){
            System.out.println("Login failed");
            model.addAttribute("msg","No user found");
            return "studentlogin";
        }
        if (password.equals(originalpassword))
        {
            model.addAttribute("stud",stu);
            model.addAttribute("msg","Login successfull");
            System.out.println("Login successfull");
            return "studentdashboard";
        }
        else {
            model.addAttribute("msg","Invalid password");
            return "studentlogin";
        }
    }
    @GetMapping("/idcard/{enrollno}/")
    public String geMyIdCard(Model model,@PathVariable Long enrollno)
    {
        Student stud = studentRepo.getReferenceById(enrollno);
//        System.out.print(stud.toString());
        model.addAttribute("stud", stud);
        return "GenerateId";
    }

    @GetMapping("/attendance/{enrollno}/")
    public String getMyAttendance(Model model,@PathVariable Long enrollno)
    {
        List<Attendance> attendedList =attendanceRepo.findByEnrollno(enrollno);
//        System.out.print(stud.toString());
        model.addAttribute("attendedList", attendedList);
        return "AttendanceTable";
    }
}
