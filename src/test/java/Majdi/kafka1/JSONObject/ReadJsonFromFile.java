package Majdi.kafka1.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJsonFromFile {
    public static void main(String[] args) {
        // Path to the JSON file
        String fileName = "/Users/macbook/Downloads/2/favorite_music.json";

        try {
            // Read the content from the file
            String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
            JSONObject favoriteMusic = new JSONObject(jsonContent);

            // Extract values from the JSON object and print them
            if (favoriteMusic.has("artist")) {
                System.out.println("Artist: " + favoriteMusic.getString("artist"));
            } else {
                System.out.println("Artist key not found in JSON.");
            }

            if (favoriteMusic.has("song")) {
                System.out.println("Låt: " + favoriteMusic.getString("song"));
            } else {
                System.out.println("Song key not found in JSON.");
            }

            if (favoriteMusic.has("genre")) {
                System.out.println("Genre: " + favoriteMusic.getString("genre"));
            } else {
                System.out.println("Genre key not found in JSON.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error parsing the JSON content: " + e.getMessage());
        }
    }
}
