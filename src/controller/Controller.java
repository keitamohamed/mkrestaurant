package controller;

import blueprint.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

    private List<Product> products = new ArrayList<Product>();
    private List<Button> buttonList = new ArrayList<Button>();
    private ObservableList<Button> observableList;
    private ImageView view;

    @FXML
    public void initialize() {
        loadData(products);
        rAddress.setText("3420 Eastway Ave NW\n\tRoanoke VA");
        flowPaneChildren(products);

    }

    private void flowPaneChildren(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Button button = new Button();
            button.setGraphic(imageView(view, products.get(i).getImage()));
            flowPane.getChildren().add(button);
        }
        observableList = FXCollections.observableList(buttonList);
    }

    private void loadData(List<Product> products) {
        products.add(new Product("Lay's, Sun and Jalapeno", 5, 8.89, "chips"));
        products.add(new Product("Strawberry and Peach", 10, 5.47, "fruit-one"));
        products.add(new Product("Apple, Banana and Pana", 10, 12.36, "fruit-two"));
        products.add(new Product("Golden valley", 13, 3.89, "golden-valley"));
        products.add(new Product("Orange", 89, 3.89, "orange"));
        products.add(new Product("Orange drink", 23, 2.49, "orange-drink"));
        products.add(new Product("Oreo Cookie", 6, 3.67, "oreo"));
    }

    private ImageView imageView (ImageView view, String imageName) {
        Image image;
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
}
