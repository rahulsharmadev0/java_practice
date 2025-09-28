import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;

// Simple CRUD Operations
public class HttpClientDemo {

    public static void main(String[] arg) {
        System.out.println("START");
        // getRequestDemo();
        // for async run
        // CompletableFuture.runAsync(HttpClientDemo::getRequestDemo);

        // postRequest();

        // putRequest();

        patchRequest();

        System.out.println("END");

    }

    static void getRequestDemo() {
        try (HttpClient client = HttpClient.newHttpClient();) {
            HttpRequest request = HttpRequest.newBuilder(
                    URI.create("https://api.restful-api.dev/objects"))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Accept", "application/json")
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    static void postRequest() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            var body = """
                                {
                       "name": "Apple MacBook Pro 16",
                       "data": {
                          "year": 2019,
                          "price": 1849.99,
                          "CPU model": "Intel Core i9",
                          "Hard disk size": "1 TB"
                       }
                    }""";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.restful-api.dev/objects"))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(body)).build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.body());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void putRequest() {

        try (HttpClient client = HttpClient.newHttpClient()) {

            var body = """
                                        {
                       "name": "Apple MacBook Pro 16",
                       "data": {
                          "year": 2019,
                          "price": 2049.99,
                          "CPU model": "Intel Core i9",
                          "Hard disk size": "1 TB",
                          "color": "silver"
                       }
                    }""";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.restful-api.dev/objects/13"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body)).build();

            try {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.version());
                System.out.println(response.body());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void patchRequest() {

        try (HttpClient client = HttpClient.newHttpClient()) {

            var body = """
                {
                    "name": "Apple MacBook Pro 21"
                }""";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.restful-api.dev/objects/ff8081819782e69e01997d41f90626a4"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .method("PATCH", BodyPublishers.ofString(body)).build();

            try {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.version());
                System.out.println(response.body());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
