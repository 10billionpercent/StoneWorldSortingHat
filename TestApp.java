package lol;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
public class TestApp extends Application{
    @Override
    public void start(Stage primaryStage) {
    	Label label=new Label("Stone World Sorting Hat");
    	Button optionA=new Button("Option A");
    	Button optionB=new Button("Option B");
    	Button optionC=new Button("Option C");
    	Button optionD=new Button("Option D");
    	Button optionE=new Button("Option E");
    	optionA.setOnAction(e->label.setText("SOSORU ZE KORE WA"));
    	VBox layout=new VBox(10);
    	layout.getChildren().addAll(label,optionA,optionB,optionC,optionD,optionE);
    	layout.setAlignment(Pos.CENTER);
    	Scene scene = new Scene(layout,300,300);
    	primaryStage.setScene(scene);
    	primaryStage.setTitle("Stone World Sorting Hat");
    	primaryStage.show();
    	}
	public static void main(String[] args) {
		launch(args);
	}

}
