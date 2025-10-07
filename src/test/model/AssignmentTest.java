package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class AssignmentTest {
    Assignment testAssignment;
    Assignment testAssignment2;
    Assignment testAssignment3;

    @BeforeEach
    void runBefore() {
        testAssignment = new Assignment("Homework 1", 0.2, 90.0);
        testAssignment2 = new Assignment("Test 1", 0.5, 75.5);
        testAssignment3 = new Assignment("Lab 1", 0.3, 0.0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Homework 1", testAssignment.getName());
        assertEquals(0.2, testAssignment.getWeight());
        assertEquals(90.0, testAssignment.getGrade());

        assertEquals("Test 1", testAssignment2.getName());
        assertEquals(0.5, testAssignment2.getWeight());
        assertEquals(75.5, testAssignment2.getGrade());

        assertEquals("Lab 1", testAssignment3.getName());
        assertEquals(0.3, testAssignment3.getWeight());
        assertEquals(0.0, testAssignment3.getGrade());
    }

    @Test
    public void testWeightedContribution() {
        assertEquals((0.2 * 90.0), testAssignment.getWeightedContribution());
        assertEquals((0.5 * 75.5), testAssignment2.getWeightedContribution());
        assertEquals((0.3 * 0.0), testAssignment3.getWeightedContribution());
    }
}
