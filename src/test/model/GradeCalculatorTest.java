package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GradeCalculatorTest {
    private List<Double> grades;
    private List<Double> weights;
    private List<Double> zeroWeights;
    private List<Double> gpas;

    @BeforeEach
    void runBefore() {
        grades = List.of(90.0, 0.0, 75.5);
        weights = List.of(0.2, 0.3, 0.5);
        zeroWeights = List.of(0.0, 0.0, 0.0);
        gpas = List.of(4.0, 3.7, 3.3);
    }

    @Test
    public void testCalculateWeightedAverageTypical() {
        double expected = (90.0 * 0.2) + (0.0 * 0.3) + (75.5 * 0.5);
        assertEquals(expected, GradeCalculator.calculateWeightedAverage(grades, weights));
    }

    @Test
    public void testCalculateWeightedAverageZeroWeights() {
        assertEquals(0.0, GradeCalculator.calculateWeightedAverage(grades, zeroWeights));
    }

    @Test
    public void testConvertToLetterGrade() {
        assertEquals("A+", GradeCalculator.convertToLetterGrade(90.0));
        assertEquals("A", GradeCalculator.convertToLetterGrade(85.0));
        assertEquals("A-", GradeCalculator.convertToLetterGrade(80.0));
        assertEquals("B+", GradeCalculator.convertToLetterGrade(76.0));
        assertEquals("B", GradeCalculator.convertToLetterGrade(72.0));
        assertEquals("B-", GradeCalculator.convertToLetterGrade(68.0));
        assertEquals("C+", GradeCalculator.convertToLetterGrade(64.0));
        assertEquals("C", GradeCalculator.convertToLetterGrade(60.0));
        assertEquals("C-", GradeCalculator.convertToLetterGrade(55.0));
        assertEquals("D", GradeCalculator.convertToLetterGrade(50.0));
        assertEquals("F", GradeCalculator.convertToLetterGrade(49.0));
    }

    @Test
    public void testLetterToGPA() {
        assertEquals(4.33, GradeCalculator.letterToGPA("A+"));
        assertEquals(4.0, GradeCalculator.letterToGPA("A"));
        assertEquals(3.7, GradeCalculator.letterToGPA("A-"));
        assertEquals(3.3, GradeCalculator.letterToGPA("B+"));
        assertEquals(3.0, GradeCalculator.letterToGPA("B"));
        assertEquals(2.7, GradeCalculator.letterToGPA("B-"));
        assertEquals(2.4, GradeCalculator.letterToGPA("C+"));
        assertEquals(2.0, GradeCalculator.letterToGPA("C"));
        assertEquals(1.5, GradeCalculator.letterToGPA("C-"));
        assertEquals(1.0, GradeCalculator.letterToGPA("D"));
        assertEquals(0.0, GradeCalculator.letterToGPA("F"));
    }

    @Test
    public void testCalculateAverageGPA() {
        double expected = (4.0 + 3.7 + 3.3) / 3.0;
        assertEquals(expected, GradeCalculator.calculateAverageGPA(gpas));
        List<Double> empty = List.of();
        assertEquals(0.0, GradeCalculator.calculateAverageGPA(empty));
    }
}
