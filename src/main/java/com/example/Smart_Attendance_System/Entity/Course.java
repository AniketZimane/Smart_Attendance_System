package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;

@Entity
public class Course {
    @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_seq")
            @SequenceGenerator(name="course_seq")
    Integer courseId;
    @Column(unique = true)
    String name;

    public Course() {
    }

    public Course(Integer courseId, String name) {
        this.courseId = courseId;
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                '}';
    }
}
