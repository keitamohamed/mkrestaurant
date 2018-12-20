package stage;

import blueprint.Cart;
import controller.Admin;
import controller.Checkout;
import controller.Main;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchScene {

    private static String title;

    public static void switchScene(String className, String userID, boolean admin) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(getFXML(className, admin)));
            sendUserID(userID, admin);
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle(title);
            stage.setResizable(false);
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource(getStyleSheet(className, admin)).toExternalForm());
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource("../style/Message.css").toExternalForm());
            stage.show();
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    public static void switchScene(ObservableList<Cart> order, String className, String loginStatic) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(getFXML(className)));
            orderList(order, loginStatic);
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle(title);
            stage.setResizable(false);
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource(getStyleSheet(className)).toExternalForm());
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource("../style/Message.css").toExternalForm());
            stage.show();
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    @FXML
    public static void logInAndLogOut(Event e) {
        try {
            ((Node)e.getSource()).getScene().getWindow().hide();
            Parent main = FXMLLoader.load(SwitchScene.class.getResource("fxml/MainIndex.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(main));
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource("../style/Main.css").toExternalForm());
            stage.show();
        }catch (IOException | NullPointerException io) {
            System.out.println(io.getMessage());

        }
    }

    private static String getFXML(String className, boolean admin) {
        if (className.equals("Main") && !admin) {
            title = "Login Index";
            return "/fxml/LoginIndex.fxml";
        }
        else if ((className.equals("Login") || className.equals("Admin")) && !admin) {
            title = "Main Index";
            return "/fxml/MainIndex.fxml";
        }

        title = "Employee Index";
        return "/fxml/AdminIndex.fxml";
    }

    private static String getFXML(String className) {
        if (className.equals("Main")) {
            title = "Checkout Index";
            return "/fxml/CheckoutIndex.fxml";
        }
        title = "Main Index";
        return "/fxml/MainIndex.fxml";
    }

    private static String getStyleSheet(String className, boolean admin) {
        if (className.equals("Main"))
            return "../style/Login.css";
        else if ((className.equals("Login") || className.equals("Admin")) && !admin)
            return "../style/Main.css";
        return "../style/Admin.css";
    }

    private static String getStyleSheet(String className) {
        if (className.equals("Main"))
            return "../style/Checkout.css";
        return "../style/Main.css";
    }

    private static void sendUserID(String userID, boolean admin) {
        if (admin) {
            Admin.getUserID(userID);
            return;
        }
        Main.getUserID(userID);
    }

    private static void orderList(ObservableList<Cart> carts, String loginStatic){

        Checkout.getOrderList(carts, loginStatic);
    }


}
