package utility;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utility {

    public static double roundPrice (double price){
        return Math.round(price * 100.0) / 100.0;
    }

    public static ImageView getImageProduct(String imageName, int imageWidth, int imageHeight) {
        Image image;
        ImageView view;
        try {
            image = new Image(Utility.class.getResourceAsStream("/image/pImage/" + imageName + ".png"));
            view = new ImageView(image);
            view.setFitWidth(imageWidth);
            view.setFitHeight(imageHeight);
        } catch (NullPointerException e) {
            image = new Image(Utility.class.getResourceAsStream("/image/pImage/" + imageName + ".jpg"));
            view = new ImageView(image);
            view.setFitWidth(imageWidth);
            view.setFitHeight(imageHeight);
        }
        return view;
    }
}
