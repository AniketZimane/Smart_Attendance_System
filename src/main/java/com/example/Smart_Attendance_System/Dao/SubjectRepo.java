package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subject,Integer> {
    @Query("select name from Subject where id=:id")
    String getNameById(Integer id);

    @Query("from Subject where name=:name")
    List<Subject> findByName(String name);
}
