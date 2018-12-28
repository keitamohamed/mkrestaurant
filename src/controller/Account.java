package controller;

import blueprint.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sqlscript.SQLPrepareStatement;

public class Account {
    private static String userID, setUserName;
    private SQLPrepareStatement statement = new SQLPrepareStatement();

    @FXML
    private AnchorPane root;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab accountInfo, editAccount, editAddress;
    @FXML
    private Label accountID, firstName, lastName, address;
    @FXML
    private Label city, state, zipCode, userName, password;

    private ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        root.setOnMouseEntered(e -> {
            if (userID != null){
                statement.getUserInfo(userID, users);
            }
            displaceUserInfo();
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getText().equals("Edit Account")) {
                System.out.println();
            }
            else if (newValue.getText().equals("Edit Address")) {
                System.out.println();
            }
        });
    }

    private void displaceUserInfo(){
        users.forEach(e -> {
            accountID.setText(String.valueOf(e.getUserID()));
            firstName.setText(e.getFirstName());
            lastName.setText(e.getLastName());
            address.setText("Address: " + e.getAddress());
            city.setText("City: " + e.getCity());
            state.setText("State: " + e.getState());
            zipCode.setText("ZipCode: " + e.getZipCode());
            userName.setText("Username: " + e.getUserName());
            password.setText("Password: " + e.getPassword());
        });
    }

    public static void getUserInfo(String id) {
        userID = id;
    }
}
