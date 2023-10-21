package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindowController implements Initializable {

	@FXML
	private Button btnPlayGame;
	@FXML
	private Button btnHighScores;
	@FXML
	private Button btnCredits;
	@FXML
	private Button btnExit;
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	private void playGame(Event event) {

		GameWindowController gameWindowRootController = new GameWindowController();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/gameWindow.fxml"));
			SplitPane gameWindowRoot = (SplitPane) loader.load();
			gameWindowRootController = loader.getController();	
			Stage gameStage = new Stage();
			gameWindowRootController.stage = gameStage;
			Scene gameWindowScene = new Scene(gameWindowRoot);
			gameStage.setScene(gameWindowScene);
			gameStage.setTitle("Let's Play");
			gameStage.initModality(Modality.APPLICATION_MODAL);
			gameStage.initOwner(((Node) event.getSource()).getScene().getWindow());
			gameStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void showHighScores(Event event) {
		// TO-DO change showHighScores view
		System.out.println("Test highScores button");
	}

	@FXML
	private void showCredits(Event event) {
		// TO-DO change showHighScores view
		System.out.println("Test credits button");
	}

	@FXML
	private void exit(Event event) {
		showAlert("Exit Game", "Do You Really Wnat To Exit The Game :(");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(message);
		Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.setAlwaysOnTop(true);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			System.exit(0);
		} else {
			alert.close();
		}

	}

}
