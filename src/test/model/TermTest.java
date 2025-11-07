package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

public class TermTest {
    private Term t1;
    private Term t2;
    private Course c1;
    private Course c2;
    private Assignment a1;
    private Assignment a2;
    private Assignment a3;

    @BeforeEach
    void runBefore() {
        t1 = new Term("Winter Term 1", 2024);
        t2 = new Term("Summer Term 2", 2025);

        c1 = new Course("CPSC_210", 4, true);
        c2 = new Course("ECON_101", 3, false);

        a1 = new Assignment("Homework 1", 0.2, 90.0);
        a2 = new Assignment("Midterm", 0.3, 0.0);
        a3 = new Assignment("Final", 0.5, 75.5);
    }

    @Test
    public void testConstructor() {
        assertEquals("Winter Term 1", t1.getTermName());
        assertEquals(2024, t1.getTermYear());
        assertTrue(t1.getCourses().isEmpty());
        assertEquals(0.0, t1.getTermAverage());

        assertEquals("Summer Term 2", t2.getTermName());
        assertEquals(2025, t2.getTermYear());
        assertTrue(t2.getCourses().isEmpty());
        assertEquals(0.0, t2.getTermAverage());
    }

    @Test
    public void testAddCourse() {
        assertTrue(t1.getCourses().isEmpty());
        assertTrue(t2.getCourses().isEmpty());
        t1.addCourse(c1);
        t2.addCourse(c1);
        assertEquals(c1, t1.getCourses().get(0));
        assertEquals(1, t1.getCourses().size());
        assertEquals(c1, t2.getCourses().get(0));
        assertEquals(1, t2.getCourses().size());
        t1.addCourse(c2);
        t2.addCourse(c1);
        assertEquals(c2, t1.getCourses().get(1));
        assertEquals(2, t1.getCourses().size());
        assertEquals(c1, t2.getCourses().get(1));
        assertEquals(2, t2.getCourses().size());
    }

    @Test
    public void testGetTermAverage() {
        assertEquals(0.0, t1.getTermAverage());
        t1.addCourse(c1);
        c1.addAssignment(a1);
        c1.addAssignment(a2);
        c1.addAssignment(a3);
        double c1Avg = 0.2 * 90.0 + 0.3 * 0.0 + 0.5 * 75.5;
        assertEquals(c1Avg, t1.getTermAverage());
        t1.addCourse(c2);
        c2.addAssignment(a1);
        c2.addAssignment(a3);
        double c2Avg = (0.2 * 90 + 0.5 * 75.5) / (0.2 + 0.5);
        double c1Weight = c1Avg * 4.0;
        double c2Weight = c2Avg * 3.0;
        assertEquals(((c1Weight + c2Weight) / (4.0 + 3.0)), t1.getTermAverage());
    }

    @Test
    public void testGetTermAverageWithZeroCreditCourse() {
        Course zeroCredit = new Course("MATH_100", 0, true);
        zeroCredit.addAssignment(new Assignment("Hw", 0.5, 90.0));
        t1.addCourse(zeroCredit);
        assertEquals(0.0, t1.getTermAverage());
    }

    @Test
    void testRemoveCoursePresent() {
        t1.addCourse(c1);
        t1.addCourse(c2);
        assertEquals(2, t1.getCourses().size());
        t1.removeCourse(c1);
        assertEquals(1, t1.getCourses().size());
        assertEquals(c2, t1.getCourses().get(0));
    }

    @Test
    void testRemoveCourseNotPresent() {
        t1.addCourse(c1);
        assertEquals(1, t1.getCourses().size());
        t1.removeCourse(c2);
        assertEquals(1, t1.getCourses().size());
        assertEquals(c1, t1.getCourses().get(0));
    }
}
