package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

/*To-Do
 * 1. Create the file/folder system
 * 2. Create database for highscores
 * 3. Create player class
 * 4. create snake piece class
 * 5. create database connection class
 * 6. create main window
 * 6. create Game window
 * 7. create HighScore window
 * 8. create main window controller for different button events
 * 9. create Snake controller   
 * 
 * 
 * 
 */
