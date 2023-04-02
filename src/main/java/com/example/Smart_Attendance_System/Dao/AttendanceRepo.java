package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AttendanceRepo extends JpaRepository<Attendance,Integer> {
//    @Query("select * from Attendance where studId=:studId and subId=:subId and teacherId=:teacherId")
//    public List<Attendance> checkAttendance(Long studId,Integer subId,Long teacherId);

    public List<Attendance> findByStudIdAndSubIdAndTeacherId(Long studId,Integer subId,Long teacherId);
}
