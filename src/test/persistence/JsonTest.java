package persistence;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Assignment;
import model.Course;
import model.Term;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class JsonTest {

    protected void checkAssignment(String name, double weight, double grade, Assignment a) {
        assertEquals(name, a.getName());
        assertEquals(weight, a.getWeight());
        assertEquals(grade, a.getGrade());
    }

    protected void checkCourse(String courseCode, int credits, boolean isCore, int numAssignments, Course c) {
        assertEquals(courseCode, c.getCourseCode());
        assertEquals(credits, c.getCredits());
        assertEquals(isCore, c.checkIsCore());
        assertEquals(numAssignments, c.getAssignments().size());
    }

    protected void checkTerm(String termName, int year, int numCourses, Term t) {
        assertEquals(termName, t.getTermName());
        assertEquals(year, t.getTermYear());
        assertEquals(numCourses, t.getCourses().size());
    }
}
