package persistence;

import java.io.IOException;
import java.util.List;

import model.Term;

// Represents a writer that writes JSON representation of GradeTracker to file
public class JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destinationPath) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws IOException if destination file cannot
    // be opened for writing
    public void open() throws IOException {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of GradeTracker to file
    public void write(List<Term> terms) throws IOException {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer if it is open; may throw IOException if 
    // an IO error occurs while closing
    public void close() throws IOException {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes given JSON string to the destination 
    private void saveToFile(String json) {
        // stub
    }
}
