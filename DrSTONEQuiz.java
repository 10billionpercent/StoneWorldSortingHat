package lol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Arrays;

class Question {
    String question;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String optionE;
}
public class DrSTONEQuiz extends Application {

    private List<Question> questions;
    public List<Character> characters;
    private int currentIndex = 0;
    private int score = 0;

    private Label questionLabel;
    private Button optionA, optionB, optionC, optionD, optionE;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage; 
        // Load questions
    	loadQuestions();
        if (!loadQuestions()) {
            System.out.println("Failed to load questions.");
            return;
        }

        // Title screen
        Label title = new Label("The Petrification Personality Test");
        Label subtitle = new Label("It's been 3700 years.\nYou wake up.\nWho are you now?");

        Image image = new Image("file:resources/images/Medusa.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        Button startButton = new Button("Start");
        VBox startLayout = new VBox(30, title, subtitle, imageView, startButton);
        startLayout.setAlignment(Pos.CENTER);
        Scene startScene = new Scene(startLayout, 440, 700);
        startButton.setPrefWidth(300);
        startButton.setPrefHeight(50);
        // Quiz layout
        questionLabel = new Label();
        optionA = new Button();
        optionB = new Button();
        optionC = new Button();
        optionD = new Button();
        optionE = new Button();
        optionA.setPrefWidth(300);
        optionA.setPrefHeight(50);
        optionB.setPrefWidth(300);
        optionB.setPrefHeight(50);
        optionC.setPrefWidth(300);
        optionC.setPrefHeight(50);
        optionD.setPrefWidth(300);
        optionD.setPrefHeight(50);
        optionE.setPrefWidth(300);
        optionE.setPrefHeight(50);


        // Option button click handling
        optionA.setOnAction(e -> handleOptionClick(-10));
        optionB.setOnAction(e -> handleOptionClick(-5));
        optionC.setOnAction(e -> handleOptionClick(0));
        optionD.setOnAction(e -> handleOptionClick(5));
        optionE.setOnAction(e -> handleOptionClick(10));

        VBox quizLayout = new VBox(20, questionLabel, optionA, optionB, optionC, optionD, optionE);
        quizLayout.setAlignment(Pos.CENTER);
        Scene quizScene = new Scene(quizLayout, 440, 700);

        // Start button action
        startButton.setOnAction(e -> {
            currentIndex = 0;
            score = 0;
            showQuestion();
            primaryStage.setScene(quizScene);
        });

        // Show start scene
        primaryStage.setTitle("Stone World Sorting Hat");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private boolean loadQuestions() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\shreya v\\eclipse-workspace\\lol\\resources\\questions.json.txt")));
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Question>>() {}.getType();
            questions = gson.fromJson(content, listType);
            return questions != null && !questions.isEmpty();
        } catch (IOException e) {
            System.out.println("Error loading questions: " + e.getMessage());
            return false;
        }
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            showResult();
            return;
        }
        Question q = questions.get(currentIndex);
        questionLabel.setText(q.question);
        optionA.setText(q.optionA);
        optionB.setText(q.optionB);
        optionC.setText(q.optionC);
        optionD.setText(q.optionD);
        optionE.setText(q.optionE);
        if (currentIndex==2) {
        	Random randomNumbers = new Random();
        	int a = randomNumbers.nextInt(10) + 1;
            int b = randomNumbers.nextInt(9) + 1;
            int c = randomNumbers.nextInt(10) + 1;
            int d = randomNumbers.nextInt(10) + 1;
            int e = randomNumbers.nextInt(10) + 1;
            String expression = a + "/" + b + "+" + c + "*" + d + "-" + e;
            double correctAns = a / (double)b + c * d - e;
            double opA = correctAns - 10;
            double opB = a / (double)(b + c) * (d - e);
            double opC = correctAns;
            double opD = (a / (double)b) + c * (d - e);
            double opE = randomNumbers.nextInt(20) + 1;
            questionLabel.setText(q.question+"\n"+expression);
            optionA.setText(String.valueOf(opA));
            optionB.setText(String.valueOf(opB));
            optionC.setText(String.valueOf(opC));
            optionD.setText(String.valueOf(opD));
            optionE.setText(String.valueOf(opE));
            

        }
    }

    private void handleOptionClick(int scoreChange) {
        score += scoreChange;
        currentIndex++;
        showQuestion();
    }

    private void showResult() {
        int[] lowerBounds = {-100, -80, -60, -40, -20, 0, 21, 41, 61, 81};
        int[] upperBounds = {-81, -61, -41, -21, 0, 20, 40, 60, 80, 100};
        String range = findRange(score, lowerBounds, upperBounds);
        
        // Load character data
        try {
            String content2 = new String(Files.readAllBytes(Paths.get("C:\\Users\\shreya v\\eclipse-workspace\\lol\\resources\\drstone.json.txt")));
            Gson gson2 = new Gson();
            Type listType2 = new TypeToken<List<Character>>() {}.getType();
            
            characters = gson2.fromJson(content2, listType2);
        } catch (IOException e) {
            System.out.println("Error loading character " + e.getMessage());
        }

        // Find the matching character based on the score
        Character finalCharacter = findCharacter(score);
        if (finalCharacter == null) {
            System.out.println("No matching character found for the score.");
            return;
        }

        // Create UI for result screen
        Label YouGot = new Label("You Got");
        Label name = new Label(finalCharacter.characterName);
        Label description = new Label(finalCharacter.description);
        
        // Create an ImageView for the character's image
        Image image = new Image(finalCharacter.image);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        // Add all elements to the VBox
        VBox resultLayout = new VBox(30, YouGot, name, imageView, description);
        resultLayout.setAlignment(Pos.CENTER);
        
        // Create the result scene
        Scene resultScene = new Scene(resultLayout, 440, 700);

        // Set the scene for the primary stage
        primaryStage.setTitle("Your Result");
        primaryStage.setScene(resultScene);
        primaryStage.show();
    }

    public static String findRange(int score, int[] lowerBounds, int[] upperBounds) {
        for (int i = 0; i < lowerBounds.length; i++) {
            if (score >= lowerBounds[i] && score <= upperBounds[i]) {
                return lowerBounds[i] + " to " + upperBounds[i];  // Range as a string
            }
        }
        return "Score is out of range";  // In case the score doesn't fall within any range
    }
    public Character findCharacter(int score) {
        for (Character character : characters) {
            if (score >= character.lowerBound&& score <= character.upperBound) {
                return character;  // Return the character that matches the score range
            }
        }
        return null;  // If no character matches, return null
    }

    public static void main(String[] args) {
        launch(args);
    }
}
