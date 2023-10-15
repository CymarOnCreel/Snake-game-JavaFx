package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainWindowController implements Initializable{

	@FXML private Button btnPlayGame;
	@FXML private Button btnHighScores;
	@FXML private Button btnCredits;
	@FXML private Button btnExit;
	
	@FXML private void playGame(Event event) {
		//TO-DO change to play view
		System.out.println("Test play button");
	}
	@FXML private void showHighScores(Event event) {
		//TO-DO change showHighScores view
		System.out.println("Test highScores button");
	}
	@FXML private void showCredits(Event event) {
		//TO-DO change showHighScores view
		System.out.println("Test credits button");
	}
	@FXML private void exit(Event event) {
		//TO-DO change showHighScores view
		System.out.println("Test exit button");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
