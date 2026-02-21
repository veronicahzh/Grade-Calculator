package network;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class UbcGradesClient {

    private final HttpClient client;
    private final String baseUrl;

    public UbcGradesClient(String baseUrl) {
        this.baseUrl = stripTrailingSlash(baseUrl);
        client = HttpClient.newBuilder().build();
    }

    public JSONObject getJson(String path) throws IOException {
        String url = baseUrl + ensureLeadingSlash(path);

        HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) throw new IOException();

        return new JSONObject(res.body());
    }

    private static String ensureLeadingSlash(String s) {
        return s.startsWith("/") ? s : ("/" + s);
    }

    private static String stripTrailingSlash(String s) {
        return s.endsWith("/") ? s.substring(0, s.length() - 1) : s;
    }

}
