package lol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Read {

    public static void main(String[] args) {
        try {
            String path = "C:\\Users\\shreya v\\eclipse-workspace\\lol\\resources\\drstone.json.txt";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            if (content.isEmpty()) {
                System.out.println("The file is empty.");
                return;
            }

            Gson gson = new Gson();
            Type characterListType = new TypeToken<List<Character>>() {}.getType();
            List<Character> characters = gson.fromJson(content, characterListType);
            if (characters == null) {
                System.out.println("Failed to parse JSON or the format is incorrect.");
                return;
            }
            for (Character character : characters) {
                System.out.println(character.characterName);
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
class Character {
    String characterName;
    String description;
    int lowerBound;
    int upperBound;
    String image;
}
