package model;

import java.util.List;

/*
 * Represents a school term within an academic year.
 * Each Term has a name (e.g., "Term 1", "Winter 2025") and a list of Courses
 */

public class Term {
    private String termName;        // the name of the term
    private List<Course> courses;   // the list of courses in this term

    public Term(String termName) {
        // stub
    }

    public void addCourse(Course c) {
        // stub
    }

    public String getTermName() {
        return "";
    }

    public List<Course> getCourses() {
        return null;
    }

    public double getTermAverage() {
        return 0.0;
    }
}
