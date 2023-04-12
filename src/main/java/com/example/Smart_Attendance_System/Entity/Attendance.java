package com.example.Smart_Attendance_System.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Attendance_seq")
    @SequenceGenerator(name="Attendance_seq")
    Integer id;
    @UpdateTimestamp
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:sss")
    LocalDateTime attendanceTime;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Integer subId;
    Long teacherId;
    Long enrollno;
    String email;


    public Attendance() {
    }

    public Attendance(Integer subId, Long teacherId, Long enrollno, LocalDateTime startTime, LocalDateTime endTime,String email) {
        this.startTime = startTime;
        this.email = email;
        this.endTime = endTime;
        this.subId = subId;
        this.teacherId = teacherId;
        this.enrollno = enrollno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Long getEnrollno() {
        return enrollno;
    }

    public void setEnrollno(Long enrollno) {
        this.enrollno = enrollno;
    }

    public void setStartTime(LocalDateTime stratTime) {
        this.startTime = stratTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    public Long getenrollno() {
        return enrollno;
    }

    public void setenrollno(Long enrollno) {
        this.enrollno = enrollno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", attendanceTime=" + attendanceTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", subId=" + subId +
                ", teacherId=" + teacherId +
                ", enrollno=" + enrollno +
                ", email='" + email + '\'' +
                '}';
    }
}
