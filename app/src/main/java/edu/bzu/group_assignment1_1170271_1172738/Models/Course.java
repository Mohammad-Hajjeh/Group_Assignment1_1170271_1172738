package edu.bzu.group_assignment1_1170271_1172738.Models;

public class Course {
    private String code;
    private String name;
    private String number;
    private String lecturer;
    private String day;
    private String time;
    private String place;

    public Course() {
    }

    public Course(String code, String name, String number, String lecturer, String day, String time, String place) {
        this.code = code;
        this.name = name;
        this.number = number;
        this.lecturer = lecturer;
        this.day = day;
        this.time = time;
        this.place = place;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
