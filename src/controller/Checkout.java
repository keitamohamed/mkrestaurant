package controller;

import blueprint.Cart;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import message.Message;
import sqlscript.SQLPrepareStatement;
import stage.SwitchScene;

import java.util.Random;

public class Checkout {
    private static String userID, setUserFirstName;
    private Button getUserName = new Button();
    private boolean orderSubmitted = false;

    @FXML
    private AnchorPane root;
    @FXML
    private Button login, forgetPassword, signUp, myAccount, submitOrder;
    @FXML
    private TextField userName, password;
    @FXML
    private Label message, incorrectLogin, loginStatic;
    @FXML
    private ImageView imageViewU, passwordImage;
    @FXML
    private TableView<Cart> cartTable;
    @FXML
    private TableColumn<Cart, ImageView> itemImage;
    @FXML
    private TableColumn<Cart, String> itemName;
    @FXML
    private TableColumn<Cart, Integer> itemQuantity;
    @FXML
    private TableColumn<Cart, Double> itemPrice;

    private SQLPrepareStatement statement = new SQLPrepareStatement();
    private static ObservableList<Cart> cartList;

    @FXML
    private void initialize() {
        setLoginFieldVisible(false);
        disableCartTable();
        root.setOnMouseEntered(e -> {
            cartTable();
            enableCartTable();
            if (userID != null && !orderSubmitted) {
                submitOrder.setText("Submit Order");
                disableUnnecessarilyField();
            }
        });
        message.setText(getMessage());

        login.setOnAction(e -> {
            if (!loginFieldNotFillOut()) {
                statement.checkLoginInfo(userName, password.getText().trim(), getUserName);
                disableUnnecessarilyField();
                userID = userName.getText().trim();
                setUserFirstName = getUserName.getText().trim();
                changeButtonText();
                return;
            }
            incorrectLogin.setVisible(true);
        });

        signUp.setOnAction(e -> getPopUpStage(signUp));
        myAccount.setOnAction(e -> {
            passwordImage.setVisible(true);
            setLoginFieldVisible(true);
            incorrectLogin.setVisible(false);
        });
        submitOrder.setOnAction(this::insertOrder);
    }

    @FXML
    private void changeButtonText(){
        loginStatic.setText("You Are Successfully Login. Your ID Is: " + userID);
        loginStatic.setTextFill(Color.rgb(255,255,141));
        submitOrder.setText("Submit Order");
        loginStatic.setVisible(true);
    }

    @FXML
    private void insertOrder(Event event) {
        int orderIDGenerated = generateOrderID();
        if (submitOrder.getText().equals("Return Home")){
            SwitchScene.switchStage(event, this.getClass().getSimpleName(), userID, "Customer", new Button(setUserFirstName));
        }
        else if (userID == null) {
            disableUnnecessarilyField();
            if (statement.insertOrderItems(cartList, orderIDGenerated, 0)) {
                Message.successful(("You Order Were Successfully Receive.\n" +
                        "Your Order ID is: " + orderIDGenerated), 1);
            }
        } else {
            if (statement.insertOrderItems(cartList, orderIDGenerated, Integer.parseInt(userID))) {
                Message.successful(("You Order Was Successfully Receive. " +
                        "Your\nUser ID is: " + userID + ". Order ID is: " + orderIDGenerated), 1);
            }
        }
        submitOrder.setText("Return Home");
        cartList.clear();
        cartTable.refresh();
        cartTable.setVisible(false);
        orderSubmitted = true;
    }

    @FXML
    private void cartTable() {
        itemImage.setCellValueFactory(new PropertyValueFactory<>("view"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        cartTable.setItems(cartList);
    }

    @FXML
    private void disableUnnecessarilyField() {
        setLoginFieldVisible(false);
        signUp.setVisible(false);
        myAccount.setVisible(false);
        message.setVisible(false);
    }

    @FXML
    private void disableCartTable() {
        cartTable.setVisible(false);
    }

    private void enableCartTable() {
        cartTable.setVisible(true);
    }


    @FXML
    private void setLoginFieldVisible(boolean visible) {
        if (visible){
            message.setText("");
            message.setTextFill(Color.web("#FFFF8D"));
        }
        imageViewU.setVisible(visible);
        passwordImage.setVisible(visible);
        userName.setVisible(visible);
        password.setVisible(visible);
        incorrectLogin.setVisible(visible);
        login.setVisible(visible);
        forgetPassword.setVisible(visible);
    }

    @FXML
    private int generateOrderID() {
        Random random = new Random();
        return (random.nextInt(900000) + 900000);
    }

    @FXML
    private boolean loginFieldNotFillOut() {
        return userName.getText().isEmpty() || password.getText().isEmpty();

    }

    @FXML
    public static void getOrderList(ObservableList<Cart> cart, String id, String userFirstName) {
        cartList = cart;
        userID = id;
        setUserFirstName = userFirstName;
    }

    private String getMessage() {
        return "Important: If you are not login, please click on My Account to login. " +
                "If you do not have an Account, click on the Sign Up\nbutton and create " +
                "an Account, or Checkout as a Guess";
    }

    @FXML
    private void getPopUpStage(Button button) {
        SwitchScene.switchScene(button);
    }
}
