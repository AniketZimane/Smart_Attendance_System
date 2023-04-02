package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.CourseRepo;
import com.example.Smart_Attendance_System.Dao.DeparmentRepo;
import com.example.Smart_Attendance_System.Dao.SubjectRepo;
import com.example.Smart_Attendance_System.Dao.TeacherRepo;
import com.example.Smart_Attendance_System.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    DeparmentRepo deparmentRepo;

    @PostMapping("/departmentdata/")
    public String deptreg(Model model, Department department)
    {
        deparmentRepo.save(department);
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("departmentList",departmentList);
        model.addAttribute("msg","Teacher Registration successful");
        return "coursereg";
    }
    @PostMapping("/coursedata/")
    public String courseReg(Model model, Course cos)
    {
        courseRepo.save(cos);
        List<Course> courseList=courseRepo.findAll();
        model.addAttribute("courseList",courseList);
        model.addAttribute("msg","Course Registration successful");
        return "subjectreg";
    }
    @PostMapping("/subdatadata/")
    public String subReg(Model model, Subject sub)
    {
        subjectRepo.save(sub);
        List<Subject> subList=subjectRepo.findAll();
        model.addAttribute("subList",subList);
        model.addAttribute("msg","Subject Registration successful");
        return "teacherreg";
    }
    @PostMapping("/teacherdata/")
    public String teacherReg(Model model, Teacher teacher)
    {
        teacherRepo.save(teacher);
        List<Teacher> teacherList=teacherRepo.findAll();
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("msg","Teacher Registration successful");
        return "MainDashBoard";
    }


}
