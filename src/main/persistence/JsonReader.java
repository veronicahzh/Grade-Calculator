package persistence;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Assignment;
import model.Course;
import model.Term;

// Represents a reader that reads GradeTracker from JSON data stored in file
public class JsonReader {
    
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads term from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Term> readTerms() throws IOException {
        return null;
    }

    // EFFECTS: parses term from JSON object and returns it
    private Term parseTerm(JSONObject jTerm) {
        return null;
    }

    // EFFECTS: parses course from JSON object and returns it
    private Course parseCourse(JSONObject jCourse) {
        return null;
    }

    // EFFECTS: parses assignment from JSON object and returns it
    private Assignment parseAssignment(JSONObject jAssignment) {
        return null;
    }

    // MODIFIES: term
    // EFFECTS: parses each course JSONObject in array and adds to term
    private void addCourses(Term term, JSONArray coursesArray) {
        // stub
    }

    // MODIFIES: course
    // EFFECTS: parses each assignment JSONObject in array and adds to course
    private void addAssignments(Course course, JSONArray assignmentsArray) {
        // stub
    }
}
