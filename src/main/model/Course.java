package model;

import java.util.List;

/*
 * Represents a single course taken by a student during a term.
 * Each course has a course code, number of credits, a category,
 * and a list of assignments associated with it.
 */

public class Course {
    private String courseCode;              // the code of the course
    private int credits;                    // number of credits for the course
    private Category category;              // the course's category (core or elective)
    private List<Assignment> assignments;  // list of assignments for this course

    public Course(String courseCode, int credits, Category category) {
        // stub
    }

    public void addAssignment(Assignment a) {
        // stub
    }

    public String getCourseCode() {
        return "";
    }

    public int getCredits() {
        return 0;
    }

    public Category getCategory() {
        return null;
    }

    public List<Assignment> getAssignments() {
        return null;
    }

    public double getCourseAverage() {
        return 0.0;
    }
}
