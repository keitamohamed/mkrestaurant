package blueprint;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cart {
    private SimpleStringProperty  name;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;

    public Cart(String n, int q, double p) {
        this.name = new SimpleStringProperty(n);
        this.quantity = new SimpleIntegerProperty(q);
        this.price = new SimpleDoubleProperty(p);
    }

    @Override
    public String toString() {
        return " Name " + name + " Quantity " + quantity + " Price " + price;
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
}
