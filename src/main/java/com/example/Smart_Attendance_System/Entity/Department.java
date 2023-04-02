package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.*;

@Entity
public class Department {
    @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Dept_seq")
            @SequenceGenerator(name="Dept_seq")
    Integer id;
    String deptname;

    public Department() {
    }

    public Department(String deptname) {
        this.deptname = deptname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", deptname='" + deptname + '\'' +
                '}';
    }
}
