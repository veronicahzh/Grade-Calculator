package model;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a student using the Grade Tracker application.
 * Each user has a name, an ID, and a list of years representing
 * their academic record over time.
 */

public class User {
    private String name;        // the user's name
    private String id;          // the user's ID
    private List<Year> years;   // list of academic years associated with this user

    public User(String name, String id) {
        // stub
    }

    public void AddYear(Year y) {
        // stub
    }

    public List<Year> getYears() {
        return null;
    }

    public String getName() {
        return null;
    }

    public String getId() {
        return null;
    }

    public double getOverallGPA() {
        return 0.0;
    }
}
