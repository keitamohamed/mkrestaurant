package controller;

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
    private Button setUserFirstName = new Button();

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
        incorrectLogin.setVisible(false);

        login.setOnAction( e -> {
            try {
                String userType = statement.checkLoginInfo(userName, password.getText(), setUserFirstName);
                String className = this.getClass().getSimpleName();
                if (userType != null) {
                    ((Node)e.getSource()).getScene().getWindow().hide();
                    SwitchScene.switchScene(className, userName.getText(), userType, setUserFirstName);
                }
                incorrectLogin.setVisible(true);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
