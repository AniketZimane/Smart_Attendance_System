package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Student;
import com.example.Smart_Attendance_System.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TeacherRepo extends JpaRepository<Teacher,Integer> {
    @Query("select name from Teacher where id=:id")
    String getNameById(Integer id);
    List<Teacher> findByName(String name);
}
