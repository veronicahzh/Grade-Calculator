package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

public class AssignmentTest {
    private Assignment a1;
    private Assignment a2;
    private Assignment a3;

    @BeforeEach
    void runBefore() {
        a1 = new Assignment("Homework 1", 0.2, 90.0);
        a2 = new Assignment("Midterm", 0.3, 0.0);
        a3 = new Assignment("Final", 0.5, 75.5);
    }

    @Test
    public void testConstructor() {
        assertEquals("Homework 1", a1.getName());
        assertEquals(0.2, a1.getWeight());
        assertEquals(90.0, a1.getGrade());

        assertEquals("Midterm", a2.getName());
        assertEquals(0.3, a2.getWeight());
        assertEquals(0.0, a2.getGrade());

        assertEquals("Final", a3.getName());
        assertEquals(0.5, a3.getWeight());
        assertEquals(75.5, a3.getGrade());
    }

    @Test
    public void testWeightedContribution() {
        assertEquals((0.2 * 90.0), a1.getWeightedContribution());
        assertEquals((0.3 * 0.0), a2.getWeightedContribution());
        assertEquals((0.5 * 75.5), a3.getWeightedContribution());
    }
}
