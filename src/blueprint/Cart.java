package blueprint;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class Cart {
    private SimpleIntegerProperty productID;
    private SimpleStringProperty  name;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;
    private ImageView view;

    public Cart(String n, int q, double p) {
        this.name = new SimpleStringProperty(n);
        this.quantity = new SimpleIntegerProperty(q);
        this.price = new SimpleDoubleProperty(p);
    }

    public Cart(int pID, ImageView image, String n, int q, double p) {
        this.productID = new SimpleIntegerProperty(pID);
        this.view = image;
        this.name = new SimpleStringProperty(n);
        this.quantity = new SimpleIntegerProperty(q);
        this.price = new SimpleDoubleProperty(p);
    }

    @Override
    public String toString() {
        return " Name " + name + " Quantity " + quantity + " Price " + price;
    }

    public int getProductID() {
        return productID.get();
    }

    public ImageView getView() {
        return view;
    }

    public String getName() {
        return name.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrice() {
        return price.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
