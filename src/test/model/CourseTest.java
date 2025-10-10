package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseTest {
    private Course c1;
    private Course c2;
    private Assignment a1;
    private Assignment a2;
    private Assignment a3;

    @BeforeEach
    void runBefore() {
        c1 = new Course("CPSC_210", 4, true);
        c2 = new Course("ECON_101", 3, false);

        a1 = new Assignment("Homework 1", 0.2, 90.0);
        a2 = new Assignment("Midterm", 0.3, 0.0);
        a3 = new Assignment("Final", 0.5, 75.5);
    }
    
    @Test
    public void testConstructor() {
        assertEquals("CPSC_210", c1.getCourseCode());
        assertEquals(4, c1.getCredits());
        assertTrue(c1.checkIsCore());
        assertEquals("CPSC", c1.getCourseType());
        assertEquals(200, c1.getCourseLevel());

        assertEquals("ECON_101", c2.getCourseCode());
        assertEquals(3, c2.getCredits());
        assertFalse(c2.checkIsCore());
        assertEquals("ECON", c2.getCourseType());
        assertEquals(100, c2.getCourseLevel());
    }

    @Test
    public void testAddAssignment() {
        c1.addAssignment(a1);
        List<Assignment> assignments = c1.getAssignments();
        assertEquals(1, assignments.size());
        c1.addAssignment(a2);
        assertEquals(2, assignments.size());
        assertEquals(a1, assignments.get(0));
        assertEquals(a2, assignments.get(1));
    }

    @Test
    public void testGetCourseAverage() {
        c1.addAssignment(a1);
        c1.addAssignment(a2);
        c1.addAssignment(a3);
        double c1Avg = 0.2 * 90.0 + 0.3 * 0.0 + 0.5 * 75.5;
        assertEquals(c1Avg, c1.getCourseAverage());
        assertEquals(0.0, c2.getCourseAverage());
    }

    @Test
    public void testCourseAverageWithMissingWeights() {
        c1.addAssignment(a1);
        c1.addAssignment(a3);
        double c1Avg = (0.2 * 90 + 0.5 * 75.5) / (0.2 + 0.5);
        assertEquals(c1Avg, c1.getCourseAverage());
    }

    @Test
    public void testGetCourseAverageEmptyCourse() {
        assertEquals(0.0, c1.getCourseAverage());
    }

    @Test
    public void testGetCourseAverageZeroWeights() {
        Assignment assignmentZero = new Assignment("A1", 0.0, 85.0);
        Assignment assignmnentZeroTwo = new Assignment("A2", 0.0, 70.0);
        c1.addAssignment(assignmentZero);
        c1.addAssignment(assignmnentZeroTwo);
        assertEquals(0.0, c1.getCourseAverage());
    }
}
