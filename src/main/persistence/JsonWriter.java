package persistence;

import java.io.*;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Term;

// Represents a writer that writes JSON representation of GradeTracker to file
public class JsonWriter {
    private static final int TAB = 2;
    private PrintWriter writer;
    private String destination;
    
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws IOException if destination file cannot
    // be opened for writing
    public void open() throws IOException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of GradeTracker to file
    public void write(List<Term> terms) throws IOException {
        JSONObject root = new JSONObject();
        JSONArray array = new JSONArray();
        for (Term t : terms) {
            array.put(t.toJson());
        }
        root.put("terms", array);
        saveToFile(root.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer if it is open; may throw IOException if 
    // an IO error occurs while closing
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }

    // MODIFIES: this
    // EFFECTS: writes given JSON string to the destination 
    private void saveToFile(String json) {
        writer.print(json);
    }
}
