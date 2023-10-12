package Majdi.kafka1.JSONObject;




import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFileHandler {

    public static void main(String[] args) {
        String filePath = "/Users/macbook/Downloads/2/favorite_music.json";

        writeToJsonFile(filePath);

        JSONObject jsonObject = readFromJsonFile(filePath);
        if (jsonObject != null) {
            System.out.println("Läst från filen: " + jsonObject);
        } else {
            System.out.println("Kunde inte läsa från filen.");
        }
    }

    private static void writeToJsonFile(String filePath) {
        JSONObject favoriteMusic = new JSONObject();
        favoriteMusic.put("Mervat", "habibi");
        favoriteMusic.put("dadi", "waw");
        favoriteMusic.put("oka", "salsa");

        Path path = Path.of(filePath);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(favoriteMusic.toJSONString());
            System.out.println("JSON-data har skrivits till filen: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject readFromJsonFile(String filePath) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = parser.parse(reader);
            return (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
