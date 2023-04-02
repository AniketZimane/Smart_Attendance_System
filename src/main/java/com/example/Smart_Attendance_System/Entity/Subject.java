package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_seq")
    @SequenceGenerator(name="course_seq")
    Integer id;
    String name;
    Integer subId;

    public Subject() {
    }

    public Subject(String name, Integer subId) {
        this.name = name;
        this.subId = subId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subId=" + subId +
                '}';
    }
}
