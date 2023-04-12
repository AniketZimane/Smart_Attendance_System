package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface AttendanceRepo extends JpaRepository<Attendance,Integer> {
//    @Query("select * from Attendance where enrollno=:enrollno and subId=:subId and teacherId=:teacherId")
//    public List<Attendance> checkAttendance(Long enrollno,Integer subId,Long teacherId);

    public List<Attendance> findByenrollnoAndSubIdAndTeacherIdAndStartTimeAndEndTime(Long enrollno, Integer subId, Long teacherId, LocalDateTime startTime, LocalDateTime endTime);
}
