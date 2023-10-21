package application.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

import application.dao.PlayerDao;
import application.dto.Apple;
import application.dto.Player;
import application.dto.SnakePiece;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameWindowController implements Initializable {
	@FXML
	private SplitPane fullGameWindow;

	@FXML
	private GridPane gameWindow;
	@FXML
	Pane gameTextsWindow;
	@FXML
	Label lblHighScore;
	@FXML
	Label lblPlayerName;
	final double gridHeight = 600;
	final double gridWidth = 600;
	final double paneHeightForTexts = 80;
	final int gameSpeed = 150;
	final int boxSizeInGrid = 20;
	final int columnCount = (int) gridWidth / boxSizeInGrid;
	final int rowCount = (int) gridHeight / boxSizeInGrid;
	final int xSnakeStartingPosition = 10;
	final int ySnakeStartingPosition = 10;
	private int xDirection;
	private int yDirection;
	private int snakeSize;
	private LinkedList<SnakePiece> snakeBody;
	private Apple apple;
	private int score;
	private Timeline timeline;
	private boolean isGameRunning;
	public Player player;
	private String playerName;
	MediaPlayer mediaPlayer;
	private TextInputDialog playerNameTextInput;
	public Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		getPlayerName();
		initializeVariablesForGameStart();
		setGameWindowUp();
		music();
		playGame();

	}

	public void playGame() {
		snakeBody = createSnakeBodyList(xSnakeStartingPosition, ySnakeStartingPosition, snakeSize);
		creatingSnakeBody(gameWindow, snakeBody, snakeSize);
		createFoodAtRandomPosition();
		gameWindow.setFocusTraversable(true);
		gameWindow.setOnKeyPressed(event -> {
			handleKeyPress(event.getCode());
		});
		timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed), event -> {
			moveSnake(gameWindow);

			if (didSnakeEatApple(snakeBody, apple)) {
				updateAfterSnakeEatsApple();
			}
			if (didSnakeHitWall(snakeBody) || didSnakeHitHimself(snakeBody))
				handleCloseWindow();
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}

	private void getPlayerName() {
		playerNameTextInput = new TextInputDialog();
		playerNameTextInput.setTitle("Enter you name");
		playerNameTextInput.setHeaderText(null);
		playerNameTextInput.setContentText("Player Name: ");
		playerNameTextInput.showAndWait().ifPresent(name -> {
			playerName = name.isEmpty() ? "Player" : name;
			lblPlayerName.setText("Player: " + playerName);
		});
	}

	public void handleCloseWindow() {
		isGameRunning = false;
		player = new Player(playerName, score);
		PlayerDao playerDao = new PlayerDao();
		mediaPlayer.stop();
		Platform.runLater(() -> {
			playerDao.save(player);
			showAlertOnGameFinish("Játék Vége", "Kedves " + playerName + " összesen " + score + " pontot értél el");
			System.out.println(stage);
			
		});

		if (timeline != null) {
			timeline.stop();

		}
		if (stage != null) {
	        stage.close();  
	    }
	}

	public void music() {
		Platform.runLater(() -> {
			try {
				String path = "/application/util/snake_music.mp3";
				Media toPlay = new Media(getClass().getResource(path).toExternalForm());
				mediaPlayer = new MediaPlayer(toPlay);
				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
				mediaPlayer.play();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

// setting game logic methods
	public void updateAfterSnakeEatsApple() {
		relocateAppleOnGrid(snakeBody, apple);
		score = updateScore(score);
		changeHighScoreDisplay();
	}

	public boolean didSnakeHitWall(LinkedList<SnakePiece> snake) {
		boolean snakeHitWall = false;
		if (snake.getFirst().getyPosition() == -1 || snake.getFirst().getxPosition() == -1
				|| snake.getFirst().getxPosition() == columnCount || snake.getFirst().getyPosition() == rowCount)
			snakeHitWall = true;
		return snakeHitWall;
	}

	public boolean didSnakeHitHimself(LinkedList<SnakePiece> snake) {
		boolean didSnakeHithimself = false;
		int headX = snake.getFirst().getxPosition();
		int headY = snake.getFirst().getyPosition();
		int i;
		for (i = 1; i < snake.size(); i++) {
			if (snake.get(i).getxPosition() == headX && snake.get(i).getyPosition() == headY)
				didSnakeHithimself = true;
		}
		return didSnakeHithimself;
	}

	public void moveSnake(GridPane root) {
		int headX = snakeBody.getFirst().getxPosition();
		int headY = snakeBody.getFirst().getyPosition();
		int newHeadX = headX + xDirection;
		int newHeadY = headY + yDirection;
		SnakePiece head = new SnakePiece(newHeadX, newHeadY);
		snakeBody.addFirst(head);
		if (didSnakeEatApple(snakeBody, apple)) {
			Platform.runLater(() -> {
				if (isGameRunning) {
					root.add(creatingSnakeBodyPiece(), newHeadX, newHeadY);
				}
			});
		} else {
			Platform.runLater(() -> {
				if (isGameRunning) {
					root.add(creatingSnakeBodyPiece(), newHeadX, newHeadY);
					SnakePiece tail = snakeBody.removeLast();
					Rectangle erasePart = eraseSnakeBodyPiece();
					root.add(erasePart, tail.getxPosition(), tail.getyPosition());
				}
			});
		}
	}

	// Setting up methods for Game window
	public void setGameWindowUp() {
		fullGameWindow.setPrefSize(gridWidth, gridHeight + paneHeightForTexts);
		gameTextsWindow.setPrefSize(gridWidth, paneHeightForTexts);
		gameWindow.setPrefSize(gridWidth, gridHeight);
		createTheGridPaneWithColumnsAndRows(gameWindow);
		fillGridPaneWithBlackRectangles(gameWindow, rowCount, columnCount);
	}

	public void initializeVariablesForGameStart() {
		snakeSize = 7;
		score = 0;
		xDirection = 0;
		yDirection = -1;
		isGameRunning = true;
	}

	public void createTheGridPaneWithColumnsAndRows(GridPane root) {
		for (int i = 0; i < rowCount; i++) {
			RowConstraints row = new RowConstraints();
			row.setPrefHeight(boxSizeInGrid);
			root.getRowConstraints().add(row);
		}
		for (int i = 0; i < columnCount; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setPrefWidth(boxSizeInGrid);
			root.getColumnConstraints().add(col);
		}
	}

	public void fillGridPaneWithBlackRectangles(GridPane root, int rowCount, int columnCount) {
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				Rectangle rect = new Rectangle(boxSizeInGrid, boxSizeInGrid);
				rect.setFill(Color.BLACK);
				root.add(rect, i, j);
			}
		}
	}

	// Methods for Snake creating and movement
	public Rectangle creatingSnakeBodyPiece() {
		Rectangle rect = new Rectangle(boxSizeInGrid, boxSizeInGrid);
		rect.setFill(Color.GREEN);
		return rect;
	}

	public Rectangle eraseSnakeBodyPiece() {
		Rectangle rect = new Rectangle(boxSizeInGrid, boxSizeInGrid);
		rect.setFill(Color.BLACK);
		return rect;
	}

	public LinkedList<SnakePiece> createSnakeBodyList(int xPosition, int yPosition, int snakeSize) {
		LinkedList<SnakePiece> snakeBody = new LinkedList<SnakePiece>();
		for (int i = yPosition; i < (yPosition + snakeSize); i++) {
			SnakePiece snakePiece = new SnakePiece(xPosition, i);
			snakeBody.add(snakePiece);
		}
		return snakeBody;
	}

	public void creatingSnakeBody(GridPane root, LinkedList<SnakePiece> snake, int snakeSize) {
		for (SnakePiece snakePiece : snake) {
			Rectangle snakePieceShow = creatingSnakeBodyPiece();
			root.add(snakePieceShow, snakePiece.getxPosition(), snakePiece.getyPosition());
		}
	}

//	private void

	@FXML
	public void changeDirection(KeyEvent event) {
		handleKeyPress(event.getCode());
	}

	private void handleKeyPress(KeyCode code) {
		if (code == KeyCode.UP && yDirection != 1) {
			xDirection = 0;
			yDirection = -1;
		} else if (code == KeyCode.DOWN && yDirection != -1) {
			xDirection = 0;
			yDirection = 1;
		} else if (code == KeyCode.LEFT && xDirection != 1) {
			xDirection = -1;
			yDirection = 0;
		} else if (code == KeyCode.RIGHT && xDirection != -1) {
			xDirection = 1;
			yDirection = 0;
		}
	}

// creating apple for snake to eat
	public void createFoodAtRandomPosition() {
		Apple apple = createRandomPositionForApple(snakeBody);
		gameWindow.add(createAppleShape(), apple.getxPosition(), apple.getyPosition());
	}

	private Apple createRandomPositionForApple(LinkedList<SnakePiece> snake) {
		Random ranNum = new Random();
		int appleX, appleY;

		do {
			appleX = ranNum.nextInt(columnCount - 1) + 1;
			appleY = ranNum.nextInt(rowCount - 1) + 1;
		} while (isApllePositionOnSnake(snake, appleX, appleY));

		apple = new Apple(appleX, appleY);
		return apple;
	}

	private boolean isApllePositionOnSnake(LinkedList<SnakePiece> snake, int x, int y) {
		for (SnakePiece snakePiece : snake) {
			if (snakePiece.getxPosition() == x && snakePiece.getyPosition() == y)
				return true;
		}
		return false;
	}

	private Circle createAppleShape() {
		Circle appleShape = new Circle(boxSizeInGrid / 2, Color.RED);
		return appleShape;
	}

	private void relocateAppleOnGrid(LinkedList<SnakePiece> snake, Apple apple) {
		if (didSnakeEatApple(snake, apple)) {
			gameWindow.getChildren().remove(apple);
			createFoodAtRandomPosition();
		}
	}

	// set up methods for score
	private boolean didSnakeEatApple(LinkedList<SnakePiece> snake, Apple apple) {
		for (SnakePiece snakePiece : snake) {
			if (snakePiece.getxPosition() == apple.getxPosition() && snakePiece.getyPosition() == apple.getyPosition())
				return true;
		}
		return false;
	}

	private int updateScore(int score) {
		score += 100;
		return score;
	}

	public void changeHighScoreDisplay() {
		lblHighScore.setText("HighScore: " + score);
	}

	public void showAlertOnGameFinish(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(message);
		Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.setAlwaysOnTop(true);
		alert.show();
	}
}