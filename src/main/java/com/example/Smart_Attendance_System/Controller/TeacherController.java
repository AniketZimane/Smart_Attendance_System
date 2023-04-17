package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.*;
import com.example.Smart_Attendance_System.Entity.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TeacherController {

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    DeparmentRepo deparmentRepo;
    @Autowired
    AttendanceRepo attendanceRepo;
    @Autowired
    LecturesRepo lecturesRepo;

    @Autowired
    private JavaMailSender mailSender;

    int maxSize=5;
    @PostMapping("/departmentdata/")
    public String deptreg(Model model, Department department)
    {
        deparmentRepo.save(department);
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("departmentList",departmentList);
        model.addAttribute("msg","Department Registration successful");
        return "departmentreg";
    }
    @PostMapping("/coursedata/")
    public String courseReg(Model model, Course cos)
    {
        courseRepo.save(cos);
        List<Course> courseList=courseRepo.findAll();
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("departmentList",departmentList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("msg","Course Registration successful");
        return "coursereg";
    }
    @PostMapping("/subdatadata/")
    public String subReg(Model model, String name, Integer idCourse) throws MessagingException, UnsupportedEncodingException {

        subjectRepo.save(new Subject(name, idCourse));
        List<Course> courseList=courseRepo.findAll();
        List<Subject> subList=subjectRepo.findAll();

        model.addAttribute("courseList",courseList);
        model.addAttribute("subList",subList);
        model.addAttribute("courseRepo", courseRepo);
        model.addAttribute("msg","Subject Registration successful");

//        String from = "ad.developer@gmail.com";
//        String to = "aniketzimane@gmail.com";
//        MimeMessage message=mailSender.createMimeMessage();
//        MimeMessageHelper helper=new MimeMessageHelper(message,"utf-8");
////        SimpleMailMessage message=new SimpleMailMessage();
//
//
//        String maiSubject="To verifying attendence";
////        String mailContent="<h1>You have successfully marked your attendence please verify your attendence</h1><br>"
////                +http:localhost:8080;
//
//        helper.setFrom(from,"Innovative Things");
//        helper.setTo(to);
//        helper.setSubject(maiSubject);
//        helper.setText("To confirm your account, please click here : "
//                +"http://localhost:8080/confirm-account?token=");
////        helper.setText(mailContent,true);
//        mailSender.send(message);
        return "subjectreg";
    }
    @PostMapping("/teacherdata/")
    public String teacherReg(Model model, Teacher teacher)
    {
        teacherRepo.save(teacher);
        List<Teacher> teacherList=teacherRepo.findAll();
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("msg","Teacher Registration successful");
        return "teacherreg";
    }
    @GetMapping("/departmentRegistration/")
    public String deotReg(Model model)
    {
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("departmentList",departmentList);
        return "departmentreg";
    }
    @GetMapping("/courseRegistration/")
    public String courseReg(Model model)
    {
        List<Course> courseList=courseRepo.findAll();
        model.addAttribute("courseList",courseList);
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("departmentList",departmentList);
        return "coursereg";
    }
    @GetMapping("/subjectRegistration/")
    public String subReg(Model model)
    {
        List<Course> courseList=courseRepo.findAll();
        List<Subject> subList=subjectRepo.findAll();
        model.addAttribute("courseList",courseList);
        model.addAttribute("subList",subList);
        model.addAttribute("courseRepo", courseRepo);
        model.addAttribute("subList",subList);
        return "subjectReg";
    }
    @GetMapping("/teacherRegistration/")
    public String teacherReg(Model model)
    {
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("departmentList",departmentList);
        List<Teacher> teacherList=teacherRepo.findAll();
        model.addAttribute("teacherList",teacherList);
        return "teacherreg";
    }
    @GetMapping("/retriveid/{enrollno}/")
    public String findByEnrollno(Model model,@PathVariable Long enrollno) {
        Student stud = studentRepo.getReferenceById(enrollno);
//        System.out.print(stud.toString());
        model.addAttribute("stud", stud);

        return "GenerateId";
    }
    @GetMapping("/attendencemarkdetails/{subId}/{teacherId}/")
    public String attendanceDetails(Model model,@PathVariable Integer subId,@PathVariable Integer teacherId) {
        Subject sub = subjectRepo.getReferenceById(subId);
        Teacher teach=teacherRepo.getReferenceById(teacherId);
        model.addAttribute("teach", teach);
        model.addAttribute("sub", sub);
        return "TeacherInfo";
    }
    @GetMapping("/tech/delete/{id}/")
    public String deleteTeacherRecord(Model model, @PathVariable Integer id)
    {
        teacherRepo.deleteById(id);
        Pageable pageable = PageRequest.of(0, maxSize, Sort.by("id").descending());
        Page<Teacher> page = teacherRepo.findAll(pageable);
        int totalPages = page.getTotalPages();
        List<Teacher> listTech = page.toList();
        List<Teacher> teacherList=teacherRepo.findAll();
        model.addAttribute("teacherList",teacherList);

        if(totalPages < 1)
        {
            totalPages = 1;
        }

        model.addAttribute("listTech", listTech);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("curPage", 1);
        return "teacherreg";
    }
    @GetMapping("/course/delete/{courseId}/")
    public String deleteCourseRecord(Model model, @PathVariable Integer courseId)
    {
        courseRepo.deleteById(courseId);
        Pageable pageable = PageRequest.of(0, maxSize, Sort.by("id").descending());
        Page<Course> page = courseRepo.findAll(pageable);
        int totalPages = page.getTotalPages();
        List<Course> listCourse = page.toList();
        List<Course> courseList=courseRepo.findAll();
        model.addAttribute("courseList",courseList);

        if(totalPages < 1)
        {
            totalPages = 1;
        }

        model.addAttribute("listCourse", listCourse);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("curPage", 1);
        return "coursereg";
    }


    @GetMapping("/department/delete/{id}/")
    public String deleteDepartmentRecord(Model model, @PathVariable Integer id)
    {
        deparmentRepo.deleteById(id);
        Pageable pageable = PageRequest.of(0, maxSize, Sort.by("id").descending());
        Page<Department> page = deparmentRepo.findAll(pageable);
        int totalPages = page.getTotalPages();
        List<Department> listdept = page.toList();
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("departmentList",departmentList);

        if(totalPages < 1)
        {
            totalPages = 1;
        }

//        model.addAttribute("listdept", listdept);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("curPage", 1);
        return "departmentreg";
    }



//    @GetMapping("/sendEmail/")
//    public void sendEmail(HttpServletRequest request, String email, Integer subId, LocalDateTime startTime, LocalDateTime endTime) throws MessagingException, UnsupportedEncodingException {
//        List<Attendance> at=attendanceRepo.findByEmailAndSubIdAndStartTimeAndEndTime(email,subId,startTime,endTime);
//        String from = "ad.developer@gmail.com";
//        String to = at.toString();
//        MimeMessage message=mailSender.createMimeMessage();
//        MimeMessageHelper helper=new MimeMessageHelper(message,"utf-8");
////        SimpleMailMessage message=new SimpleMailMessage();
//
//
//        String maiSubject="To verifying attendence";
//        String mailContent="<h1>You have successfully marked your attendence please verify your attendence</h1><br><p>Use following link to review your attendence and view your id card</p><br>";
//               mailContent+= "<a href='https://localhost:8080/student/login'><a/>";
//        helper.setFrom(from,"Innovative Things");
//        helper.setTo(to);
//        helper.setSubject(maiSubject);
//        helper.setText(mailContent,true);
//        mailSender.send(message);
//
//    }

    @GetMapping("/passkey/")
    public String passKey(Model model)
    {
        List<String>emailList=attendanceRepo.findAll().stream().map(x->x.getEmail()).collect(Collectors.toList());
        Long atcount = attendanceRepo.count();
        System.out.print(atcount);
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        model.addAttribute("emailList",emailList);
        model.addAttribute("atcount",atcount);
        model.addAttribute("otp",otp);
        return "Passkey";
    }
    @GetMapping("/verification/")
    public String verification(Model model)
    {
        return "PasskeyValidation";
    }
    @GetMapping("/teacher/login/")
    public String teacherLogin(Model model)
    {
        return "teacherlogin";
    }
    @PostMapping("/teacher/validate/")
    public String teacherValidate(Model model,Integer id,String password)
    {
        Teacher tech = null;
        String originalpassword=null;
        try{
            tech=teacherRepo.getReferenceById(id);
            originalpassword=tech.getPassword();

        }
        catch (Exception e){
            System.out.println("Login failed");
            model.addAttribute("msg","No user found");
            return "teacherlogin";
        }
        if (password.equals(originalpassword))
        {
            List<Department> departmentList=deparmentRepo.findAll();
            List<Teacher> teacherList=teacherRepo.findAll();
            System.out.println(departmentList);
            List<Course> courseList=courseRepo.findAll();
            List<Subject> subjectList=subjectRepo.findAll();
            List<Lectures> lecturesList=lecturesRepo.findAll();
            System.out.println(lecturesList);
            model.addAttribute("departmentList",lecturesList);
            model.addAttribute("teacherList",teacherList);

            model.addAttribute("departmentList",departmentList);
            model.addAttribute("courseList",courseList);
            model.addAttribute("subjectList",subjectList);

            model.addAttribute("tech",tech);
            model.addAttribute("msg","Login successfull");
            System.out.println("Login successfull");
            return "lectureEntry";
        }
        else {
            model.addAttribute("msg","Invalid password");
            return "teacherlogin";
        }

    }
    @GetMapping("/teacher/lecture/entry/{id}/")
    public String markAttendance(Model model,@PathVariable Integer id)
    {
        Teacher tech=teacherRepo.getReferenceById(id);
        List<Department> departmentList=deparmentRepo.findAll();
        List<Teacher> teacherdata=teacherRepo.findAll();
        System.out.println(departmentList);
        List<Course> courseList=courseRepo.findAll();
        List<Subject> subjectList=subjectRepo.findAll();
        List<Lectures> lecturesList=lecturesRepo.findAll();
        System.out.println(lecturesList);
        model.addAttribute("departmentList",lecturesList);
        model.addAttribute("teacherdata",teacherdata);

        model.addAttribute("departmentList",departmentList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("subjectList",subjectList);

        model.addAttribute("tech",tech);
        return "lectureEntry";
    }
    @GetMapping("/addmysubject/")
    public String teacherandsubject(Model model)
    {
        List<Department> departmentList=deparmentRepo.findAll();
        List<Course> courseList=courseRepo.findAll();
        List<Subject> subList=subjectRepo.findAll();
        List<Teacher> teacherdata=teacherRepo.findAll();
        model.addAttribute("teacherdata",teacherdata);
        model.addAttribute("departmentList",departmentList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("subList",subList);
        return "teacherandsubject";
    }
    @PostMapping("/teachersubjectdata/")
    public String teachersubjectdataregistration(Model model,Teacher tech)
    {
        teacherRepo.save(tech);
        List<Teacher> teacherdata=teacherRepo.findAll();
        model.addAttribute("teacherdata",teacherdata);
        return"teacherandsubject";
    }
    @GetMapping("/summeryreport/")
    public String getreport(Model model)
    {
        List<Course> courseList=courseRepo.findAll();
        model.addAttribute("courseList",courseList);
        return "attendancereportentry";
    }
    @PostMapping("/attendancereport2/")
    public String summeryReport(Model model,Integer courseId,Integer month,Integer year)
    {

//        int totalPresenty= attendanceRepo.getTotalPresenty(enrollno,courseId,month,year);
        int totallectures=lecturesRepo.getTotalLecturesByCourse(courseId);
        List<Student> studentList=studentRepo.findByCourseId(courseId);
        List<Course> courseList=courseRepo.findAll();
        model.addAttribute("studentList",studentList);
        model.addAttribute("attendanceRepo",attendanceRepo);
        model.addAttribute("courseId",courseId);
        model.addAttribute("month",month);
        model.addAttribute("year",year);
        model.addAttribute("courseList",courseList);
        model.addAttribute("totallectures",totallectures);

        return "attendanceSummaryreport";
    }
    @PostMapping("/generatesummeryreport/")
    public String generatesummeryreport(Model model,Long enrollno,Integer courseId,Integer month,Integer year)
    {
        List<Subject> subjectList=subjectRepo.findAll();
        int totalPresenty= attendanceRepo.getTotalPresenty(enrollno,courseId,month,year);
        int totallectures=lecturesRepo.getTotalLecturesByCourse(courseId);
        int atpersent=(totalPresenty/totallectures)*100;
        if(atpersent<75)
        {
            model.addAttribute("stutus","Not Eligible");
        }
        else{
            model.addAttribute("stutus","Eligible");
        }
        System.out.println(atpersent);
        model.addAttribute("atpersent",atpersent);
        model.addAttribute("subjectList",subjectList);
        return"attendanceSummaryreport";
    }

    @GetMapping("/generateattendencereportforteacher/")
    public String atRecord2(Model model)
    {
        List<Attendance> attendedList=attendanceRepo.findAll();
        System.out.println(attendedList);
        model.addAttribute("attendedList",attendedList);
        model.addAttribute("subjectRepo",subjectRepo);
        return "teachersideattendance";
    }

    @GetMapping("/attendencemarkdetails/delete/{id}/")
    public String deleteatrecord(Model model, @PathVariable Integer id)
    {
        attendanceRepo.deleteById(id);
        List<Attendance> attendedList=attendanceRepo.findAll();
        System.out.println(attendedList);
        model.addAttribute("attendedList",attendedList);
        model.addAttribute("subjectRepo",subjectRepo);
        return "teachersideattendance";
    }

    @GetMapping("/GenerateAttendenceReport/delete/{id}/")
    public String deleteatrecordofadmin(Model model, @PathVariable Integer id)
    {
        attendanceRepo.deleteById(id);
        List<Attendance> attendedList=attendanceRepo.findAll();
        System.out.println(attendedList);
        model.addAttribute("attendedList",attendedList);
        model.addAttribute("subjectRepo",subjectRepo);
        return "AttendanceTable";
    }

}
