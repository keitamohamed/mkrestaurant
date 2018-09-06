package controller;

import blueprint.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sqlscript.SQLPrepareStatement;

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

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadProduct(products);
        sortBy();
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

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(filteredScoreData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
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
}
