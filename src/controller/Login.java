package controller;

import javafx.fxml.FXML;
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
    private Button login, register;
    @FXML
    private Label incorrectLogin;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    @FXML
    private void initialize() {
        incorrectLogin.setVisible(false);

        login.setOnAction(e -> {
            String userType = statement.checkLoginInfo(userName, password.getText(), setUserFirstName);
            if (userType != null) {
                SwitchScene.closeStage(e);
                SwitchScene.switchStage(this.getClass().getSimpleName(), userName.getText(),
                        userType, setUserFirstName);
            }
            incorrectLogin.setVisible(true);
        });
        register.setOnAction(e -> getPopUpStage(register));
    }

    @FXML
    private void getPopUpStage(Button button) {
        SwitchScene.switchScene(button);
    }
}
