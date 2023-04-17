package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Lectures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LecturesRepo extends JpaRepository<Lectures,Integer> {
    List<Lectures> findByTeacherIdAndSubjectIdAndMonthAndYear(Integer teacherId,Integer subjectId,Integer month,Integer year);

    @Query("select sum(totalLectures) from Lectures where subjectId in (select id from Subject where courseId=:courseId)")
    int getTotalLecturesByCourse(int courseId);



}
