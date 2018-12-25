package stage;

import blueprint.Cart;
import controller.Employee;
import controller.Checkout;
import controller.Main;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.Location;

import java.io.IOException;

public class SwitchScene {

    private static void switchScene(String className, String userID, String userType, Button userFirstName) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.fxmlLocation(className, userType)));
            sendUserID(userID, userType, userFirstName);
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle(stageTitle(className, userType));
            stage.setResizable(false);
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource(Location.cssLocation(className, userType)).toExternalForm());
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource("../style/Message.css").toExternalForm());
            stage.show();
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    public static void switchScene(ObservableList<Cart> order, String className, String loginStatic, Button userFirstName) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.fxmlLocation(className)));
            orderList(order, loginStatic, userFirstName.getText());
            Stage stage = new Stage();
            stageProperty(anchorPane, className, stage);
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    public static void switchScene(Button button) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.fxmlLocation(button.getText().trim())));
            Stage stage = new Stage();
            stageProperty(anchorPane, button.getText().trim(), stage);
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    private static String stageTitle(String className, String userType){
        if (className.equals("Main") && !userType.equals("Employee")) {
            return "Login";
        }
        else if ((className.equals("Login") || className.equals("Employee")
                || className.equals("Checkout") || className.equals("Sign Out"))
                && !userType.equals("Employee")) {
            return "Main";
        }
        return "Employee";
    }

    private static String subStageTitle(String textValue){
        switch (textValue) {
            case "Main":
                return "Checkout";
            case "Sign Out":
                return "Main";
            case "Sign Up / Register":
            case "Sign Up":
                return "Register";
        }
        return "Main";
    }

    private static void sendUserID(String userID, String userType, Button setUserFirstName) {
        if (userType.equals("Employee")) {
            Employee.getUserID(userID, setUserFirstName);
            return;
        }
        Main.getUserID(userID, setUserFirstName);
    }

    private static void orderList(ObservableList<Cart> carts, String loginStatic, String userFirstName){
        Checkout.getOrderList(carts, loginStatic, userFirstName);
    }

    private static void stageProperty(AnchorPane anchorPane, String className, Stage stage){
        stage.setScene(new Scene(anchorPane));
        stage.setTitle(subStageTitle(className));
        stage.getScene().getStylesheets().add(SwitchScene.class.getResource(Location.cssLocation(className)).toExternalForm());
        stage.getScene().getStylesheets().add(SwitchScene.class.getResource("../style/Message.css").toExternalForm());
        stage.setResizable(false);
        stage.show();
    }

    /**
     * logInAndLogout Method is call to switch between stage only
     * if the login button text is equal to "Sign In" for the class.
     * And also the class name is pass into the switchStage Method
     * to deterrent which fxmal to get
     * @param event
     * Need it to close current class.
     */
    @FXML
    public static void switchStage(Event event, String className, String userName, String userType, Button button) {
        if (userType != null) {
            ((Node)event.getSource()).getScene().getWindow().hide();
            SwitchScene.switchScene(className, userName, userType, button);
        }
    }
}
