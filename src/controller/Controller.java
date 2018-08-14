package controller;

import blueprint.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;


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
    TableView itemListTable, totalPriceTable;

    private List<Product> products = new ArrayList<Product>();
    private List<Button> buttonList = new ArrayList<Button>();
    private ObservableList<Button> observableList;

    @FXML
    public void initialize() {
        loadData(products);
        doNotShowMessage();
        rAddress.setText("3420 Eastway Ave NW\n\tRoanoke VA");
        flowPaneChildren(products);

    }

    private void flowPaneChildren(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Button button = new Button();
            button.setGraphic(imageView(products.get(i).getImage()));
            button.setText(products.get(i).getName());
            flowPane.getChildren().add(button);
        }
        observableList = FXCollections.observableList(buttonList);
    }

    private void loadData(List<Product> products) {
        products.add(new Product("Lay's, Sun and Jalapeno", 5, 8.89, "chips"));
        products.add(new Product("Chips Ahoy", 15, 5.37, "chips-ahoy-cookie"));
        products.add(new Product("Pure-Life Water", 7, 3.98, "pure-life-water"));
        products.add(new Product("Grandmas Chocolate", 9, 1.89, "grandmas-cookie"));
        products.add(new Product("Origing Water", 5, 2.56, "origing-water"));
        products.add(new Product("Deer Park", 9, 2.89, "deer-park"));
        products.add(new Product("Strawberry and Peach", 10, 5.47, "fruit-one"));
        products.add(new Product("Apple, Banana and Pana", 10, 12.36, "fruit-two"));
        products.add(new Product("Golden valley", 13, 3.89, "golden-valley"));
        products.add(new Product("Orange", 89, 3.89, "orange"));
        products.add(new Product("Orange drink", 23, 2.49, "orange-drink"));
        products.add(new Product("Oreo", 6, 3.67, "oreo"));
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
        this.totalPriceTable.setPlaceholder(new Label(""));
    }
}
