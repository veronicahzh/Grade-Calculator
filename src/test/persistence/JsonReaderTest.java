package persistence;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Assignment;
import model.Course;
import model.Term;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchGradeTracker.json");
        try {
            List<Term> terms = reader.readTerms();
            fail("IoException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderGeneralGradeTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGradeTracker.json");
        try {
            List<Term> terms = reader.readTerms();
            assertEquals(1, terms.size());

            Term t = terms.get(0);
            checkTerm("Term 1", 2025, 2, t);

            List<Course> courses = t.getCourses();
            Course c0 = courses.get(0);
            checkCourse("CPSC_210", 4, true, 2, c0);
            List<Assignment> a0 = c0.getAssignments();
            checkAssignment("A1", 0.10, 85.0, a0.get(0));
            checkAssignment("Midterm", 0.25, 78.5, a0.get(1));

            Course c1 = courses.get(1);
            checkCourse("ECON_101", 3, false, 1, c1);
            List<Assignment> a1 = c1.getAssignments();
            checkAssignment("Quiz 1", 0.05, 100.0, a1.get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
