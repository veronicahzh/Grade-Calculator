package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Unit tests for the Event class. */
public class EventTest {

    private Event event;
    private Date date;

    @BeforeEach
    public void runBefore() {
        event = new Event("Sensor open at door");
        date = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Sensor open at door", event.toString());
    }
}
