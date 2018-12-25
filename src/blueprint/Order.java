package blueprint;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private SimpleIntegerProperty orderID;
    private SimpleIntegerProperty userID;
    private SimpleIntegerProperty productID;
    private SimpleStringProperty productName;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;

    public Order (int oID, int uID, int pID, String pName, int pQuantity, double pPrice) {
        this.orderID = new SimpleIntegerProperty(oID);
        this.userID = new SimpleIntegerProperty(uID);
        this.productID = new SimpleIntegerProperty(pID);
        this.productName = new SimpleStringProperty(pName);
        this.quantity = new SimpleIntegerProperty(pQuantity);
        this.price = new SimpleDoubleProperty(pPrice);
    }

    public int getOrderID() {
        return orderID.get();
    }

    public int getUserID() {
        return userID.get();
    }

    public int getProductID() {
        return productID.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrice() {
        return price.get();
    }

}
