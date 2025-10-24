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
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGradeTracker() {
        try {
            List<Term> terms = List.of();

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGradeTracker.json");
            writer.open();
            writer.write(terms);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGradeTracker.json");
            List<Term> loaded = reader.readTerms();
            assertTrue(loaded.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGradeTracker() {
        try {
            List<Term> terms = sampleState();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGradeTracker.json");
            writer.open();
            writer.write(terms);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGradeTracker.json");
            List<Term> loaded = reader.readTerms();

            assertGeneralState(loaded);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private List<Term> sampleState() {
        Term t = new Term("Term 1", 2025);

        Course c0 = new Course("CPSC_210", 4, true);
        c0.addAssignment(new Assignment("A1", 0.10, 85.0));
        c0.addAssignment(new Assignment("Midterm", 0.25, 78.5));
        t.addCourse(c0);

        Course c1 = new Course("ECON_101", 3, false);
        c1.addAssignment(new Assignment("Quiz 1", 0.05, 100.0));
        t.addCourse(c1);

        return List.of(t);
    }

    private void assertGeneralState(List<Term> loaded) {
        assertEquals(1, loaded.size());
        Term term = loaded.get(0);
        checkTerm("Term 1", 2025, 2, term);

        Course course0 = term.getCourses().get(0);
        checkCourse("CPSC_210", 4, true, 2, course0);
        checkAssignment("A1", 0.1, 85.0, course0.getAssignments().get(0));
        checkAssignment("Midterm", 0.25, 78.5, course0.getAssignments().get(1));

        Course course1 = term.getCourses().get(1);
        checkCourse("ECON_101", 3, false, 1, course1);
        checkAssignment("Quiz 1", 0.05, 100.0, course1.getAssignments().get(0));
    }
}
