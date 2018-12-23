package controller;

import blueprint.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sqlscript.SQLPrepareStatement;
import stage.SwitchScene;

public class Employee {
    private static String userID, setUserFirstName;
    private SQLPrepareStatement statement = new SQLPrepareStatement();
    @FXML
    private AnchorPane root;
    @FXML
    private GridPane gridPaneFooter;
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
    private Button log, account;
    @FXML
    private TextField searchProduct;
    @FXML
    private Label uAccount, footer;
    @FXML
    private Pane popUp, topPane;

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        loadProducts(products);
        FilterProductBySearchKeyword();
        changePaneBehavior();
        uAccount.setOnMouseEntered(e -> popUp.setVisible(true));
//        popUp.shapeProperty().addListener(observable -> {
//            if (!popUp.isVisible()) {
//                popUp.setVisible(false);
//            }
//        });
//        popUp.setOnMouseExited(event -> popUp.setVisible(false));
        root.setOnMouseEntered(e -> {
            if (userID != null){
                uAccount.setText("Hello, " + setUserFirstName);
            }
        });
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

        //signOut.setOnAction(this::switchStage);
    }

    private void FilterProductBySearchKeyword() {
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

    private void loadProducts(ObservableList<Product> products) {
        getUserID(userID, new Button(setUserFirstName));
        statement.getProducts(products, userID, log);
        if (productTable.getItems().size() > 0) {
            productTable.getItems().clear();
        }

        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(products);
    }

    @FXML
    private void logInAndLogOut(Event event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        String className = this.getClass().getSimpleName();
        SwitchScene.switchScene(className, null, "Customer", new Button(uAccount.getText()));
    }

    public static void getUserID(String id, Button userFirstName) {
        userID = id;
        setUserFirstName = userFirstName.getText();
    }
}
