package model;

import java.util.List;

/*
 * Represents a single course taken by a student during a term.
 * Each course has a course code, number of credits, a category,
 * and a list of assignments associated with it.
 */

public class Course {
    private String courseCode;               // the code of the course
    private int credits;                     // number of credits for the course
    private boolean isCore;                  // determines if course is core or elective
    private List<Assignment> assignments;    // list of assignments for this course

    // EFFECTS: creates a Course with given courseCode, credits, category,
    // and an empty list of assignments 
    public Course(String courseCode, int credits, boolean isCore) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds the assignment a to the list of assignments for this course
    public void addAssignment(Assignment a) {
        // stub
    }

    // EFFECTS: returns the course code of this course
    public String getCourseCode() {
        return "";
    }

    // EFFECTS: returns the number of credits for this course
    public int getCredits() {
        return 0;
    }

    // EFFECTS: returns the category of this course
    public String getCourseType() {
        return "";
    }

    // EFFECTS: returns the level of this course
    public int getCourseLevel() {
        return 0;
    }

    // EFFECTS: returns true if this course is a core course
    public boolean checkIsCore() {
        return false;
    }

    // EFFECTS: returns a list of assignments in this course
    // if there are no assignments, returns nothing
    public List<Assignment> getAssignments() {
        return null;
    }

    // EFFECTS: returns weighted average of all assignments in this course,
    // or 0.0 if there are no assignments
    public double getCourseAverage() {
        return 0.0;
    }
}
