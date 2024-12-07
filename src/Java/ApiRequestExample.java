import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ApiRequestExample {

    public static void main(String[] args) {
        try {
            String url1 = "https://fake-json-api.mock.beeceptor.com/users";
            String url2 = "https://dummy-json.mock.beeceptor.com/posts";

            String response1 = sendGetRequest(url1);
            String response2 = sendGetRequest(url2);

            System.out.println("Response for URL 1:");
            parseAndPrintJson(response1);

            System.out.println("\nResponse for URL 2:");
            parseAndPrintJson(response2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sendGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        System.out.println("Requesting URL: " + urlString);
        System.out.println("Response Code: " + connection.getResponseCode());
        System.out.println("Response Message: " + connection.getResponseMessage());

        Map<String, java.util.List<String>> headers = connection.getHeaderFields();
        System.out.println("Response Headers:");
        for (Map.Entry<String, java.util.List<String>> header : headers.entrySet()) {
            System.out.println(header.getKey() + ": " + String.join(", ", header.getValue()));
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static void parseAndPrintJson(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

            System.out.println("\nFormatted JSON Response:");
            System.out.println(formattedJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
