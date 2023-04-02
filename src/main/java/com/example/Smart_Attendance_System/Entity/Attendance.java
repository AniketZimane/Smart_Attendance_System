package com.example.Smart_Attendance_System.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Attendance_seq")
    @SequenceGenerator(name="Attendance_seq")
    Integer id;
    @UpdateTimestamp
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:sss")
    LocalDateTime dateTime;
    Integer subId;
    Long teacherId;
    Long studId;
    String department;

    public Attendance() {
    }

    public Attendance(LocalDateTime dateTime, Long teacherId, Long studId,String department) {
        this.dateTime = dateTime;
        this.teacherId = teacherId;
        this.studId = studId;
        this.department = department;
    }



    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getStudId() {
        return studId;
    }

    public void setStudId(Long studId) {
        this.studId = studId;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", teacherId=" + teacherId +
                ", studId=" + studId +
                ", department='" + department + '\'' +
                '}';
    }

}
