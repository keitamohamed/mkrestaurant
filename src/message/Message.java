package message;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Message {

    public static void successful(String message) {
        Notifications.create()
                .darkStyle()
                .text(message)
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(20))
                .show();
    }
}
