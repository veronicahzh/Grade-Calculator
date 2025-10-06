package model;

/*
 * Represents a graded component of a course, such as an assignment,
 * quiz, test, or lab. Each assignment has a name, weight, and grade.
 */

public class Assignment {
    private String name;    // name of the assignment
    private double weight;  // weight of this assignment
    private double grade;   // grade received on this assignment (%)

    /*
    * REQUIRES: 0 <= weight <= 1, 0 <= grade <= 100
    * creates an Assignment with the given name, weight and grade
    */

    public Assignment(String name, double weight, double grade) {
        // stub
    }

    // EFFECTS: returns the name of this assignment
    public String getName() {
        return "";
    }

    // EFFECTS: returns the weight of this assignment (between 0 and 1)
    public double getWeight() {
        return 0.0;
    }

    // EFFECTS: returns the grade received for this assignment (0 to 100)
    public double getGrade() {
        return 0.0;
    }

    // EFFECTS: returns grade * weight
    public double getWeightedContribution() {
        return 0.0;
    }
}
