package blueprint;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class Cart {
    private SimpleIntegerProperty itemID;
    private SimpleStringProperty itemName;
    private SimpleIntegerProperty itemQuantity;
    private SimpleDoubleProperty itemPrice;
    private ImageView itemImage;

    public Cart(String n, int q, double p) {
        this.itemName = new SimpleStringProperty(n);
        this.itemQuantity = new SimpleIntegerProperty(q);
        this.itemPrice = new SimpleDoubleProperty(p);
    }

    public Cart(int pID, ImageView image, String n, int q, double p) {
        this.itemID = new SimpleIntegerProperty(pID);
        this.itemImage = image;
        this.itemName = new SimpleStringProperty(n);
        this.itemQuantity = new SimpleIntegerProperty(q);
        this.itemPrice = new SimpleDoubleProperty(p);
    }

    @Override
    public String toString() {
        return " Name " + itemName + " Quantity " + itemQuantity + " Price " + itemPrice;
    }

    public int getItemID() {
        return itemID.get();
    }

    public ImageView getItemImage() {
        return itemImage;
    }

    public String getItemName() {
        return itemName.get();
    }

    public int getItemQuantity() {
        return itemQuantity.get();
    }

    public double getItemPrice() {
        return itemPrice.get();
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity.set(itemQuantity);
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice.set(itemPrice);
    }
}
