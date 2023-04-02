package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;

@Entity
public class Lectures {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Lecture_seq")
    @SequenceGenerator(name="Lecture_seq")
    Integer id;
    Integer year;
    String month;
    Integer subId;
    Integer totalLectures;

    public Lectures() {
    }

    public Lectures(Integer year, String month, Integer subId, Integer totalLectures) {
        this.year = year;
        this.month = month;
        this.subId = subId;
        this.totalLectures = totalLectures;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
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
                ", year=" + year +
                ", month='" + month + '\'' +
                ", subId=" + subId +
                ", totalLectures=" + totalLectures +
                '}';
    }
}
