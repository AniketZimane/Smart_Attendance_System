package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.*;
import com.example.Smart_Attendance_System.Entity.*;
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
    @Autowired
    LecturesRepo lecturesRepo;
    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    CourseRepo courseRepo;



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
            model.addAttribute("enrollno",enrollno);
            model.addAttribute("studentRepo",studentRepo);
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
        return "retriveIdCard";
    }

//    @GetMapping("/attendance/{enrollno}/")
//    public String getMyAttendance(Model model,@PathVariable Long enrollno)
//    {
//        int courseId=1;
//        int month=4;
//        int year=2023;
//        List<Attendance> attendedList =attendanceRepo.findByEnrollno(enrollno);
//        Student studentList=studentRepo.getReferenceById(enrollno);
//        int totalPresenty= attendanceRepo.getTotalPresenty(enrollno,courseId,month,year);
//        int totallectures=lecturesRepo.getTotalLecturesByCourse(courseId);
//        System.out.println(totallectures);
//        System.out.println(totalPresenty);
//
//        float atpersent=(float) ((totalPresenty/totallectures)*100);
//        System.out.println(atpersent);
//        model.addAttribute("atpersent",atpersent);
//
//        model.addAttribute("studentList", studentList);
//        model.addAttribute("attendedList", attendedList);
//        return "StudentAttendancetable";
//    }
    @GetMapping("/attendance/{enrollno}/")
    public String getMyAttendance(Model model,@PathVariable Long enrollno)
    {
        Student stud = studentRepo.getReferenceById(enrollno);
        List<Subject> subjectList=subjectRepo.findAll();
        List<Course> courseList=courseRepo.findAll();
        model.addAttribute("stud",stud);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("courseList",courseList);
        return "studentattendancestatus";
    }

    @PostMapping("/filterattendacne/")
    public String filterattendecne(Model model,Integer month,Integer year,Long enrollno,Integer courseId)
    {
        List<Attendance> attendedList =attendanceRepo.findByEnrollno(enrollno);
        Course course=courseRepo.getReferenceById(courseId);
        Student student=studentRepo.getReferenceById(enrollno);

        int totalPresenty= attendanceRepo.getTotalPresenty(enrollno,courseId,month,year);
        int totallectures=lecturesRepo.getTotalLecturesByCourse(courseId);
        System.out.println("Total lectures"+totallectures);
        System.out.println("Total presenty"+totalPresenty);
        double atpersent=100*totalPresenty/totallectures;

        System.out.println("attendance  of student"+atpersent);
        model.addAttribute("atpersent",atpersent);
        model.addAttribute("course",course);
        model.addAttribute("student", student);
        model.addAttribute("attendedList", attendedList);
        return "attendancesummeryofstudentside";
    }
    @GetMapping("/record/{enrollno}/")
    public String getStudentAttendance(Model model,@PathVariable Long enrollno)
    {
        List<Attendance> attendanceList=attendanceRepo.findByEnrollno(enrollno);
        model.addAttribute("attendanceList",attendanceList);
        model.addAttribute("subjectRepo",subjectRepo);
        return "StudentAttendancetable";
    }

}
