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
import javafx.scene.layout.Pane;
import sqlscript.SQLPrepareStatement;
import stage.SwitchScene;

public class Admin {
    private static String userID;
    private SQLPrepareStatement statement = new SQLPrepareStatement();

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
    private Button logout, account;
    @FXML
    private TextField searchProduct;
    @FXML
    private Label uAccount;
    @FXML
    private Pane popUp;

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadProducts(products);
        FilterProductBySearchKeyword();
//        uAccount.setText("Hello, " + statement.userInfo(userID));

        uAccount.setOnMouseEntered(e -> popUp.setVisible(true));

        popUp.shapeProperty().addListener(observable -> {
            if (!popUp.isVisible()) {
                popUp.setVisible(false);
            }
        });

        popUp.setOnMouseExited(event -> popUp.setVisible(false));
    }

    public static void getUserID(String id) {
        userID = id;
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
        popUp.setVisible(false);
        getUserID(userID);
        statement.product(products, userID, uAccount);
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
        switchStage(event);
    }

    private void switchStage(Event event) {
        String className = this.getClass().getSimpleName();
        ((Node)event.getSource()).getScene().getWindow().hide();
        SwitchScene.switchScene(className, null, false);
    }

}
