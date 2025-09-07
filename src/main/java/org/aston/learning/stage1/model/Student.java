package org.aston.learning.stage1.model;

public class Student implements Comparable<Student> {
    private String groupNumber;
    private double averageGrade;
    private String recordBookNumber;

    public Student() {}

    public Student(String groupNumber, double averageGrade, String recordBookNumber) {
        this.groupNumber = groupNumber;
        this.averageGrade = averageGrade;
        this.recordBookNumber = recordBookNumber;
    }

    // Геттеры и сеттеры
    public String getGroupNumber() { return groupNumber; }
    public void setGroupNumber(String groupNumber) { this.groupNumber = groupNumber; }
    public double getAverageGrade() { return averageGrade; }
    public void setAverageGrade(double averageGrade) { this.averageGrade = averageGrade; }
    public String getRecordBookNumber() { return recordBookNumber; }
    public void setRecordBookNumber(String recordBookNumber) { this.recordBookNumber = recordBookNumber; }

    @Override
    public int compareTo(Student anotherStudent) {
        return this.getRecordBookNumber().compareToIgnoreCase(anotherStudent.getRecordBookNumber());
    }
}