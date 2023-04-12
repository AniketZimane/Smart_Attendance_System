package com.example.Smart_Attendance_System.Controller;

import com.example.Smart_Attendance_System.Dao.DeparmentRepo;
import com.example.Smart_Attendance_System.Dao.LecturesRepo;
import com.example.Smart_Attendance_System.Dao.StudentRepo;
import com.example.Smart_Attendance_System.Entity.Department;
import com.example.Smart_Attendance_System.Entity.Lectures;
import com.example.Smart_Attendance_System.Entity.Student;
import com.example.Smart_Attendance_System.Helper.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ScanController {
    @Autowired
    DeparmentRepo deparmentRepo;
    @Autowired
    StudentRepo studentRepo;

    @Autowired
    LecturesRepo lecturesRepo;
    @GetMapping("/entry/")
    public String entryFill(Model model)
    {
        List<Department> departmentList=deparmentRepo.findAll();
        System.out.println(departmentList);
        model.addAttribute("departmentList",departmentList);
        return "lectureEntry";
    }
    @PostMapping("/mark/")
    public String markAtt(Model model, Integer idSub, Integer idTeacher, String startTime, String endTime,String email)
    {
        List<Lectures> list = lecturesRepo.findByTeacherIdAndSubjectIdAndMonthAndYear(idTeacher,idSub, LocalDate.now().getMonthValue(),LocalDate.now().getYear());

        Lectures lec = null;

        if(list.isEmpty())
        {
            lec = new Lectures(idTeacher,idSub);
        }
        else
        {
            lec = list.get(0);
            lec.setTotalLectures(lec.getTotalLectures()+1);
        }

        lecturesRepo.save(lec);
        List<Student> stud=studentRepo.findAll();
        List<Lectures> lecturesList=lecturesRepo.findAll();
        System.out.println(lecturesList);
        model.addAttribute("stud",stud);
        model.addAttribute("idSub",idSub);
        model.addAttribute("idTeacher",idTeacher);

        try {
            System.out.println("startTime = " + startTime);
            System.out.println("endTime = " + endTime);

            System.out.println("startTime1 = " + Utils.sdf.format(Utils.sdfUtc.parse(startTime)));
            System.out.println("endTime1 = " + Utils.sdf.format(Utils.sdfUtc.parse(endTime)));

            model.addAttribute("startTime", Utils.sdf.format(Utils.sdfUtc.parse(startTime)));
            model.addAttribute("endTime", Utils.sdf.format(Utils.sdfUtc.parse(endTime)));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "ScanQr";
    }


}
