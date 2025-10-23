package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/*
 * Represents a school term within an academic year.
 * Each Term has a name (e.g., "Term 1", "Winter 2025") and a list of Courses
 */

public class Term implements Writable {
    private String termName;        // the name of the term
    private List<Course> courses;   // the list of courses in this term
    private int year;

    // EFFECTS: creates a Term with the given termName and year,
    // and an empty list of courses
    public Term(String termName, int year) {
        this.termName = termName;
        this.year = year;
        this.courses = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the course c to the list of courses for this term
    public void addCourse(Course c) {
        courses.add(c);
    }

    // EFFECTS: returns the name of this term
    public String getTermName() {
        return termName;
    }

    // EFFECTS: returns the year of this term
    public int getTermYear() {
        return year;
    }

    // EFFECTS: returns a list of courses in this term
    // if there are no courses, return nothing
    public List<Course> getCourses() {
        return courses;
    }

    // EFFECTS: returns the credit-weighted average of all courses
    // in this term, or 0.0 if there are no courses
    public double getTermAverage() {
        if (courses.isEmpty()) {
            return 0.0;
        }

        double totalWeighted = 0;
        double totalCredits = 0;

        for (Course c : courses) {
            double courseAverage = c.getCourseAverage();
            totalWeighted += courseAverage * c.getCredits();
            totalCredits += c.getCredits();
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return totalWeighted / totalCredits;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("termName", termName);
        json.put("year", year);

        JSONArray jsonArray = new JSONArray();
        for (Course c : courses) {
            jsonArray.put(c.toJson());
        }
        json.put("courses", jsonArray);
        return json;
    }
}
