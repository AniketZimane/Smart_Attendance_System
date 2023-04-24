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
    Integer departmentId;

    public Course() {
    }

    public Course(Integer courseId, String name, Integer departmentId) {
        this.courseId = courseId;
        this.name = name;
        this.departmentId = departmentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }
}
