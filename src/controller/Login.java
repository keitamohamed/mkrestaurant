package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sqlscript.SQLPrepareStatement;
import stage.SwitchScene;

public class Login {

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
                boolean admin = statement.checkLoginInfo(userName, password.getText());
                String className = this.getClass().getSimpleName();
                ((Node)e.getSource()).getScene().getWindow().hide();
                SwitchScene.switchScene(className, userName.getText(), admin);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private void logInAndLogOut(Event event) {
       ((Node)event.getSource()).getScene().getWindow().hide();
    }

//    private void setConnection() throws Throwable {
//        if (dbConnection.getConnection() == null) {
//            dbConnection = DBConnection.getInstance();
//        }
//    }

}
