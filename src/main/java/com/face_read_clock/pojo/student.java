package com.face_read_clock.pojo;

public class student {
    private int ID;
    private String StuId;
    private String StuName;
    private String StuStatus;
    private String StuClass;
    private String StuSex;
    private String StuClockTime;
    private String StuFace;


    public student(int id, String stuId, String stuName,
                   String stuStatus, String stuClass,
                   String stuSex, String stuClockTime,
                   String stuFace) {
        ID = id;
        StuId = stuId;
        StuName = stuName;
        StuStatus = stuStatus;
        StuClass = stuClass;
        StuSex = stuSex;
        StuClockTime = stuClockTime;
        StuFace = stuFace;
    }

    public student(String stuId, String stuName,
                   String stuStatus, String stuClass,
                   String stuSex, String stuClockTime,
                   String stuFace) {
        StuId = stuId;
        StuName = stuName;
        StuStatus = stuStatus;
        StuClass = stuClass;
        StuSex = stuSex;
        StuClockTime = stuClockTime;
        StuFace = stuFace;
    }

    public student() {

    }

    public String getStuStatus() {
        return StuStatus;
    }

    public void setStuStatus(String stuStatus) {
        StuStatus = stuStatus;
    }

    public String getStuId() {
        return StuId;
    }

    public void setStuId(String stuId) {
        StuId = stuId;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getStuClass() {
        return StuClass;
    }

    public void setStuClass(String stuClass) {
        StuClass = stuClass;
    }

    public String getStuSex() {
        return StuSex;
    }

    public void setStuSex(String stuSex) {
        StuSex = stuSex;
    }

    public String getStuClockTime() {
        return StuClockTime;
    }

    public void setStuClockTime(String stuClockTime) {
        StuClockTime = stuClockTime;
    }


    public String getStuFace() {
        return StuFace;
    }

    public void setStuFace(String stuFace) {
        StuFace = stuFace;
    }

    @Override
    public boolean equals(Object stu) {
        if (this == stu) {
            return true;
        }

        if (stu == null || getClass() != stu.getClass()) {
            return false;
        }

        student student = (student) stu;

        if (this.StuId == null) {
            return student.StuId == null;
        } else return this.StuId.equals(student.StuId);
    }


    public int getID() {
        return ID;
    }
}
