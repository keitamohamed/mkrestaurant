package stage;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchScene {

    public static void switchScene(String className, String title) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SwitchScene.class.getResource(getFXML(className)));
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle(title);
            stage.setResizable(false);
            stage.getScene().getStylesheets().add(SwitchScene.class.getResource(getStyleSheet(className)).toExternalForm());
            stage.show();
            stage.setOnCloseRequest(e -> stage.close());
        }catch (IOException io) {
            System.out.println("IO-Exception occur: " + io.getMessage());
        }
    }

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

    private static String getFXML(String name) {
        switch(name) {
            case "Main":
                return "../fxml/Login.fxml";
            case "Login":
                return "../fxml/Main.fxml";
        }
        return null;
    }

    private static String getStyleSheet(String name) {
        if (name.equals("Main"))
            return "../style/Login.css";
        else if (name.equals("Login"))
            return "../style/Main.css";
        return null;
    }
}
