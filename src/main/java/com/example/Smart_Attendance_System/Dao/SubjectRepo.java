package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SubjectRepo extends JpaRepository<Subject,Integer> {

}
