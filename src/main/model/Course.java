package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/*
 * Represents a single course taken by a student during a term.
 * Each course has a course code, number of credits, a category,
 * and a list of assignments associated with it.
 */

public class Course implements Writable {
    private String courseCode;               // the code of the course
    private int credits;                     // number of credits for the course
    private boolean isCore;                  // determines if course is core or elective
    private List<Assignment> assignments;    // list of assignments for this course

    // REQUIRES: courseCode is in format "XXXX_000" where XXXX are letters and 000 are digits
    // EFFECTS: creates a Course with given courseCode, credits, category,
    // and an empty list of assignments
    public Course(String courseCode, int credits, boolean isCore) {
        this.courseCode = courseCode;
        this.credits = credits;
        this.isCore = isCore;
        this.assignments = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the assignment a to the list of assignments for this course
    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    // EFFECTS: returns the course code of this course
    public String getCourseCode() {
        return courseCode;
    }

    // EFFECTS: returns the number of credits for this course
    public int getCredits() {
        return credits;
    }

    // EFFECTS: returns the subject portion of this course's code
    public String getCourseType() {
        return courseCode.split("_")[0];
    }

    // EFFECTS: returns the course level rounded to the nearest hundred
    public int getCourseLevel() {
        int level = Integer.parseInt(courseCode.split("_")[1]);
        return (level / 100) * 100;
    }

    // EFFECTS: returns true if this course is a core course
    public boolean checkIsCore() {
        return isCore;
    }

    // EFFECTS: returns a list of assignments for this course (empty if none)
    public List<Assignment> getAssignments() {
        return assignments;
    }

    // EFFECTS: returns weighted average of all assignments in this course,
    // or 0.0 if there are no assignments
    public double getCourseAverage() {
        if (assignments.isEmpty()) {
            return 0.0;
        }

        double totalWeighted = 0;
        double totalWeight = 0;

        for (Assignment a : assignments) {
            totalWeighted += a.getWeightedContribution();
            totalWeight += a.getWeight();
        }

        if (totalWeight == 0) {
            return 0.0;
        }

        return totalWeighted / totalWeight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courseCode", courseCode);
        json.put("credits", credits);
        json.put("isCore", isCore);

        JSONArray jsonArray = new JSONArray();
        for (Assignment a : assignments) {
            jsonArray.put(a.toJson());
        }
        json.put("assignments", jsonArray);
        return json;
    }

    @Override
    public String toString() {
        return courseCode + " (" + credits + " credits), " + (isCore ? "Core" : "Elective");
    }
}
