package message;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Message {

    public static void successful(String title, String message) {
        ImageView view = new ImageView(Message.class.getResource("/image/icon/Ok.png").toExternalForm());
        Notifications.create()
                .darkStyle()
                .title(title)
                .text(message)
                .position(Pos.CENTER)
                .graphic(view)
                .hideAfter(Duration.seconds(5))
                .show();
    }
}
