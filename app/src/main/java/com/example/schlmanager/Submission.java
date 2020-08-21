package com.example.schlmanager;

public class Submission {

    String name;
    String assignmentName;
    String facultyName;
    String assignUrl;

    public Submission() {

    }

    public Submission(String name, String assignmentName, String facultyName, String assignUrl) {
        this.name = name;
        this.assignmentName = assignmentName;
        this.facultyName = facultyName;
        this.assignUrl = assignUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getAssignUrl() {
        return assignUrl;
    }

    public void setAssignUrl(String assignUrl) {
        this.assignUrl = assignUrl;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "name='" + name + '\'' +
                ", assignmentName='" + assignmentName + '\'' +
                ", facultyName='" + facultyName + '\'' +
                ", assignUrl='" + assignUrl + '\'' +
                '}';
    }
}
