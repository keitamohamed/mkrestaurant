package controller;

import dbconnection.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Login {

    @FXML
    private Button login;

    @FXML
    private void initialize() {
        login.setOnAction( e -> {
            try {
                setConnection();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private void setConnection() throws Throwable {
        DBConnection.getInstance();
    }

}
