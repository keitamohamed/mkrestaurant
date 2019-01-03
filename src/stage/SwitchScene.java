package stage;

import blueprint.Cart;
import controller.Account;
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

    private static void switchScene(String className, String userID, String userOrClassType, Button userFirstName) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.fxmlLocation(className, userOrClassType)));
            sendUserID(userID, userOrClassType, userFirstName);
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle(stageTitle(className, userOrClassType));
            stage.setResizable(false);
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource(Location.cssLocation(className, userOrClassType)).toExternalForm());
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource("../style/Message.css").toExternalForm());
            stage.show();
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    public static void switchScene(String typeOfAccount, String userID) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.fxmlLocation(typeOfAccount)));
            sendUserID(userID, typeOfAccount, new Button());
            Stage stage = new Stage();
            stageProperty(anchorPane, typeOfAccount, stage);
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    public static void switchScene(ObservableList<Cart> order, String className, String userID) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.fxmlLocation(className)));
            orderList(order, userID);
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
        else if (userType.equals("Account")) {
            Account.getUserInfo(userID);
            return;
        }
        Main.getUserID(userID, setUserFirstName);
    }

    /**
     * orderList method will be pass/call when the customer is ready
     * to place an order. It will be pass to the Checkout controller
     * during the switching scene.
     * @param carts
     * The all items added to the cart
     * @param userID
     * Customer id. If the customer is null, the customer will have a
     * choice to either login or register when they get to the Checkout page
     */
    private static void orderList(ObservableList<Cart> carts, String userID){
        Checkout.getOrderList(carts, userID);
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
     */
    @FXML
    public static void switchStage(String className, String userName, String userType, Button button) {
        if (userType != null) {
            SwitchScene.switchScene(className, userName, userType, button);
        }
    }

    /**
     * @param event
     * closeStage Method: is call when the user is switching between
     * stages.
     */
    @FXML
    public static void closeStage(Event event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
}
