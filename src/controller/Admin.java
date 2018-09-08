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
import org.controlsfx.control.PopOver;
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
    private Button logout;
    @FXML
    private TextField searchP;
    @FXML
    private Label name;
    private PopOver over = new PopOver();

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadProduct(products);
        sortBy();

        name.setOnMouseEntered(e -> {
            show();
        });

        name.setOnMouseExited(e -> {
            over.hide();
        });
    }
    public static void receiveUserID(String id) {
        userID = id;
    }

    private void sortBy() {

        FilteredList<Product> filteredScoreData = new FilteredList<>(products, p -> true);
        searchP.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredScoreData.setPredicate(p -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return (String.valueOf(p.getProductID()).toLowerCase().contains(lowerCaseFilter))
                            || (p.getName().toLowerCase().contains(lowerCaseFilter));
            });
        });

        SortedList<Product> sortedData = new SortedList<>(filteredScoreData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);
    }

    private void loadProduct(ObservableList<Product> products) {
        receiveUserID(userID);
        statement.product(products);
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
    private void logOut(Event event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        switchStage(event);
    }

    private void switchStage(Event event) {
        String className = this.getClass().getSimpleName();
        ((Node)event.getSource()).getScene().getWindow().hide();
        SwitchScene.switchScene(className, null, false);
    }

    private void show() {
        over.show(name);
    }
}
