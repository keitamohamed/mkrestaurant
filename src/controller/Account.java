package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class Account {
    private static String userID, setUserName;

    @FXML
    private AnchorPane root;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab account, editAccount, editAddress;

    @FXML
    private void initialize() {

        root.setOnMouseEntered(e -> {
            if (userID != null){
            }
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });
    }

    public static void getUserInfo(String id) {
        userID = id;
    }
}
