package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class UBCGradesService {

    private UBCGradesClient client;
    private HashMap<String, JSONObject> cache;

    public UBCGradesService(UBCGradesClient client) {
        this.client = client;
        cache = new HashMap<>();
    }

    private JSONObject fetchObject(String path) throws IOException, InterruptedException {
        if (cache.containsKey(path)) {
            return cache.get(path);
        }

        JSONObject jsonObj = client.getJson(path);
        cache.put(path, jsonObj);
        return jsonObj;
    }

    private JSONArray fetchArray(String path) {
        return null;
    }

    // public List<String> getSubjects(String campus, String yearSession) {
    //     String path = campus + yearSession;
    //     JSONObject jsonPath = fetch(path);
        
    // }

    private List<String> readStringList(JSONObject json) {
        ArrayList<String> average = new ArrayList<>();
        ArrayList<String> course = new ArrayList<>();
        
        if (json.optJSONArray("results") != null) {
            JSONArray arr = json.getJSONArray("results");

            // for ()
        }
    }
}