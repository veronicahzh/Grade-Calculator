package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseTest {
    Course course1;
    Course course2;
    private Assignment a1;
    private Assignment a2;
    private Assignment a3;

    @BeforeEach
    void runBefore() {
        course1 = new Course("CPSC_210", 4, true);
        course2 = new Course("ECON_101", 3, false);

        a1 = new Assignment("Homework 1", 0.2, 90.0);
        a2 = new Assignment("Miderm", 0.3, 0.0);
        a3 = new Assignment("Final", 0.5, 75.5);
    }
    
    @Test
    public void testConstructor() {
        assertEquals("CPSC_210", course1.getCourseCode());
        assertEquals(4, course1.getCredits());
        assertTrue(course1.checkIsCore());
        assertEquals("CPSC", course1.getCourseType());
        assertEquals(200, course1.getCourseLevel());

        assertEquals("ECON_101", course2.getCourseCode());
        assertEquals(3, course2.getCredits());
        assertFalse(course2.checkIsCore());
        assertEquals("ECON", course2.getCourseType());
        assertEquals(100, course2.getCourseLevel());
    }

    @Test
    public void testAddAssignment() {
        course1.addAssignment(a1);
        List<Assignment> assignments = course1.getAssignments();
        assertEquals(1, assignments.size());
        course1.addAssignment(a2);
        assertEquals(2, assignments.size());
        assertEquals(a1, assignments.get(0));
        assertEquals(a2, assignments.get(1));
    }

    @Test
    public void testGetCourseAverage() {
        course1.addAssignment(a1);
        course1.addAssignment(a2);
        course1.addAssignment(a3);
        assertEquals((0.2 * 90.0 + 0.3 * 0.0 + 0.5 * 75.5), course1.getCourseAverage());
        assertEquals(0.0, course2.getCourseAverage());
    }

    @Test
    public void testCourseAverageWithMissingWeights() {
        course1.addAssignment(a1);
        course1.addAssignment(a3);
        assertEquals(((0.2 * 90 + 0.5 * 75.5)/(0.2 + 0.5)), course1.getCourseAverage());
    }
}
