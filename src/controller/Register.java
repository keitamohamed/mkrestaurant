package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import message.Message;
import sqlscript.SQLPrepareStatement;

import java.util.Random;

public class Register {

    @FXML
    private TextField firstName, lastName, userName, address;
    @FXML
    private TextField city, state, zipCode, password, confirmPassword;
    @FXML
    private Button clearFields, register;
    @FXML
    private Label fieldsRequire;

    private SQLPrepareStatement statement = new SQLPrepareStatement();

    @FXML
    private void initialize() {
        fieldsRequire.setVisible(false);
        register.setOnAction(this::sendUserRegistration);
        clearFields.setOnAction(e -> clearFields());
    }

    @FXML
    private void sendUserRegistration(Event event) {
        int generateUserID = generateUserID();
        if (signUpNotFillOut()) {
            fieldsRequire.setText("All fields are require");
            fieldsRequire.setVisible(true);
            return;
        }

        if (!signUpNotFillOut()) {
            if (!(password.getText().equals(confirmPassword.getText()))) {
                fieldsRequire.setText("Password does not match");
                fieldsRequire.setVisible(true);
                return;
            }

        }

        if (!signUpNotFillOut() && (password.getText().equals(confirmPassword.getText()))){
            if (statement.setUserLogin(generateUserID, firstName.getText().trim(), lastName.getText().trim(),
                    userName.getText().trim(), password.getText().trim(), "Customer")) {
                if (statement.setUserAddressInfo(generateUserID, address.getText().trim(), city.getText().trim(),
                        state.getText().trim(), zipCode.getText().trim())) {
                    Message.successful(("Your Account Have Been Created " +
                            "Successfully.\nYour User ID is: " + generateUserID + ", Username is " +
                            "" + userName.getText() + "\nand your password is " + password.getText() + ". " +
                            "You can close\nthe Registration window and login."), 1);
                }
            }
            clearFields();
        }
    }

    @FXML
    private boolean signUpNotFillOut() {
        return firstName.getText().isEmpty() || lastName.getText().isEmpty() ||
                address.getText().isEmpty() || city.getText().isEmpty() ||
                state.getText().isEmpty() || zipCode.getText().isEmpty() ||
                userName.getText().isEmpty() || password.getText().isEmpty();
    }

    @FXML
    private void clearFields() {
        firstName.clear(); lastName.clear();
        address.clear(); city.clear();
        state.clear(); zipCode.clear();
        userName.clear(); password.clear();
        confirmPassword.clear();
    }

    @FXML
    private int generateUserID() {
        Random random = new Random();
        return (random.nextInt(900000) + 900000);
    }

    @FXML
    private void closeWindow(Event event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
}
