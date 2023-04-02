package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_seq")
    @SequenceGenerator(name="course_seq")
    Integer id;
    String name;
    Integer id_course;

    public Subject() {
    }
}
