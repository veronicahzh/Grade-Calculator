package model;

/*
 * Represents a graded component of a course, such as an assignment,
 * quiz, test, or lab. Each assignment has a name, weight, and grade.
 */

public class Assignment {
    private String name;    // name of the assignment
    private double weight;  // weight of this assignment
    private double grade;   // grade received on this assignment (%)

    public Assignment(String name, double weight, double grade) {
        // stub
    }

    public String getName() {
        return "";
    }

    public double getWeight() {
        return 0.0;
    }

    public double getGrade() {
        return 0.0;
    }

    public double getWeightedContribution() {
        return 0.0;
    }
}
