package stage;

import controller.Admin;
import controller.Main;
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
            stage.show();
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

    @FXML
    public static void logOut(Event e) {
        try {
            ((Node)e.getSource()).getScene().getWindow().hide();
            Parent main = FXMLLoader.load(SwitchScene.class.getResource("../fxml/Main.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(main));
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource("../style/Main.css").toExternalForm());
            stage.show();
        }catch (IOException io) {
            System.out.println("Io-Exception occur: " + io.getMessage());

        }catch (NullPointerException nullPoint) {
            System.out.println("Null-Point-Exception occur: " + nullPoint.getMessage());
        }
    }

    private static String getFXML(String name, boolean admin) {
        if (name.equals("Main") && !admin) {
            title = "Login";
            return "../fxml/Login.fxml";
        }
        else if ((name.equals("Login") || name.equals("Admin")) && !admin) {
            title = "Main";
            return "../fxml/Main.fxml";
        }
        title = "Employee";
        return "../fxml/Admin.fxml";
    }

    private static String getStyleSheet(String name, boolean admin) {
        if (name.equals("Main"))
            return "../style/Login.css";
        else if ((name.equals("Login") || name.equals("Admin")) && !admin)
            return "../style/Main.css";
        return "../style/Admin.css";
    }

    private static void sendUserID(String userID, boolean admin) {
        if (admin) {
            Admin.receiveUserID(userID);
            return;
        }
        Main.receiveUserID(userID);
    }
}
