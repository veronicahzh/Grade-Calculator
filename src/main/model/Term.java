package model;

import java.util.List;

/*
 * Represents a school term within an academic year.
 * Each Term has a name (e.g., "Term 1", "Winter 2025") and a list of Courses
 */

public class Term {
    private String termName;        // the name of the term
    private List<Course> courses;   // the list of courses in this term
    private int year;

    // EFFECTS: creates a Term with the given termName and year,
    // and an empty list of courses
    public Term(String termName, int year) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds the course c to the list of courses for this term
    public void addCourse(Course c) {
        // stub
    }

    // EFFECTS: returns the name of this term
    public String getTermName() {
        return "";
    }

    // EFFECTS: returns a list of courses in this term
    // if there are no courses, return nothing
    public List<Course> getCourses() {
        return null;
    }

    // EFFECTS: returns the credit-weighted average of all courses
    // in this term, or 0.0 if there are no courses
    public double getTermAverage() {
        return 0.0;
    }
}
