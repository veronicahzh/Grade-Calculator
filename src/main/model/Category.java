package model;

import java.util.List;

/*
 * Represents a course category, defining both the subject area
 * (e.g. "CPSC", "MATH") and the course type
 * (e.g. "Core" or "Elective")
 */

public class Category {
    private String subject;         // the subject of the course
    private String courseType;      // the type of course it is
    private List<Course> courses;   // courses belonging to this category

    public Category(String subject, String courseType) {
        // stub
    }

    public void addCourse(Course c) {
        // stub
    }

    public String getSubject() {
        return "";
    }

    public String getCourseType() {
        return "";
    }

    public List<Course> getCourses() {
        return null;
    }

    public void setSubject(String subject) {
        // stub
    }

    public void setCourseType(String type) {
        // stub
    }
    
    public double getCategoryAverage() {
        return 0.0;
    }
}
