package com.example.Smart_Attendance_System.Dao;

import com.example.Smart_Attendance_System.Entity.ConfigProp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepo extends JpaRepository<ConfigProp,String> {
    @Query("select val from ConfigProp where prop=:prop")
    String getVal(String prop);

}
