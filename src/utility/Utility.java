package utility;

import blueprint.Cart;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.text.NumberFormat;
import java.util.concurrent.locks.Lock;

public class Utility {

    /**
     * Pass all price variable and carts observableList and calculate all price, discount
     * and total item, and assign them to their price
     * Reset sumPrice, totalPrice discountPrice, and discount percentage if
     * cars size is 0
     */
    public static void calculatePrice(ObservableList<Cart> carts, Label sumPrice, Label totalPrice,
                                       Label discountPer, Label discount, Label totalItem) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        double price = 0;
        if (!totalItem.getText().isEmpty()) {
            totalItem.setText("0");
        }
        for (Cart c : carts) {
            totalItem.setText(String.valueOf((Integer.parseInt(totalItem.getText()) + c.getQuantity())));

            if (sumPrice.getText().equals("0")) {
                sumPrice.setText(nf.format(c.getPrice()));
                totalPrice.setText(nf.format(c.getPrice()));
            }
            else {
                price += (c.getPrice());
                if (price > 100) {
                    double dPercent = .05;
                    if (price >= 500) {
                        dPercent = .10;
                    }
                    discountPer.setText(String.valueOf(dPercent) + " %");
                    discount.setText(nf.format(price * dPercent));

                    sumPrice.setText(nf.format(price));
                    totalPrice.setText(String.valueOf(nf.format(price - Double.parseDouble(discount.getText().substring(1)) )));

                } else {
                    sumPrice.setText(nf.format(price));
                    totalPrice.setText(nf.format(price));
                }
            }
        }

        if (carts.size() == 0) {
            sumPrice.setText("0");
            totalPrice.setText("0");
            discountPer.setText("0 %");
            discount.setText("0");
        }
    }

    /**
     * Pass getProducts price and return the price with only two decimal place
     * @param price
     * @return
     */
    public static double roundPrice (double price){
        return Math.round(price * 100.0) / 100.0;
    }

    /**
     * ImageView Method take in image url, width and height, get the right image,
     * resize it width and height and return the image
     * @param imageName
     * @param imageWidth
     * @param imageHeight
     * @return
     */
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

    public static void waitTime(String mom){
        synchronized (mom) {
            try {
                mom.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
