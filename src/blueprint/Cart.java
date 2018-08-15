package blueprint;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cart {
    private SimpleStringProperty productID;
    private SimpleStringProperty  name;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;

    public Cart(String id, String n, int q, double p) {
        this.productID = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(n);
        this.quantity = new SimpleIntegerProperty(q);
        this.price = new SimpleDoubleProperty(p);
    }

    @Override
    public String toString() {
        return "Product ID: " + productID + " Name " + name +
                " Quantity " + quantity + " Price " + price;
    }

    public String getProductID() {
        return productID.get();
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

}
