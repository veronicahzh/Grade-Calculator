package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Assignment;
import model.Course;
import model.Term;

// Represents a reader that reads GradeTracker from JSON data stored in file
public class JsonReader {
    private String source;
    
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads term from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Term> readTerms() throws IOException {
        String text = Files.readString(Path.of(source), StandardCharsets.UTF_8);
        JSONObject root = new JSONObject(text);

        List<Term> terms = new ArrayList<>();
        JSONArray termsArray = root.optJSONArray("terms");
        if (termsArray != null) {
            for (int i = 0; i < termsArray.length(); i++) {
                JSONObject jsonTerm = termsArray.getJSONObject(i);
                terms.add(parseTerm(jsonTerm));
            }
        }
        return terms;
    }

    // EFFECTS: parses term from JSON object and returns it
    private Term parseTerm(JSONObject jsonTerm) {
        String termName = jsonTerm.getString("termName");
        int year = jsonTerm.getInt("year");
        Term term = new Term(termName, year);

        JSONArray coursesArray = jsonTerm.optJSONArray("courses");
        if (coursesArray != null) {
            addCourses(term, coursesArray);
        }
        return term;
    }

    // EFFECTS: parses course from JSON object and returns it
    private Course parseCourse(JSONObject jsonCourse) {
        String courseCode = jsonCourse.getString("courseCode");
        int credits = jsonCourse.getInt("credits");
        boolean isCore = jsonCourse.getBoolean("isCore");
        Course course = new Course(courseCode, credits, isCore);

        JSONArray assignmentsArray = jsonCourse.optJSONArray("assignments");
        if (assignmentsArray != null) {
            addAssignments(course, assignmentsArray);
        }
        return course;
    }

    // EFFECTS: parses assignment from JSON object and returns it
    private Assignment parseAssignment(JSONObject jsonAssignment) {
        String name = jsonAssignment.getString("name");
        double weight = jsonAssignment.getDouble("weight");
        double grade = jsonAssignment.getDouble("grade");
        return new Assignment(name, weight, grade);
    }

    // MODIFIES: term
    // EFFECTS: parses each course JSONObject in array and adds to term
    private void addCourses(Term term, JSONArray coursesArray) {
        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject jsonCourse = coursesArray.getJSONObject(i);
            term.addCourse(parseCourse(jsonCourse));
        }
    }

    // MODIFIES: course
    // EFFECTS: parses each assignment JSONObject in array and adds to course
    private void addAssignments(Course course, JSONArray assignmentsArray) {
        for (int i = 0; i < assignmentsArray.length(); i++) {
            JSONObject jsonAssignment = assignmentsArray.getJSONObject(i);
            course.addAssignment(parseAssignment(jsonAssignment));
        }
    }
}
