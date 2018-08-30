package controller;

import dbconnection.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sqlscript.SQLPrepareStatement;

public class Login {
//    private DBConnection dbConnection = new DBConnection();
    private SQLPrepareStatement statement = new SQLPrepareStatement();

    @FXML
    private Button login;
    @FXML
    private Label incorrectLogin;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    @FXML
    private void initialize() {

        login.setOnAction( e -> {
            try {
                if (statement.checkLogin(userName, password)) {
                    incorrectLogin.setText("Admin login: " + userName.getText());
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

//    private void setConnection() throws Throwable {
//        if (dbConnection.getConnection() == null) {
//            dbConnection = DBConnection.getInstance();
//        }
//    }

}
