package utility;

import blueprint.Cart;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.text.NumberFormat;

public class Utility {

    public static void cartsTableProperty(TableView<Cart> cart, TableColumn<Cart, String> name,
                                    TableColumn<Cart, Integer> quantity, TableColumn<Cart, Double> price,
                                          ObservableList<Cart> item) {
        name.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        cart.setItems(item);
    }

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
            totalItem.setText(String.valueOf((Integer.parseInt(totalItem.getText()) + c.getItemQuantity())));

            if (sumPrice.getText().equals("0")) {
                sumPrice.setText(nf.format(c.getItemPrice()));
                totalPrice.setText(nf.format(c.getItemPrice()));
            }
            else {
                price += (c.getItemPrice());
                if (price > 100) {
                    double dPercent = .05;
                    if (price >= 500) {
                        dPercent = .10;
                    }
                    discountPer.setText(dPercent + " %");
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
     * Product original price. It it require to get right price each time when user
     * change item quantity
     * @return
     * Will return the product original price
     */
    public static double roundPrice (double price){
        return Math.round(price * 100.0) / 100.0;
    }

    /**
     * ImageView Method take in image url, width and height, get the right image,
     * resize it width and height and return the image
     * @param imageName
     * Product image name
     * @param imageWidth
     * Define the width of the image
     * @param imageHeight
     * Define the height of the image
     * @return
     * Will return the product image
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
}
