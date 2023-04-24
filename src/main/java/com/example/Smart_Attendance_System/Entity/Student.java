package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    long enrollno;
    String usertype;
    String firstname;
    String lastname;
    String fathername;
    String mothername;
    String enrollyear;
    String dob;
    String gender;
    Integer age;
    String emailid;
    String country;
    String aadharno;
    String mobileno;
    String deptname;
    String collagename;
    Integer courseId;
    Integer deptId;
    String address;
    String status;
    String password;


    public Student(long enrollno, String usertype, String firstname, String lastname, String fathername, String mothername, String enrollyear, String dob, String gender, Integer age, String emailid, String country, String aadharno, String mobileno, String deptname, String collagename, Integer courseId,Integer deptId, String address, String status, String extension,String password) {
        this.enrollno = enrollno;
        this.usertype = usertype;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fathername = fathername;
        this.mothername = mothername;
        this.enrollyear = enrollyear;
        this.dob = dob;
        this.gender = gender;
        this.age = age;
        this.emailid = emailid;
        this.country = country;
        this.aadharno = aadharno;
        this.mobileno = mobileno;
        this.deptname = deptname;
        this.collagename = collagename;
        this.courseId = courseId;
        this.deptId = deptId;
        this.address = address;
        this.status=status;
        this.extension=extension;
        this.password=password;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String extension;

    public Student() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getEnrollno() {
        return enrollno;
    }

    public void setEnrollno(long enrollno) {
        this.enrollno = enrollno;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getEnrollyear() {
        return enrollyear;
    }

    public void setEnrollyear(String enrollyear) {
        this.enrollyear = enrollyear;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getCollagename() {
        return collagename;
    }

    public void setCollagename(String collagename) {
        this.collagename = collagename;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "Student{" +
                "enrollno=" + enrollno +
                ", usertype='" + usertype + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", fathername='" + fathername + '\'' +
                ", mothername='" + mothername + '\'' +
                ", enrollyear='" + enrollyear + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", emailid='" + emailid + '\'' +
                ", country='" + country + '\'' +
                ", aadharno='" + aadharno + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", deptname='" + deptname + '\'' +
                ", collagename='" + collagename + '\'' +
                ", courseId=" + courseId +
                ", deptId=" + deptId +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }

}
