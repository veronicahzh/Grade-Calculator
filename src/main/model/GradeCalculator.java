package model;

import java.util.List;

/*
 * Calculates averages and GPAs.
 */

public class GradeCalculator {

    private GradeCalculator() {
    }
    // REQUIRES: grades and weights are not null and the same size, each weight >= 0
    // EFFECTS: returns the weighted average, returns 0.0 if total weight is 0
    
    public static double calculateWeightedAverage(List<Double> grades, List<Double> weights) {
        double totalWeighted = 0.0;
        double totalWeight = 0.0;

        for (int i = 0; i < grades.size(); i++) {
            totalWeighted += grades.get(i) * weights.get(i);
            totalWeight += weights.get(i);
        }

        if (totalWeight == 0.0) {
            return 0.0;
        }

        return totalWeighted;
    }

    // REQUIRES: 0 <= avg <= 100
    // EFFECTS: returns corresponding letter grade based on UBC grading scale
    public static String convertToLetterGrade(double avg) {
        if (avg >= 90) {
            return "A+";
        } else if (avg >= 85) {
            return "A";
        } else if (avg >= 80) {
            return "A-";
        } else if (avg >= 76) {
            return "B+";
        } else if (avg >= 72) {
            return "B";
        } else if (avg >= 68) {
            return "B-";
        } else if (avg >= 64) {
            return "C+";
        } else if (avg >= 60) {
            return "C";
        } else if (avg >= 55) {
            return "C-";
        } else if (avg >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    // EFFECTS: converts letter grade into its GPA value based on UBC 4.33 scale
    public static double letterToGPA(String letter) {
        switch (letter) {
            case "A+": return 4.33;
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.4;
            case "C": return 2.0;
            case "C-": return 1.5;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }
    
    // EFFECTS: returns the mean of all GPA values in the list,
    // returns 0.0 if the list is empty
    public static double calculateAverageGPA(List<Double> gpas) {
        if (gpas.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (double gpa : gpas) {
            total += gpa;
        }

        return total / gpas.size();
    }
}
