package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DeparmentRepo extends JpaRepository<Department,Integer> {
}
