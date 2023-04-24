package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Subject;
import com.example.Smart_Attendance_System.Entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherSubjectRepo extends JpaRepository<TeacherSubject,Integer> {
    @Query("from Subject where id in (select subjectId from TeacherSubject where teacherId=:teacherId)")
    List<Subject> findSubjectByTeacherId(Integer teacherId);
}
