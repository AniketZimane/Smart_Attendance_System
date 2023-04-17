package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "teacher_seq")
    @SequenceGenerator(name="teacher_seq")
    Integer id;
    String name;
    String department;
    @Column(unique = true)
    String username;
    String password;
    String course;
    String subject;
    public Teacher() {
    }

    public Teacher(String name, String department, String username, String password, String course, String subject) {
        this.name = name;
        this.department = department;
        this.username = username;
        this.password = password;
        this.course = course;
        this.subject = subject;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", course='" + course + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
