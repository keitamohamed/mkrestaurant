package controller;

import blueprint.Cart;
import blueprint.Product;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Controller {

    @FXML
    private Pane topPane, leftPane;
    @FXML
    private FlowPane flowPane;
    @FXML
    private ListView listView, rightPane;
    @FXML
    private Label labelCategory, rAddress;
    @FXML
    TableView<Cart> itemListTable;
    @FXML
    TableColumn<Cart, String> pName;
    @FXML
    TableColumn<Cart, Integer> quantity;
    @FXML
    TableColumn<Cart, Double> productPrice;
    @FXML
    TableView<String> totalPriceTable;

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
        actionListener(buttonList, products);

    }

    private void flowPaneChildren(List<Button> buttonList, List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Button button = new Button();
            button.setGraphic(imageView(products.get(i).getImage()));
            button.setText("" + products.get(i).getProductID());
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
                        shoppingTable(p.getName(), p.getQuantity(), p.getPrice());
                    }
                }
            });

        }
    }

    private void shoppingTable(String name, int q, double price) {
        carts.add(new Cart(name, q, price));
        pName.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        itemListTable.setItems(carts);
    }

    private void loadData(List<Product> products) {
        products.add(new Product(generateProductID(), "Lay's, Sun and Jalapeno", 5, 8.89, "chips"));
        products.add(new Product(generateProductID(),"Chips Ahoy", 15, 5.37, "chips-ahoy-cookie"));
        products.add(new Product(generateProductID(),"Pure-Life Water", 7, 3.98, "pure-life-water"));
        products.add(new Product(generateProductID(),"Grandmas Chocolate", 9, 1.89, "grandmas-cookie"));
        products.add(new Product(generateProductID(),"Origing Water", 5, 2.56, "origing-water"));
        products.add(new Product(generateProductID(),"Deer Park", 9, 2.89, "deer-park"));
        products.add(new Product(generateProductID(),"Strawberry and Peach", 10, 5.47, "fruit-one"));
        products.add(new Product(generateProductID(),"Apple, Banana and Pana", 10, 12.36, "fruit-two"));
        products.add(new Product(generateProductID(),"Golden valley", 13, 3.89, "golden-valley"));
        products.add(new Product(generateProductID(),"Orange", 89, 3.89, "orange"));
        products.add(new Product(generateProductID(),"Orange drink", 23, 2.49, "orange-drink"));
        products.add(new Product(generateProductID(),"Oreo", 6, 3.67, "oreo"));
    }

    private ImageView imageView (String imageName) {
        Image image;
        ImageView view;
        try {
            image = new Image(getClass().getResourceAsStream("/image/" + imageName + ".png"));
            view = new ImageView(image);
            view.setFitWidth(150); view.setFitHeight(135);
        }catch (NullPointerException e) {
            image = new Image(getClass().getResourceAsStream("/image/" + imageName + ".jpg"));
            view = new ImageView(image);
            view.setFitWidth(150); view.setFitHeight(135);
        }
        return view;
    }

    private void doNotShowMessage() {
        this.itemListTable.setPlaceholder(new Label(""));
    }

    private int generateProductID() {
        Random random = new Random();
        return (random.nextInt(90000) + 90000);
    }
}
