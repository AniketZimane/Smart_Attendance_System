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


    public Attendance() {
    }

    public Attendance(Integer subId, Long teacherId, Long studId) {
        this.subId = subId;
        this.teacherId = teacherId;
        this.studId = studId;
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

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
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
                ", subId=" + subId +
                ", teacherId=" + teacherId +
                ", studId=" + studId +
                '}';
    }
}
