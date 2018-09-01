package controller;

import javafx.fxml.FXML;

public class Admin {
    private static String userID;


    @FXML
    private void initialize() {

    }
    public static void receiveUserID(String id) {
        userID = id;
    }
}
