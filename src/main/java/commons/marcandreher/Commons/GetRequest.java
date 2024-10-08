package commons.marcandreher.Commons;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import commons.marcandreher.Utils.Stopwatch;



/**
 * This class represents a GET request to a specified URL.
 */
public class GetRequest {

    public class DebugGETRequest {
        public int statusCode;
        public boolean failed;
        public long elapsedTime;
    }

    private String url;

    /**
     * Constructs a GetRequest object with the specified URL.
     * @param url the URL to send the GET request to
     */
    public GetRequest(String url) {
        this.url = url;
    }

    /**
     * Sends a GET request to the specified URL with the given user agent.
     * @param userAgent the user agent to include in the request header
     * @return the response body as a string if the request is successful
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the request is interrupted
     */
    public String send(String userAgent) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", userAgent)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            return response.body();
        } else {
            throw new IOException("Failed to retrieve data. HTTP error code: " + statusCode);
        }
    }

    public DebugGETRequest sendDebug(String userAgent) {
        DebugGETRequest debugGETRequest = new DebugGETRequest();
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", userAgent)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            debugGETRequest.statusCode = response.statusCode();
        } catch (Exception e) {
            debugGETRequest.failed = true;
            debugGETRequest.statusCode = 500;
        }
        stopwatch.stop();
        debugGETRequest.elapsedTime = stopwatch.getElapsedTime();

        return debugGETRequest;
    }

}
