package model;

import java.util.List;

/*
 * Calculates averages and GPAs.
 */

public class GradeCalculator {

    private GradeCalculator() {
        // stub
    }

    // REQUIRES: grades and weights are not null and the same size, each weight >= 0
    // EFFECTS: returns the weighted average, returns 0.0 if total weight is 0
    public static double calculateWeightedAverage(List<Double> grades, List<Double> weights) {
        return 0.0;
    }

    // REQUIRES: 0 <= avg <= 100
    // EFFECTS: returns corresponding letter grade based on UBC grading scale
    public static String convertToLetterGrade(double avg) {
        return "";
    }

    // EFFECTS: converts letter grade into GPA based on UBC grading scale
    public static double letterToGPA(String letter) {
        return 0.0;
    }
    
    // EFFECTS: returns the mean of all GPA values in the list,
    // returns 0.0 if the list is empty
    public static double calculateAverageGPA(List<Double> gpas) {
        return 0.0;
    }
}
