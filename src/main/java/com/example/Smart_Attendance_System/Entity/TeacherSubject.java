package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
public class TeacherSubject {
    @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "teachersubject_seq")
            @SequenceGenerator(name="teachersubject_seq")
    Integer id;
    Long teacherId;
    Integer subjectId;

    public TeacherSubject() {
    }

    public TeacherSubject(Long teacherId, Integer subjectId) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "TeacherSubject{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", subjectId=" + subjectId +
                '}';
    }
}
