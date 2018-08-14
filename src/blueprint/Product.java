package blueprint;

public class Product {
    private String name;
    private int quantity;
    private double price;
    private String image;

    public Product() {}
    public Product(String n, int q, double p, String i) {
        this.name = n;
        this.quantity = q;
        this.price = p;
        this.image = i;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
