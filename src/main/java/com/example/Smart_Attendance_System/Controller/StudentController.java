package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.StudentRepo;
import com.example.Smart_Attendance_System.Entity.Student;
import com.example.Smart_Attendance_System.Helper.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class StudentController {
    int maxSize=10;
    @Autowired
    StudentRepo sturepo;
    @Autowired
    FileUploader uploader;
    @GetMapping("/")
    public String dashboard()
    {
        return "Dashboard";
    }
    @GetMapping("/register/")
    public String register()
    {
        return "Registration";
    }

    @GetMapping("/generateid/")
    public String generateId()
    {
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
        List<Student> stulist=sturepo.findAll();
        model.addAttribute("stulist",stulist);
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
        return "RecordPage";
    }
    @GetMapping("/attendancereport/")
    public String reportDaily(Model model)
    {
        List<Student> stulist=sturepo.findAll();
        model.addAttribute("stulist",stulist);
        return "AttendanceTable";
    }
    @GetMapping("/scancard/")
    public String markAttendance(Model model)
    {
        return "ScanQr";
    }



}
