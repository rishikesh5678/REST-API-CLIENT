import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {
    public static void main(String[] args) {
        // Corrected API URL for Pune
        String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=18.5204&longitude=73.8567&current_weather=true";

        try {
            // Create a URL object from the API URL
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Set request method to GET

            // Get the response code
            int responseCode = connection.getResponseCode();

            // If the response code is 200 (HTTP_OK), read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                // Read the response line by line
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the response as a JSON object
                JSONObject jsonObj = new JSONObject(response.toString());
                JSONObject currentWeather = jsonObj.getJSONObject("current_weather");

                // Extract specific weather data
                double temperature = currentWeather.getDouble("temperature");
                double windSpeed = currentWeather.getDouble("windspeed");
                int windDirection = currentWeather.getInt("winddirection");
                int isDay = currentWeather.getInt("is_day");

                // Display the weather data
                System.out.println("Current Weather in Pune:");
                System.out.println("Temperature: " + temperature + "°C");
                System.out.println("Wind Speed: " + windSpeed + " km/h");
                System.out.println("Wind Direction: " + windDirection + "°");
                System.out.println("Is it Day? " + (isDay == 1 ? "Yes" : "No"));
            } else {
                System.out.println("Error: Unable to fetch weather data. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
