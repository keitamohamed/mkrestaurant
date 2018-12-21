package stage;

import blueprint.Cart;
import controller.Admin;
import controller.Checkout;
import controller.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.Location;

import java.io.IOException;

public class SwitchScene {

    private static String title;

    public static void switchScene(String className, String userID, boolean admin) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.location(className, admin)));
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
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(Location.location(className)));
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

    private static String getStyleSheet(String className, boolean admin) {
        if (className.equals("Main") && !admin)
            return "../style/Login.css";
        else if ((className.equals("Login") || className.equals("Admin")
                || className.equals("Checkout")) && !admin )
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
