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
import javafx.scene.control.TextField;
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
    @FXML private Tab editAccount, editAddress;
    @FXML private Label accountID, firstName, lastName, address, instruction;
    @FXML private Label city, state, zipCode, userName, password, idLabel;
    @FXML private TextField updateLastName, updateFirstName, updateZipCode;
    @FXML private TextField updateAddress, updateCity, updateState;
    @FXML private TextField updateUsername, updatePassword, updateConform;


    private ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        root.setOnMouseEntered(e -> {
            if (userID != null){
                statement.getUserInfo(userID, users);
                instruction.setText(instructionMessage());
            }
            displaceUserInfo();
            displaceUpdateInfo();
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getText().equals("Edit User Account")) {
                //displaceUpdateInfo();
            }
        });
    }

    @FXML
    private void updateUserInfo () {

    }

    @FXML private void displaceUpdateInfo() {
        for (User e : users) {
            idLabel.setText("User ID: " + e.getUserID());
            updateFirstName.setText(e.getFirstName());
            updateLastName.setText(e.getLastName());
            updateAddress.setText(e.getAddress());
            updateCity.setText(e.getCity());
            updateState.setText(e.getState());
            updateZipCode.setText(String.valueOf(e.getZipCode()));
            updateUsername.setText(e.getUserName());
            updatePassword.setText(e.getPassword());
            updateConform.setText(e.getPassword());
        }
    }

    @FXML private void displaceUserInfo(){
        for (User user : users) {
            accountID.setText(String.valueOf(user.getUserID()));
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            address.setText(user.getAddress());
            city.setText(user.getCity());
            state.setText(user.getState());
            zipCode.setText(String.valueOf(user.getZipCode()));
            userName.setText(user.getUserName());
            password.setText(user.getPassword());
        }
    }

    private String instructionMessage(){
        return "Important: To Update Any Information, Please Erase Old Information " +
                "And Enter The New\nInformation, And Click The 'Update Submit' Button";
    }

    public static void getUserInfo(String id) {
        userID = id;
    }
}
