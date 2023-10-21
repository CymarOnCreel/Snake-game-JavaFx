package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.dao.PlayerDao;
import application.dto.Player;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HighScoresWindowController implements Initializable {

	@FXML
	private ScrollPane highScores;
	@FXML
	private TableView<Player> highScoresTable;
	@FXML
	private TableColumn<Player, Integer> position;
	@FXML
	private TableColumn<Player, String> playerName;
	@FXML
	private TableColumn<Player, Integer> playerHighScore;
	public Stage stage;

	ObservableList<Player> list = FXCollections.observableArrayList();
	PlayerDao playerDao = new PlayerDao();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = playerDao.getAll();
		position.setCellValueFactory(new Callback<CellDataFeatures<Player, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Player, Integer> p) {
				return new ReadOnlyObjectWrapper<>(highScoresTable.getItems().indexOf(p.getValue()) + 1);
			}
		});

		position.setCellFactory(new Callback<TableColumn<Player, Integer>, TableCell<Player, Integer>>() {
			@Override
			public TableCell<Player, Integer> call(TableColumn<Player, Integer> param) {
				return new TableCell<Player, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (!empty) {
							setText(item.toString());

						} else {
							setText("");
						}
					}
				};
			}
		});
		position.setSortable(false);
		playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
		playerHighScore.setCellValueFactory(new PropertyValueFactory<>("playerScore"));
		playerHighScore.setComparator((score1, score2) -> Integer.compare(score1, score2));
		highScoresTable.setItems(list);
		highScoresTable.getSortOrder().add(playerHighScore);
		highScoresTable.sort();

	}

}
