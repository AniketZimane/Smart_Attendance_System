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
    @GetMapping("/UserLoginPage/")
    public String dashBoard()
    {
        return "UserEntryPage";
    }


//    @GetMapping("/getMyid")
//    public String findByEnrollno(Model model, @RequestParam("enrollno") Long enrollno) {
//        Student stud = studentRepo.getReferenceById(enrollno);
//    //        System.out.print(stud.toString());
//        model.addAttribute("stud", stud);
//
//        return "GenerateId";
//    }
            @GetMapping("/edit/{id}/")
            public String editObject(@PathVariable("id") Long id, Model model) {
                Student stud = studentRepo.getReferenceById(id);
                if(stud==null)
                {
                    model.addAttribute("msg", "Incorrect Username");

                }
                else{
                    model.addAttribute("stud", stud);
                }
                return "GenerateId";
            }
}
