package controller;

import blueprint.Order;
import blueprint.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sqlscript.SQLPrepareStatement;
import stage.SwitchScene;

public class Employee {
    private static String userID, setUserFirstName;
    private SQLPrepareStatement statement = new SQLPrepareStatement();
    private String className = this.getClass().getSimpleName();
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productID;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productQuantity;
    @FXML
    private TableColumn<Product, Double> productPrice;
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, Integer> orderID;
    @FXML
    private TableColumn<Order, Integer> customerID;
    @FXML
    private TableColumn<Order, String> itemName;
    @FXML
    private Button log, account;
    @FXML
    private TextField searchProduct, searchOrder;
    @FXML
    private Label uAccount;
    @FXML
    private Pane popUp;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        loadProducts(products);
        filterProductTable();
        filterOrderTable();
        changePaneBehavior();
        uAccount.setOnMouseEntered(e -> popUp.setVisible(true));

        root.setOnMouseEntered(e -> uAccount.setText("Hello, " + setUserFirstName));
    }

    @FXML
    private void changePaneBehavior(){
        popUp.setPrefHeight(58);
        account.setVisible(false);
        log.setVisible(false);
        popUp.setOnMouseEntered(e -> {
            account.setVisible(true);
            popUp.setPrefHeight(130);
            account.setVisible(true);
            log.setVisible(true);
        });

        popUp.setOnMouseExited(e -> {
            popUp.setPrefHeight(58);
            account.setVisible(false);
            log.setVisible(false);
        });

        log.setOnAction(e -> {
            SwitchScene.closeStage(e);
            SwitchScene.switchStage(className, null, "Customer", new Button(null));
        });
        account.setOnAction(e -> {
            SwitchScene.closeStage(e);
            SwitchScene.switchScene(account.getText().trim(), userID);
        });
    }

    private void filterProductTable() {
        FilteredList<Product> filteredScoreData = new FilteredList<>(products, p -> true);
        searchProduct.textProperty().addListener((observable, oldValue, newValue) -> filteredScoreData.setPredicate(p -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return (String.valueOf(p.getProductID()).toLowerCase().contains(lowerCaseFilter))
                        || (p.getName().toLowerCase().contains(lowerCaseFilter));
        }));

        SortedList<Product> sortedData = new SortedList<>(filteredScoreData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);
    }

    private void filterOrderTable() {
        FilteredList<Order> filteredScoreData = new FilteredList<>(orders, o -> true);
        searchOrder.textProperty().addListener((observable, oldValue, newValue) -> filteredScoreData.setPredicate(o -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return (String.valueOf(o.getOrderID()).toLowerCase().contains(lowerCaseFilter) ||
                    String.valueOf(o.getUserID()).toLowerCase().contains(lowerCaseFilter)) ||
                    (o.getProductName().toLowerCase().contains(lowerCaseFilter));
        }));

        SortedList<Order> sortedData = new SortedList<>(filteredScoreData);
        sortedData.comparatorProperty().bind(orderTableView.comparatorProperty());
        orderTableView.setItems(sortedData);
    }

    private void loadProducts(ObservableList<Product> products) {
        getUserID(userID, new Button(setUserFirstName));
        statement.getProducts(products, userID, log);
        statement.getOrder(orders, null);

        productTable(productTable, productID, productName, productQuantity, productPrice);
        orderTable(orderTableView, orderID, customerID, itemName);
    }

    private void productTable(TableView<Product> table, TableColumn<Product, Integer> productID,
                              TableColumn<Product, String> name, TableColumn<Product, Integer> quantity,
                              TableColumn<Product, Double> price) {
        if (productTable.getItems().size() > 0) {
            productTable.getItems().clear();
        }

        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.setItems(products);
    }

    private void orderTable(TableView<Order> table, TableColumn<Order, Integer> oID,
                              TableColumn<Order, Integer> custID, TableColumn<Order, String> pName) {
        if (table.getItems().size() > 0) {
            table.getItems().clear();
        }

        oID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        custID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        pName.setCellValueFactory(new PropertyValueFactory<>("productName"));

        table.setItems(orders);
    }

    public static void getUserID(String id, Button userFirstName) {
        userID = id;
        setUserFirstName = userFirstName.getText();
        userFirstName.setText("Sign Out");
    }
}
