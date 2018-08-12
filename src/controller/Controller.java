package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;


public class Controller {

    @FXML
    private Pane topPane, leftPane;
    @FXML
    private ListView listView, rightPane;
    @FXML
    private Label labelCategory, rAddress;

    @FXML
    public void initialize() {
        rAddress.setText("3420 Eastway Ave NW\n\tRoanoke VA");

    }
}
