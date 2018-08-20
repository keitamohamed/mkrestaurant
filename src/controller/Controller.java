package controller;

import blueprint.Cart;
import blueprint.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import message.Message;
import org.controlsfx.control.Notifications;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;


public class Controller {

    @FXML
    private FlowPane flowPane;
    @FXML
    private TextField searchKeyWord;
    @FXML
    private Label rAddress, totalItem, discount, totalPrice, discountPer, sumPrice;
    @FXML
    private Button removeItem, updateCart, checkOut;
    @FXML
    TableView<Cart> itemListTable;
    @FXML
    TableColumn<Cart, String> pName;
    @FXML
    TableColumn<Cart, Integer> quantity;
    @FXML
    TableColumn<Cart, Double> productPrice;

    private Notifications notifications;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Button> buttonList = FXCollections.observableArrayList();
    private ObservableList<Button> observableList = FXCollections.observableArrayList();
    private ObservableList<Cart> carts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadData(products);
        doNotShowMessage();
        rAddress.setText("3420 Eastway Ave NW\n\tRoanoke VA");
        flowPaneChildren(buttonList, products);
        filterSearch();
        actionListener(buttonList, products);
        itemListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (itemListTable.getSelectionModel().getSelectedItem() != null) {
                if (!removeItem.isVisible() && !updateCart.isVisible()) {
                    removeItem.setVisible(true);
                    updateCart.setVisible(true);
                }
                removeItem.setText("" + itemListTable.getSelectionModel().getSelectedIndex());
                updateCart.setText("" + itemListTable.getSelectionModel().getSelectedIndex());
            }
        });

        removeItem.setOnAction(event -> {
            carts.remove(Integer.parseInt(removeItem.getText()));
            itemListTable.setItems(carts);
            calculateTotal(carts);
        });


    }

    private void flowPaneChildren(List<Button> buttonList, List<Product> products) {
        Button button;
        for (Product product : products) {
            button = new Button();
            button.setGraphic(imageView(product.getImage()));
            button.setText("" + product.getProductID());
            buttonList.add(button);
            flowPane.getChildren().add(button);
        }
        observableList.addAll(buttonList);
    }

    private void actionListener(List<Button> buttons, ObservableList<Product> products) {
        for (int i = 0; i < buttons.size(); i++) {
            final int location = i;
            buttons.get(i).setOnAction(e -> {
                for (Product p : products) {
                    if (Integer.parseInt(buttons.get(location).getText()) == p.getProductID()) {
                        shoppingTable(p.getName(), p.getPrice());
                        Message.successful("Successfully added product");
                        if (!checkOut.isVisible() && !totalPrice.getText().equals("0")) {
                            checkOut.setVisible(true);
                        }
                    }
                }
            });
        }
    }

    private void filterSearch() {
        searchKeyWord.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.isEmpty()) {
                flowPane.getChildren().clear();
                flowPaneChildren(buttonList, products);
                actionListener(buttonList, products);
                return;
            }

            flowPane.getChildren().clear();
            for (Product product : products) {
                Button button = new Button();
                if (product.getName().toLowerCase().contains(newValue.toLowerCase())
                        || String.valueOf(product.getProductID()).contains(newValue.toLowerCase())) {
                    button.setGraphic(imageView(product.getImage()));
                    button.setText("" + product.getProductID());
                    buttonList.add(button);
                    flowPane.getChildren().add(button);
                }
            }

            actionListener(buttonList, products);
        });
    }

    private void shoppingTable(String name, double price) {
        carts.add(new Cart(name, 1, price));
        pName.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        itemListTable.setItems(carts);
        calculateTotal(carts);
    }

    private void loadData(List<Product> products) {
        products.add(new Product(generateProductID(), "Lay's, Sun and Jalapeno Chip", 1, 3.78, "chips"));
        products.add(new Product(generateProductID(), "Chips Ahoy Cookie", 15, 5.37, "chips-ahoy-cookie"));
        products.add(new Product(generateProductID(), "Pure-Life Water", 7, 3.98, "pure-life-water"));
        products.add(new Product(generateProductID(), "Grandmas Chocolate Cookie", 9, 1.89, "grandmas-cookie"));
        products.add(new Product(generateProductID(), "Origin Water", 5, 2.56, "origing-water"));
        products.add(new Product(generateProductID(), "Deer Park Water", 9, 2.89, "deer-park"));
        products.add(new Product(generateProductID(), "Strawberry and Peach", 10, 5.47, "fruit-one"));
        products.add(new Product(generateProductID(), "Apple, Banana and Pana", 10, 12.36, "fruit-two"));
        products.add(new Product(generateProductID(), "Golden valley Water", 13, 1.50, "golden-valley"));
        products.add(new Product(generateProductID(), "Orange", 89, 3.89, "orange"));
        products.add(new Product(generateProductID(), "Orange drink", 23, 2.49, "orange-drink"));
        products.add(new Product(generateProductID(), "Oreo Cookie", 6, 3.67, "oreo"));
    }

    private ImageView imageView(String imageName) {
        Image image;
        ImageView view;
        try {
            image = new Image(getClass().getResourceAsStream("/image/pImage/" + imageName + ".png"));
            view = new ImageView(image);
            view.setFitWidth(150);
            view.setFitHeight(135);
        } catch (NullPointerException e) {
            image = new Image(getClass().getResourceAsStream("/image/pImage/" + imageName + ".jpg"));
            view = new ImageView(image);
            view.setFitWidth(150);
            view.setFitHeight(135);
        }
        return view;
    }

    private void calculateTotal(ObservableList<Cart> carts) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        double price = 0;
        if (!totalItem.getText().isEmpty()) {
            totalItem.setText("0");
        }
        for (Cart c : carts) {
            totalItem.setText(String.valueOf((Integer.parseInt(totalItem.getText()) + c.getQuantity())));

            if (sumPrice.getText().equals("0")) {
                sumPrice.setText(nf.format((c.getQuantity() * c.getPrice())));
                totalPrice.setText(nf.format((c.getQuantity() * c.getPrice())));
            }
            else {
                price += (c.getQuantity() * c.getPrice());
                if (price > 100) {
                    double dPercent = .05;
                    if (price >= 1000) {
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
        // If shopping cart is empty-Re-set discount and price variables back to their default value
        if (carts.size() == 0) {
            sumPrice.setText("0");
            totalPrice.setText("0");
            discountPer.setText("0 %");
            discount.setText("0");
        }
    }

    private void doNotShowMessage() {
        this.itemListTable.setPlaceholder(new Label(""));
        removeItem.setVisible(false);
        updateCart.setVisible(false);
        checkOut.setVisible(false);
    }

    private int generateProductID() {
        Random random = new Random();
        return (random.nextInt(90000) + 90000);
    }
}
