package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of alarm system events.
 * Singleton so there is only one EventLog shared across the app.
 */
public class EventLog implements Iterable<Event> {
    /** The only EventLog in the system (Singleton). */
    private static EventLog theLog;

    /** Collected events in insertion order. */
    private final Collection<Event> events;

    /**
     * Prevent external construction (Singleton).
     */
    private EventLog() {
        events = new ArrayList<>();
    }

    /**
     * Gets the singleton instance of EventLog, creating it if needed.
     *
     * @return the shared EventLog instance
     */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    /**
     * Adds an event to the event log.
     *
     * @param e the event to add
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    /**
     * Clears the event log and logs that it was cleared.
     */
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
