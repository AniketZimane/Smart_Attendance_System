package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;

@Entity
public class Lectures {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Lecture_seq")
    @SequenceGenerator(name="Lecture_seq")
    Integer id;
    Integer teacherId;
    Integer subjectId;
    Integer year;
    Integer month;
    Integer totalLectures;

    public Lectures() {
    }

    public Lectures(Integer teacherId, Integer subjectId) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.year =  LocalDate.now().getYear();
        this.month = LocalDate.now().getMonthValue();
        this.totalLectures = 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getTotalLectures() {
        return totalLectures;
    }

    public void setTotalLectures(Integer totalLectures) {
        this.totalLectures = totalLectures;
    }

    @Override
    public String toString() {
        return "Lectures{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", subjectId=" + subjectId +
                ", year=" + year +
                ", month=" + month +
                ", totalLectures=" + totalLectures +
                '}';
    }
}
