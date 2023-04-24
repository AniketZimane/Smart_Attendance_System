package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Attendance;
import com.example.Smart_Attendance_System.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course,Integer> {
    @Query("select name from Course where courseId = :courseId")
    String getNameByCourseId(Integer courseId);

    @Query("from Course where departmentId in (select id from Department where deptname = :deptname)")
    List<Course> findByDepartment(String deptname);
}
