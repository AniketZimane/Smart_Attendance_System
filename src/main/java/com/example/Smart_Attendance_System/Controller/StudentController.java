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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StudentController {
    int maxSize=10;
//    @Autowired
//    StudentRepo studentRepo;
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
        List <Student> studentList=studentRepo.findAll();
        List <Department> departmentList=deparmentRepo.findAll();
        Long userCount = studentRepo.count();
        Long courseCount = courseRepo.count();
        Long teacherCount = teacherRepo.count();
        Long departmentCount = deparmentRepo.count();
        Long subjectCount = subjectRepo.count();
        List<Integer>teacherid=lecturesRepo.findAll().stream().map(x->x.getTeacherId()).collect(Collectors.toList());
        List<Integer>totallectures=lecturesRepo.findAll().stream().map(x->x.getTotalLectures()).collect(Collectors.toList());
        List<Integer>subjectId=lecturesRepo.findAll().stream().map(x->x.getSubjectId()).collect(Collectors.toList());
        List<Lectures> lecturedata=lecturesRepo.findAll();
        model.addAttribute("teacherid", teacherid);
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("studentList", studentList);
        model.addAttribute("studentRepo", studentRepo);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("lecturedata", lecturedata);
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
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("courseList",courseList);
        model.addAttribute("departmentList",departmentList);
        return "Registration";
    }

    @GetMapping("/student/edit/{enrollno}/")
    public String editStudent(Model model, @PathVariable long enrollno)
    {
        Student stud = studentRepo.getReferenceById(enrollno);
        List<Course> courseList=courseRepo.findAll();
        List<Department> departmentList=deparmentRepo.findAll();
        model.addAttribute("courseList",courseList);
        model.addAttribute("departmentList",departmentList);
        model.addAttribute("stud", stud);
        return "Registration";
    }

    @GetMapping("/generateid/")
    public String generateId(Model model, Student stu)
    {
        Student fetch=studentRepo.save(stu);
        model.addAttribute("stu", fetch);
        return "GenerateId";
    }
    @PostMapping("/savedata/")
    public String submitData(Model model, Student stu, MultipartFile file) throws MessagingException, UnsupportedEncodingException {

        String resultPage = "GenerateId";
      
        List<Student> listStudentExisting = studentRepo.findAllById(Collections.singleton(stu.getEnrollno()));

        if(listStudentExisting.isEmpty())
        {
            String fileNameOld;
            fileNameOld = file.getOriginalFilename();
            String extension = fileNameOld.substring(fileNameOld.indexOf(".") + 1);
            stu.setExtension(extension);

            Student stuNew = studentRepo.save(stu);
            String fileNameNew = stuNew.getEnrollno()+ "." + extension;

            System.out.println("Image New Name is " + fileNameNew);
            uploader.uploadFile(file, fileNameNew);
            Pageable pageable = PageRequest.of(0, maxSize, Sort.by("enrollno").descending());
            Page<Student> page = studentRepo.findAll(pageable);
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
            String mailContent="<h1>Welcome to Innovative THings</h1><br><p>Your Registration Completed successfully</p><br><a href='https://localhost:8080/student/login/'>Student Login<a/>";
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
        }
        else{
            List<Course> courseList=courseRepo.findAll();
            List<Department> departmentList=deparmentRepo.findAll();
            model.addAttribute("courseList",courseList);
            model.addAttribute("departmentList",departmentList);
            model.addAttribute("msg","Record is already exist");
            resultPage = "Registration";

        }

        return resultPage;

    }
    @GetMapping("/stu/delete/{stuId}/")
    public String deleteRecord(Model model, @PathVariable long stuId)
    {
        studentRepo.deleteById(stuId);
        Pageable pageable = PageRequest.of(0, maxSize, Sort.by("enrollno").descending());
        Page<Student> page = studentRepo.findAll(pageable);
        int totalPages = page.getTotalPages();
        List<Student> listEmp = page.toList();
        List<Student> stulist=studentRepo.findAll();
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
        List<Student> stulist=studentRepo.findAll(Sort.by(colName));
        model.addAttribute("stulist",stulist);
        model.addAttribute("colName",colName);
        return "Record";
    }
    @GetMapping("/attendancereport/")
    public String reportDaily(Model model)
    {
        List<String>namelist=studentRepo.findAll().stream().map(x->x.getLastname()).collect(Collectors.toList());
        List<Integer>agelist=studentRepo.findAll().stream().map(x->x.getAge()).collect(Collectors.toList());
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
        System.out.println("StartTime "+ startTime);
        System.out.println("endtime "+endTime);
        //following line mark attendence if record is present(email)
        email=stud.getEmailid().toString();
        System.out.println(email);
        LocalTime conStartTime = LocalTime.parse(startTime, Utils.formatter);
        LocalTime conEndTime = LocalTime.parse(endTime, Utils.formatter);

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
        model.addAttribute("subjectrepo",subjectRepo);
        model.addAttribute("teacherrepo",teacherRepo);
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

//    @GetMapping("/student/record/{enrollno}/")
//    public String getattendacereview2(Model model,@PathVariable Long enrollno)
//    {
//        List<Subject> sublist=subjectRepo.findAll();
//        model.addAttribute("sublist",sublist);
//
//        int courseId=1;
//        int month=4;
//        int year=2023;
//        int totalPresenty= attendanceRepo.getTotalPresenty(enrollno,courseId,month,year);
//        int totallectures=lecturesRepo.getTotalLecturesByCourse(courseId);
//        System.out.println(totallectures);
//        System.out.println(totalPresenty);
//
//        double atpersent=(totalPresenty/totallectures)*100;
//        System.out.println(atpersent);
//        model.addAttribute("atpersent",atpersent);
//
//        model.addAttribute("enrollno",enrollno);
//        return "attendancesummeryofstudentside";
//    }
    @PostMapping("/student/displayattendancesummery/")
    public String displayattendancesummery(Model model)
    {
        return "attendancesummeryofstudentside";
    }
    @GetMapping("/adminattendanceentry/")
    public String adminattendanceentry(Model model)
    {
        List<Course> courseList=courseRepo.findAll();
        model.addAttribute("courseList",courseList);
        return "adminatttendancereport";
    }

    @PostMapping("/adminattendancereport2/")
    public String summeryReportadmin(Model model,Integer courseId,Integer month,Integer year)
    {

//        int totalPresenty= attendanceRepo.getTotalPresenty(enrollno,courseId,month,year);
        try {
            int totallectures=lecturesRepo.getTotalLecturesByCourse(courseId);
            List<Student> studentList=studentRepo.findByCourseId(courseId);
            List<Course> courseList=courseRepo.findAll();

            model.addAttribute("studentList",studentList);
            model.addAttribute("attendanceRepo",attendanceRepo);
            model.addAttribute("courseRepo",courseRepo);
            model.addAttribute("courseId",courseId);
            model.addAttribute("month",month);
            model.addAttribute("year",year);
            model.addAttribute("courseList",courseList);
            model.addAttribute("totallectures",totallectures);
        }catch (Exception e)
        {
            model.addAttribute("msg","Attendance Not Found");
        }


        return "adminattendancesummery";
    }
    @GetMapping("/forgotPassword/")
    public String forgotpage()
    {
        return "forgetpassword";
    }

    @GetMapping("/coursesDept/{deptname}/")
    @ResponseBody
    public List<Course> getCoursesByDeptId(@PathVariable String deptname)
    {
        return courseRepo.findByDepartment(deptname);
    }
//    @GetMapping("/login/")
//    public String loginPageAdmin(Model model)
//    {
//        return "/admindashboard/";
//    }

}
