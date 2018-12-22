package stage;

import blueprint.Cart;
import controller.Employee;
import controller.Checkout;
import controller.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.Location;

import java.io.IOException;

public class SwitchScene {

    public static void switchScene(String className, String userID, String userType, Button userFirstName) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.fxmlLocation(className, userType)));
            sendUserID(userID, userType, userFirstName);
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle(stageTitle(className, userType));
            stage.setResizable(false);
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource(getStyleSheet(className, userType)).toExternalForm());
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
            stage.setScene(new Scene(anchorPane));
            stage.setTitle(subStageTitle(className));
            stage.setResizable(false);
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource(getStyleSheet(className)).toExternalForm());
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource("../style/Message.css").toExternalForm());
            stage.show();
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    private static String getStyleSheet(String className, String userType) {
        if (className.equals("Main") && !userType.equals("Employee"))
            return "../style/Login.css";
        else if ((className.equals("Login") || className.equals("Employee")
                || className.equals("Checkout")) && !userType.equals("Employee"))
            return "../style/Main.css";
        return "../style/Admin.css";
    }

    private static String getStyleSheet(String className) {
        if (className.equals("Main"))
            return "../style/Checkout.css";
        return "../style/Main.css";
    }

    private static String stageTitle(String className, String userType){
        if (className.equals("Main") && !userType.equals("Employee")) {
            return "Login";
        }
        else if ((className.equals("Login") || className.equals("Employee")
                || className.equals("Checkout")) && !userType.equals("Employee")) {
            return "Main";
        }
        return "Employee";
    }

    private static String subStageTitle(String className){
        if (className.equals("Main")) {
            return "Checkout";
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


}
