package controller;

import blueprint.Cart;
import blueprint.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import message.Message;
import sqlscript.SQLPrepareStatement;
import stage.SwitchScene;
import utility.Utility;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;


public class Main {
    private NumberFormat nf = NumberFormat.getCurrencyInstance();
    private static String userID;
    private SQLPrepareStatement statement = new SQLPrepareStatement();
    @FXML
    private FlowPane flowPane;
    @FXML
    private TextField searchKeyWord;
    @FXML
    private Label rAddress, totalItem, discount, totalPrice, discountPer, sumPrice, copyRight;
    @FXML
    private Button removeItem, checkOut, log;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    TableView<Cart> itemListTable;
    @FXML
    TableColumn<Cart, String> pName;
    @FXML
    TableColumn<Cart, Integer> quantity;
    @FXML
    TableColumn<Cart, Double> productPrice;
    @FXML
    private ComboBox<Integer> numQuantity;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Button> buttonList = FXCollections.observableArrayList();
    private ObservableList<Button> observableList = FXCollections.observableArrayList();
    private ObservableList<Cart> ShoppingCarts = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadData(products);
        disableCartQuantityField();
        rAddress.setText("3420 Eastway Ave NW\n\tRoanoke VA");
        copyRight.setText("Copyright \u00a9 2018. All right reserved. Powered by M.Keita Platform");
        flowPaneChildren(buttonList, products);
        filterSearchProductByKeyword();
        actionListener(buttonList, products);
        itemListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (itemListTable.getSelectionModel().getSelectedItem() != null) {
                if (!removeItem.isVisible()) {
                    removeItem.setVisible(true);
                    numQuantity.setVisible(true);
                }
                removeItem.setText("" + itemListTable.getSelectionModel().getSelectedIndex());
            }
        });

        removeItem.setOnAction(event -> {
            ShoppingCarts.remove(Integer.parseInt(removeItem.getText()));
            itemListTable.setItems(ShoppingCarts);
            Utility.calculatePrice(ShoppingCarts, sumPrice, totalPrice, discountPer, discount, totalItem);
        });

        numQuantity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (numQuantity.getSelectionModel().getSelectedItem() != null) {
                int productID = ShoppingCarts.get((Integer.parseInt(removeItem.getText()))).getProductID();
                int quantity = numQuantity.getSelectionModel().getSelectedItem();
                double price = getProductOriginalPrice(productID) * quantity;
                ShoppingCarts.get((Integer.parseInt(removeItem.getText()))).setQuantity(quantity);
                ShoppingCarts.get((Integer.parseInt(removeItem.getText()))).setPrice(Utility.roundPrice(price));
                itemListTable.refresh();
                Utility.calculatePrice(ShoppingCarts, sumPrice, totalPrice, discountPer, discount, totalItem);
            }
        });
    }

    private void flowPaneChildren(List<Button> buttonList, List<Product> products) {
        Button button;
        for (Product product : products) {
            button = new Button();
            button.setGraphic(Utility.getImageProduct(product.getImage(), 150, 135));
            button.setText("" + product.getProductID());
            buttonList.add(button);
            flowPane.getChildren().addAll(button,
                    new Label("\tPrice: " + nf.format(product.getPrice()) + "  Quantity: " + product.getQuantity()));
        }

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        observableList.addAll(buttonList);
    }

    private void actionListener(List<Button> buttons, ObservableList<Product> products) {
        for (int i = 0; i < buttons.size(); i++) {
            final int location = i;
            buttons.get(i).setOnAction(e -> {
                for (Product p : products) {
                    if (Integer.parseInt(buttons.get(location).getText()) == p.getProductID()) {
                        cartTable(p.getProductID(), Utility.getImageProduct(p.getImage(), 100, 75), p.getName(), p.getPrice());
                        if (!checkOut.isVisible() && !totalPrice.getText().equals("0")) {
                            checkOut.setVisible(true);
                        }
                    }
                }
            });
        }
    }

    private void filterSearchProductByKeyword() {
        searchKeyWord.textProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
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
                    button.setGraphic(Utility.getImageProduct(product.getImage(), 150, 135));
                    button.setText("" + product.getProductID());
                    buttonList.add(button);
                    flowPane.getChildren().addAll(button, new Label("\tPrice: " + nf.format(product.getPrice()) + "   Quantity: " + product.getQuantity()));
                }
            }
            actionListener(buttonList, products);
        });
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private void cartTable(int productID, ImageView view, String name, double price) {
        for (Cart cart : ShoppingCarts) {
            if (ShoppingCarts.size() != 0) {
                if (cart.getName().equals(name)) {
                    Message.successful((name + " is already in your cart. " +
                            "Select\nit in your car and update it quantity."), 1);
                    return;
                }
            }
        }
        ShoppingCarts.add(new Cart(productID, view, name, 1, price));
        pName.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        itemListTable.setItems(ShoppingCarts);
        Utility.calculatePrice(ShoppingCarts, sumPrice, totalPrice, discountPer, discount, totalItem);
    }

    private double getProductOriginalPrice(int productID){
        for (Product product : products){
            if (product.getProductID() == productID)
                return product.getPrice();
        }
        return 0;
    }

    private void loadData(ObservableList<Product> products) {
        statement.getProducts(products, userID, new Label());
    }

    private void disableCartQuantityField() {
        this.itemListTable.setPlaceholder(new Label(""));
        removeItem.setVisible(false);
        checkOut.setVisible(false);
        numQuantity.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numQuantity.setVisible(false);
    }

    @FXML
    private int generateProductID() {
        Random random = new Random();
        return (random.nextInt(90000) + 90000);
    }

    @FXML
    private void checkoutOrder(Event event){
        String className = this.getClass().getSimpleName();
        ((Node)event.getSource()).getScene().getWindow().hide();
        SwitchScene.switchScene(ShoppingCarts, className, userID);
    }

    /**
     * logInAndLogout Method is call to switch between stage only
     * if the login button text is equal to "Sign In" for this class.
     * And also the class name is pass into the switchStage Method
     * to deterrent which fxmal to get
     * @param event
     */
    @FXML
    public void logInAndLogOut(Event event) {
        if (log.getText().equals("Sign In")) {
            String className = this.getClass().getSimpleName();
            switchStage(event, className);
        }
    }

    @FXML
    private void switchStage(Event event, String type) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        SwitchScene.switchScene(type, null, false);
    }

    /**
     * The getUserID Method. When user login, getUserID is call inside
     * the SwitchScene class to pass the user id in it after retrieve it
     * from the database. This id will be use when the user want to change
     * their info etc.
     * @param id
     */
    public static void getUserID(String id) {
        userID = id;
    }
}
