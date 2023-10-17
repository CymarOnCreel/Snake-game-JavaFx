package application.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import application.dto.SnakePiece;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameWindowController implements Initializable {
	@FXML
	private SplitPane fullGameWindow;

	@FXML
	private GridPane gameWindow;
	@FXML
	Pane gameTextsWindow;
	final double gridHeight = 800;
	final double gridWidth = 800;
	final double paneHeightForTexts = 80;
	final int gameSpeed = 500;
	final int columnCount = (int) gridWidth / 20;
	final int rowCount = (int) gridHeight / 20;
	private int xDirection = 0;
	private int yDirection = -1;
	private int xSnakeStartingPosition = 10;
	private int ySnakeStartingPosition = 10;
	private int snakeSize = 7;
	private LinkedList<SnakePiece> snakeBody;

	public void createTheGridPaneWithColumnsAndRows(GridPane root) {
		for (int i = 0; i < rowCount; i++) {
			RowConstraints row = new RowConstraints();
			row.setPrefHeight(20.0);
			root.getRowConstraints().add(row);
		}
		for (int i = 0; i < columnCount; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setPrefWidth(20.0);
			root.getColumnConstraints().add(col);
		}
	}

	public void fillGridPaneWithBlackRectangles(GridPane root, int rowCount, int columnCount) {
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				Rectangle rect = new Rectangle(20, 20);
				rect.setFill(Color.BLACK);
				root.add(rect, i, j);
			}
		}
	}

	public Rectangle creatingSnakeBodyPiece() {
		Rectangle rect = new Rectangle(20, 20);
		rect.setFill(Color.GREEN);
		return rect;
	}

	public Rectangle eraseSnakeBodyPiece() {
		Rectangle rect = new Rectangle(20, 20);
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

	public void moveSnake(GridPane root) {
		int headX = snakeBody.getFirst().getxPosition();
		int headY = snakeBody.getFirst().getyPosition();
		int newHeadX = headX + xDirection;
		int newHeadY = headY + yDirection;
		SnakePiece head = new SnakePiece(newHeadX, newHeadY);
		snakeBody.addFirst(head);

		Platform.runLater(() -> {

			root.add(creatingSnakeBodyPiece(), newHeadX, newHeadY);
			SnakePiece tail = snakeBody.removeLast();
			Rectangle erasePart = eraseSnakeBodyPiece();
			root.add(erasePart, tail.getxPosition(), tail.getyPosition());
		});
	}

	public void setGameWindowUp() {
		fullGameWindow.setPrefSize(gridWidth, gridHeight + paneHeightForTexts);
		gameTextsWindow.setPrefSize(gridWidth, paneHeightForTexts);
		gameWindow.setPrefSize(gridWidth, gridHeight);
		createTheGridPaneWithColumnsAndRows(gameWindow);
		fillGridPaneWithBlackRectangles(gameWindow, rowCount, columnCount);
	}

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setGameWindowUp();

		snakeBody = createSnakeBodyList(xSnakeStartingPosition, ySnakeStartingPosition, snakeSize);
		creatingSnakeBody(gameWindow, snakeBody, snakeSize);

		gameWindow.setFocusTraversable(true);
		gameWindow.setOnKeyPressed(event -> {
			handleKeyPress(event.getCode());
		});

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed), event -> {
			moveSnake(gameWindow);
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}
}