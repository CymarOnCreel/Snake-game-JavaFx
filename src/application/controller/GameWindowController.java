package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameWindowController implements Initializable {
	 @FXML
	 private GridPane gameWindow;
	final int columnCount=20;
	final int rowCount=21;

	    public void createTheGridPaneWithColumnsAndRows(GridPane root) {
	    	for (int i = 0; i < rowCount; i++) {
				RowConstraints row=new RowConstraints();
				row.setPrefHeight(25.0);
				root.getRowConstraints().add(row);
			}
	    	for (int i = 0; i < columnCount; i++) {
				ColumnConstraints col=new ColumnConstraints();
				col.setPrefWidth(25.0);
				root.getColumnConstraints().add(col);
			}
	    }
	   		@Override
		public void initialize(URL location, ResourceBundle resources) {
	   	createTheGridPaneWithColumnsAndRows(gameWindow);
	  		     
		}
}
