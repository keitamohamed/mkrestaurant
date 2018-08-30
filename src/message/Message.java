package message;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Message {
    private static Alert al;

    public static void successful(String message, int second) {
        Notifications.create()
                .darkStyle()
                .text(message)
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(second))
                .show();
    }

    public static void loginFailed(String title, String message) {
        al = new Alert(Alert.AlertType.ERROR);
        al.setTitle(title);
        al.setHeaderText(null);
        al.setContentText(message);
        al.getDialogPane().getStylesheets().add(Message.class.getResource("../style/Message.css/").toExternalForm());
        al.showAndWait();
    }

    public static void connectionFailed(String title, String message) {
        Notifications.create()
                .title(title)
                .darkStyle()
                .text(message)
                .position(Pos.CENTER)
                .show();
    }
}
