package org.aston.learning.stage1.sort;

import org.aston.learning.stage1.model.Student;

import java.util.Comparator;

public class StudentGroupNumberComparator implements Comparator<Student> {

    @Override
    public int compare(Student student1, Student student2) {
        return student1.getGroupNumber().compareToIgnoreCase(student2.getGroupNumber());
    }
}
