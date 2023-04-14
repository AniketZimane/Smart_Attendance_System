package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.*;
import com.example.Smart_Attendance_System.Entity.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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



    @GetMapping("/sendEmail/")
    public void sendEmail(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        List<Attendance> at=attendanceRepo.findAll();
        String from = "ad.developer@gmail.com";
        String to = "aniketzimane@gmail.com";
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,"utf-8");
//        SimpleMailMessage message=new SimpleMailMessage();


        String maiSubject="To verifying attendence";
        String mailContent="<h1>You have successfully marked your attendence please verify your attendence</h1><br>";
               mailContent+= "<a href='https://localhost:8080'><a/>";
        helper.setFrom(from,"Innovative Things");
        helper.setTo(to);
        helper.setSubject(maiSubject);
        helper.setText(mailContent,true);
        mailSender.send(message);

    }

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


}
