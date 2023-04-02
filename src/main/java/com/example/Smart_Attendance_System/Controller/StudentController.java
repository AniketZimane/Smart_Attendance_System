package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.*;
import com.example.Smart_Attendance_System.Entity.*;
import com.example.Smart_Attendance_System.Helper.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    @GetMapping("/")
    public String dashboard(Model model)
    {
        Long userCount = sturepo.count();
        model.addAttribute("userCount", userCount);
        return "MainDashBoard";
    }
    @GetMapping("/database/")
    public String recordOfStudent()
    {

        return "Record";
    }
    @GetMapping("/login/")
    public String login()
    {

        return "LoginPage";
    }
    @GetMapping("/register/")
    public String register()
    {
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
    public String submitData(Model model, Student stu, MultipartFile file)
    {
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

        if(totalPages < 1)
        {
            totalPages = 1;
        }

        model.addAttribute("listEmp", listEmp);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("curPage", 1);
        return "RecordPage";
    }
    @GetMapping("/record/")
    public String displayRecode(Model model)
    {
        List<Student> stulist=sturepo.findAll();
        model.addAttribute("stulist",stulist);
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
        return "lectureEntry";
    }

    @GetMapping("/setting/")
    public String setting(Model model)
    {
        return "deparmentreg";
    }
    @RequestMapping(value = "/attendancemark/", method = RequestMethod.POST)
    public @ResponseBody String handleRequest(@RequestParam("myParameter") String myParameter) {
        System.out.println(myParameter);
        return "coursereg";
    }

    @PostMapping("/markattendance/")
    @ResponseBody
    public Integer addEmp(Model model,Long studId,Integer subId,Long teacherId)
    {
        int responce = 0;
        List<Attendance> list = attendanceRepo.findByStudIdAndSubIdAndTeacherId(studId,subId,teacherId);
        if(list.isEmpty())
        {
            Attendance attendance=new Attendance(subId,teacherId,studId);
            Attendance at = attendanceRepo.save(attendance);
            responce=1;
        }

        return responce;
    }

}
