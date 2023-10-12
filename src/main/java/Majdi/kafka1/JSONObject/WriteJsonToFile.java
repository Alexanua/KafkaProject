package Majdi.kafka1.JSONObject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteJsonToFile {
    public static void main(String[] args) {
        String filePath = "/Users/macbook/Downloads/2/favorite_music.json";

        JSONObject favoriteMusic = new JSONObject();
        favoriteMusic.put("Majdi", "habibi");
        favoriteMusic.put("habibi", "waw");
        favoriteMusic.put("oka", "salsa");

        Path path = Path.of(filePath);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(favoriteMusic.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("JSON-data har skrivits till filen: " + filePath);
    }



}
