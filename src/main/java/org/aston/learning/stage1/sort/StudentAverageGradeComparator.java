package org.aston.learning.stage1.sort;

import org.aston.learning.stage1.model.Student;

import java.util.Comparator;

public class StudentAverageGradeComparator implements Comparator<Student> {

    @Override
    public int compare(Student student1, Student student2) {
        double cmp = student1.getAverageGrade() - student2.getAverageGrade();
        if (cmp == 0) {
            return 0;
        } else if (cmp > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
