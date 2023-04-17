package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface AttendanceRepo extends JpaRepository<Attendance,Integer> {
//    @Query("select eamil from attendace where subId:subId && startTime:startTime && endTime:endTime")
//    List<Attendance> findByEmailAndSubIdAndStartTimeAndEndTime(String email,Integer subId,LocalDateTime startTime, LocalDateTime endTime);
    List<Attendance> findByEnrollno(Long enrollno);
    List<Attendance> getByEnrollnoAndSubId(Long enrollno,Integer subId);

    public List<Attendance> findByenrollnoAndSubIdAndTeacherIdAndStartTimeAndEndTime(Long enrollno, Integer subId, Long teacherId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("select count(*) from Attendance where subId in (select id from Subject where courseId=:courseId) and enrollno=:enrollno and month(attendanceTime)=:month and year(attendanceTime)=:year")
    int getTotalPresenty(Long enrollno,Integer courseId,Integer month,Integer year);
}
