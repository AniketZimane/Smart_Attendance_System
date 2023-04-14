package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Student;
import com.example.Smart_Attendance_System.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StudentRepo extends JpaRepository<Student,Long> {
    @Query("select password from Student where enrollno=:enrollno")
    String gePassword(Long enrollno);

}
