package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;

@Entity
public class Course {
    @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_seq")
            @SequenceGenerator(name="course_seq")
    Integer id;
    String name;

    public Course() {
    }

    public Course(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
