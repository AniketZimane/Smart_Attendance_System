package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.*;
import com.example.Smart_Attendance_System.Entity.*;
import com.example.Smart_Attendance_System.Helper.FileUploader;
import com.example.Smart_Attendance_System.Helper.Utils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StudentController {
    int maxSize=10;
    @Autowired
    StudentRepo sturepo;
    @Autowired
    AttendanceRepo attendanceRepo;
    @Autowired
    FileUploader uploader;

    @Autowired
    CourseRepo courseRepo;
    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    DeparmentRepo deparmentRepo;
    @Autowired
    LecturesRepo lecturesRepo;

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    JavaMailSender mailSender;

    @GetMapping("/admindashboard/")
    public String dashboard(Model model)
    {
        Long userCount = sturepo.count();
        Long courseCount = courseRepo.count();
        Long teacherCount = teacherRepo.count();
        Long departmentCount = deparmentRepo.count();
        Long subjectCount = subjectRepo.count();
        List<Integer>teacherid=lecturesRepo.findAll().stream().map(x->x.getTeacherId()).collect(Collectors.toList());
        List<Integer>totallectures=lecturesRepo.findAll().stream().map(x->x.getTotalLectures()).collect(Collectors.toList());
        model.addAttribute("teacherid", teacherid);
        model.addAttribute("totallectures", totallectures);
        model.addAttribute("userCount", userCount);
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("teacherCount", teacherCount);
        model.addAttribute("departmentCount", departmentCount);
        model.addAttribute("subjectCount", subjectCount);
        return "MainDashBoard";
    }
    @GetMapping("/database/")
    public String recordOfStudent()
    {

        return "Record";
    }
    @GetMapping("/login")
    public String login()
    {
        return "LoginPage";
    }
    @GetMapping("/register/")
    public String register(Model model)
    {
        List<Course> courseList=courseRepo.findAll();
        model.addAttribute("courseList",courseList);
        return "Registration";
    }

    @GetMapping("/student/edit/{enrollno}/")
    public String editStudent(Model model, @PathVariable long enrollno)
    {
        Student stud = studentRepo.getReferenceById(enrollno);
        model.addAttribute("stud", stud);
        return "Registration";
    }

    @GetMapping("/generateid/")
    public String generateId(Model model, Student stu)
    {
        Student fetch=sturepo.save(stu);
        model.addAttribute("stu", fetch);
        return "GenerateId";
    }
    @PostMapping("/savedata/")
    public String submitData(Model model, Student stu, MultipartFile file) throws MessagingException, UnsupportedEncodingException {
        String fileNameOld;
        fileNameOld = file.getOriginalFilename();
        String extension = fileNameOld.substring(fileNameOld.indexOf(".") + 1);
        stu.setExtension(extension);

        Student stuNew = sturepo.save(stu);
        String fileNameNew = stuNew.getEnrollno()+ "." + extension;

        System.out.println("Image New Name is " + fileNameNew);
        uploader.uploadFile(file, fileNameNew);
        Pageable pageable = PageRequest.of(0, maxSize, Sort.by("enrollno").descending());
        Page<Student> page = sturepo.findAll(pageable);
        int totalPages = page.getTotalPages();
        if(totalPages < 1)
        {
            totalPages = 1;
        }

        model.addAttribute("stud", stuNew);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("curPage", 1);
        model.addAttribute("msg","Employee saved successfully");

        String from = "ad.developer@gmail.com";
        String to = stuNew.getEmailid();
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String maiSubject="To verifying attendence";
        String mailContent="<h1>Welcome to Innovative THings</h1><br><p>Your Registration Completed successfully</p>";
        mailContent+= "<a href='https://localhost:8080/UserDashBoard/'><a/>";
        helper.setFrom(from,"Innovative Things");
        helper.setTo(to);
        helper.setSubject(maiSubject);
        helper.setText(mailContent,true);

//        helper.addAttachment("/assets/img/swiftui.png", new ClassPathResource("/assets/img/swiftui.png"));
        try
        {
            FileSystemResource res = new FileSystemResource(new ClassPathResource("static/assets/img/logo.png").getFile());
            helper.addInline("swiftui.png", res);
            mailSender.send(message);
        }catch (Exception e)
        {
            System.out.println(e);
        }
        return "GenerateId";

    }
    @GetMapping("/stu/delete/{stuId}/")
    public String deleteRecord(Model model, @PathVariable long stuId)
    {
        sturepo.deleteById(stuId);
        Pageable pageable = PageRequest.of(0, maxSize, Sort.by("enrollno").descending());
        Page<Student> page = sturepo.findAll(pageable);
        int totalPages = page.getTotalPages();
        List<Student> listEmp = page.toList();
        List<Student> stulist=sturepo.findAll();
        model.addAttribute("stulist",stulist);

        if(totalPages < 1)
        {
            totalPages = 1;
        }

        model.addAttribute("listEmp", listEmp);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("curPage", 1);
        return "Record";
    }
    @GetMapping("/sub/delete/{subId}/")
    public String subjectDelete(Model model, @PathVariable Integer subId)
    {
        subjectRepo.deleteById(subId);
        Pageable pageable = PageRequest.of(0, maxSize, Sort.by("id").descending());
        Page<Subject> page = subjectRepo.findAll(pageable);
        int totalPages = page.getTotalPages();
        List<Subject> listsub = page.toList();
        List<Subject> sublist=subjectRepo.findAll();
        model.addAttribute("sublist",sublist);

        if(totalPages < 1)
        {
            totalPages = 1;
        }

        model.addAttribute("listsub", listsub);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("curPage", 1);
        return "subjectreg";
    }
    @GetMapping("/record/{colName}/")
    public String displayRecode(Model model, @PathVariable String colName)
    {
        List<Student> stulist=sturepo.findAll(Sort.by(colName));
        model.addAttribute("stulist",stulist);
        model.addAttribute("colName",colName);
        return "Record";
    }
    @GetMapping("/attendancereport/")
    public String reportDaily(Model model)
    {
        List<String>namelist=sturepo.findAll().stream().map(x->x.getLastname()).collect(Collectors.toList());
        List<Integer>agelist=sturepo.findAll().stream().map(x->x.getAge()).collect(Collectors.toList());
        model.addAttribute("name",namelist);
        model.addAttribute("age",agelist);
        return "pychart";
    }

    @GetMapping("/scancard/")
    public String markAttendance(Model model)
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
        return "lectureEntry";
    }

    @GetMapping("/setting/")
    public String setting(Model model,Department dept)
    {
        List<Department> departmentList=deparmentRepo.findAll();
        System.out.println(departmentList);
        model.addAttribute("departmentList",departmentList);
        return "departmentreg";
    }
    @RequestMapping(value = "/attendancemark/", method = RequestMethod.POST)
    public @ResponseBody String handleRequest(@RequestParam("myParameter") String myParameter) {
        System.out.println(myParameter);
        return "coursereg";
    }

    @PostMapping("/markattendance/")
    @ResponseBody
    public Integer addEmp(Model model, Long enrollno, Integer subId, Long teacherId, String startTime, String endTime,String email)
    {
        Student stud = studentRepo.getReferenceById(enrollno);
        //following line mark attendence if record is present(email)
        email=stud.getEmailid().toString();
        System.out.println(email);
        LocalDateTime conStartTime = LocalDateTime.parse(startTime, Utils.formatter);
        LocalDateTime conEndTime = LocalDateTime.parse(endTime, Utils.formatter);

        int responce = 0;
        List<Attendance> list = attendanceRepo.findByenrollnoAndSubIdAndTeacherIdAndStartTimeAndEndTime(enrollno,subId,teacherId,conStartTime, conEndTime);

        if(list.isEmpty())
        {

            Attendance attendance=new Attendance(subId,teacherId,enrollno,conStartTime,conEndTime,email);
            Attendance at = attendanceRepo.save(attendance);
            responce=1;
        }

        return responce;
    }
    @GetMapping("/GenerateAttendenceReport/")
    public String atRecord(Model model)
    {
        List<Attendance> attendedList=attendanceRepo.findAll();

        System.out.println(attendedList);
        model.addAttribute("attendedList",attendedList);
        return "AttendanceTable";
    }

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
    @GetMapping("/attendancesummary/")
    public String summery(Model model)
    {
        return "attendanceSummaryreport";
    }

    @GetMapping("/student/record/{enrollno}/")
    public String getattendacereview2(Model model,@PathVariable Long enrollno)
    {
        List<Subject> sublist=subjectRepo.findAll();
        model.addAttribute("sublist",sublist);

        int courseId=1;
        int month=4;
        int year=2023;
        int totalPresenty= attendanceRepo.getTotalPresenty(enrollno,courseId,month,year);
        int totallectures=lecturesRepo.getTotalLecturesByCourse(courseId);
        System.out.println(totallectures);
        System.out.println(totalPresenty);

        double atpersent=(totalPresenty/totallectures)*100;
        System.out.println(atpersent);
        model.addAttribute("atpersent",atpersent);

        model.addAttribute("enrollno",enrollno);
        return "attendancesummeryofstudentside";
    }
//    @PostMapping("/student/displayattendancesummery/")
//    public String displayattendancesummery(Model model)
//    {
//        return "attendancesummeryofstudentside";
//    }



}
