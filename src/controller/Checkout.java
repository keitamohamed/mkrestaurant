package controller;

import blueprint.Cart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Checkout {

    @FXML
    private Button submit, login, clearAll, forgetPassword;
    @FXML
    private Button viewCartItem, signUp, myAccount;
    @FXML
    private TextField firstName, lastName, userName, address;
    @FXML
    private TextField password, city, state, zipcode, rUserName;
    @FXML
    private TextField rPassword;
    @FXML
    private Label message, incorrectLogin, createMessage;
    @FXML
    private ImageView imageViewU, imageViewP;
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

    private static ObservableList<Cart> cartList;
    private static String loginStatic;

    @FXML
    private void initialize(){
        cartTable.setPlaceholder(new Label("To View Items, Click View Carts Items Button"));
        disableLoginField();
        disableSignUpField();

        message.setText(getMessage());
        submit.setOnAction(e -> System.out.println(loginStatic));
        submit.setOnAction(e -> {
            if (signUpNotFillOut()) {
                message.setText("All fields are require. Please fill out all fields");
                message.setTextFill(Color.rgb(255, 82, 83));
            }
        });

        login.setOnAction(e -> {
            if (loginFieldNotFillOut()){
                incorrectLogin.setVisible(true);
            }

        });

        viewCartItem.setOnAction(e -> cartTable());
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
    private boolean signUpNotFillOut(){
        return firstName.getText().isEmpty() || lastName.getText().isEmpty() ||
                address.getText().isEmpty() || city.getText().isEmpty() ||
                state.getText().isEmpty() || zipcode.getText().isEmpty() ||
                rUserName.getText().isEmpty() || rPassword.getText().isEmpty();
    }

    @FXML
    private void disableSignUpField() {
        submit.setVisible(false); clearAll.setVisible(false);
        firstName.setVisible(false); lastName.setVisible(false);
        address.setVisible(false); city.setVisible(false);
        state.setVisible(false); zipcode.setVisible(false);
        rUserName.setVisible(false); rPassword.setVisible(false);
        createMessage.setVisible(false);
    }

    @FXML
    private void enableSignUpField(){
        if (imageViewP.isVisible()){
            disableLoginField();
        }
        message.setText("");
        submit.setVisible(true); clearAll.setVisible(true);
        firstName.setVisible(true); lastName.setVisible(true);
        address.setVisible(true); city.setVisible(true);
        state.setVisible(true); zipcode.setVisible(true);
        rUserName.setVisible(true); rPassword.setVisible(true);
        createMessage.setVisible(true);

    }

    @FXML
    private void disableLoginField(){
        imageViewU.setVisible(false);
        imageViewP.setVisible(false);
        userName.setVisible(false);
        password.setVisible(false);
        incorrectLogin.setVisible(false);
        login.setVisible(false);
        forgetPassword.setVisible(false);
    }

    @FXML
    private void enableLoginField(){
        if (firstName.isVisible()) {
            disableSignUpField();
        }
        message.setText("");
        message.setTextFill(Color.web("#FFFF8D"));
        imageViewU.setVisible(true);
        imageViewP.setVisible(true);
        userName.setVisible(true);
        password.setVisible(true);
        login.setVisible(true);
        forgetPassword.setVisible(true);
    }

    @FXML
    private boolean loginFieldNotFillOut(){
        return userName.getText().isEmpty() || password.getText().isEmpty();

    }

    @FXML
    public static void getOrderList(ObservableList<Cart> cart, String lStatic) {
        cartList = cart;
        loginStatic = lStatic;
    }

    private String getMessage(){
        return "Important: If you are not login, please click on My Account to login. " +
                "If you do not have an Account, click\non the Sign Up button and " +
                "create an Account";
    }
}
