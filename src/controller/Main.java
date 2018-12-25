package controller;

import blueprint.Cart;
import blueprint.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import message.Message;
import sqlscript.SQLPrepareStatement;
import stage.SwitchScene;
import utility.Utility;

import java.text.NumberFormat;
import java.util.List;


public class Main {
    private NumberFormat nf = NumberFormat.getCurrencyInstance();
    private static String userID, setUserName;
    private SQLPrepareStatement statement = new SQLPrepareStatement();
    @FXML
    private AnchorPane root;
    @FXML
    private GridPane gridPaneTopRight;
    @FXML
    private FlowPane flowPane;
    @FXML
    private TextField searchKeyWord;
    @FXML
    private Label rAddress, totalItem, discount, totalPrice, discountPer, sumPrice, copyRight;
    @FXML
    private Button removeItem, checkOut, log, signOut;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableView<Cart> itemListTable;
    @FXML
    private TableColumn<Cart, String> pName;
    @FXML
    private TableColumn<Cart, Integer> itemQuantity;
    @FXML
    private TableColumn<Cart, Double> productPrice;
    @FXML
    private ComboBox<Integer> numQuantity;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Button> buttonList = FXCollections.observableArrayList();
    private ObservableList<Button> observableList = FXCollections.observableArrayList();
    private ObservableList<Cart> shoppingCarts = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadData(products);
        changePaneBehavior();
        root.setOnMouseEntered(e -> {
            if (userID != null) {
                log.setText("Hello, " + setUserName);
            }

        });
        disableCartQuantityField();
        rAddress.setText("3420 East way Ave NW\n\tRoanoke VA");
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
            shoppingCarts.remove(Integer.parseInt(removeItem.getText()));
            itemListTable.setItems(shoppingCarts);
            Utility.calculatePrice(shoppingCarts, sumPrice, totalPrice, discountPer, discount, totalItem);
        });

        numQuantity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (numQuantity.getSelectionModel().getSelectedItem() != null) {
                int productID = shoppingCarts.get((Integer.parseInt(removeItem.getText()))).getItemID();
                int quantity = numQuantity.getSelectionModel().getSelectedItem();
                double price = getProductOriginalPrice(productID) * quantity;
                shoppingCarts.get((Integer.parseInt(removeItem.getText()))).setItemQuantity(quantity);
                shoppingCarts.get((Integer.parseInt(removeItem.getText()))).setItemPrice(Utility.roundPrice(price));
                itemListTable.refresh();
                Utility.calculatePrice(shoppingCarts, sumPrice, totalPrice, discountPer, discount, totalItem);
            }
        });
    }

    /**
     * changePaneBehavior: Changes the behavior of the
     * top pane base upon if the user hover on the username when
     * the user is login to show other available buttons.
     */
    @FXML
    private void changePaneBehavior(){
        gridPaneTopRight.setPrefHeight(52);
        signOut.setVisible(false);
        gridPaneTopRight.setOnMouseEntered(e -> {
            if (!log.getText().equals("Register / Sign In")) {
                signOut.setVisible(true);
                gridPaneTopRight.setPrefHeight(110);
                signOut.setVisible(true);
            }
        });

        gridPaneTopRight.setOnMouseExited(e -> {
            gridPaneTopRight.setPrefHeight(52);
            signOut.setVisible(false);
        });

        log.setOnAction(e -> {
            if (userID == null) {
                SwitchScene.closeStage(e);
                SwitchScene.switchStage(this.getClass().getSimpleName(), setUserName, "Customer", log);
            }
        });
        signOut.setOnAction(e -> logOut());
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
            if (newValue.isEmpty() || newValue == null) {
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
        for (Cart cart : shoppingCarts) {
            if (shoppingCarts.size() != 0) {
                if (cart.getItemName().equals(name)) {
                    Message.successful((name + " is already in your cart. " +
                            "Select\nit in your car and update it quantity."), 1);
                    return;
                }
            }
        }
        shoppingCarts.add(new Cart(productID, view, name, 1, price));
        Utility.cartsTableProperty(itemListTable, pName, itemQuantity, productPrice, shoppingCarts);
        Utility.calculatePrice(shoppingCarts, sumPrice, totalPrice, discountPer, discount, totalItem);
    }

    /**
     * getProductOriginalPrice Method: The method take in the product
     * id and return the product original price
     * @param productID
     * Product ID. Need it to get the product price
     * @return
     * Will return either the product price or 0 if
     * product not found
     */
    private double getProductOriginalPrice(int productID){
        for (Product product : products){
            if (product.getProductID() == productID)
                return product.getPrice();
        }
        return 0;
    }

    /**
     * loadDate Method: This method is call when you run the
     * program to load all product from the database
     * @param products
     * Empty list of products being pass in to load data from the database into it
     */
    private void loadData(ObservableList<Product> products) {
        log.setText(statement.getProducts(products, userID, log).getText());
    }

    private void disableCartQuantityField() {
        this.itemListTable.setPlaceholder(new Label(""));
        removeItem.setVisible(false);
        checkOut.setVisible(false);
        numQuantity.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numQuantity.setVisible(false);
    }

    /**
     * CheckoutOrder Method: When the carts button is
     * press this method is call and the checkout items will be
     * pass into the switchScene method for final checkout
     * @param event
     * Need it to close current class.
     */
    @FXML
    private void checkoutOrder(Event event){
        String className = this.getClass().getSimpleName();
        ((Node)event.getSource()).getScene().getWindow().hide();
        SwitchScene.switchScene(shoppingCarts, className, userID, new Button(setUserName));
    }

    /**
     * Resetting the username and userID will lock the user out
     * from the database and reset all other field
     */
    @FXML
    private void logOut() {
        log.setText("Register / Sign In");
        setUserName = null; userID = null;
    }

    /**
     * The getUserID Method. When user login, getUserID is call inside
     * the SwitchScene class to pass the user id in it after retrieve it
     * from the database. This id will be use when the user want to change
     * their info etc.
     * @param id
     * User ID, which is being pass from the previous stage when you login or
     * when switching between stage
     */
    public static void getUserID(String id, Button userFirstName) {
        userID = id;
        setUserName = userFirstName.getText();

    }
}
