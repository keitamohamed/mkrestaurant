package controller;

import blueprint.Cart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import message.Message;
import sqlscript.SQLPrepareStatement;

import java.util.Random;

public class Checkout {

    @FXML
    private Button submit, login, clearAll, forgetPassword;
    @FXML
    private Button viewCartItem, signUp, myAccount, guessUser;
    @FXML
    private TextField firstName, lastName, userName, address;
    @FXML
    private TextField password, city, state, zipcode, rUserName;
    @FXML
    private TextField rPassword;
    @FXML
    private Label message, incorrectLogin, createMessage, viewMessage;
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

    private SQLPrepareStatement statement = new SQLPrepareStatement();
    private static ObservableList<Cart> cartList;
    private static String loginStatic;

    @FXML
    private void initialize(){
        disableLoginField();
        disableSignUpField();
        disableCartTable();

        message.setText(getMessage());

        submit.setOnAction(e -> {
            int generateUserID = generateUserID();
            if (!signUpNotFillOut()) {
                if (statement.setUserLogin(generateUserID, firstName.getText().trim(), lastName.getText().trim(),
                        rUserName.getText().trim(), rPassword.getText().trim(), "Customer")) {
                    if (statement.setUserAddressInfo(generateUserID, address.getText().trim(), city.getText().trim(),
                            state.getText().trim(), zipcode.getText().trim())) {
                        Message.successful(("Your Account Have Been Created " +
                                "\nSuccessfully. Your Id is: " + generateUserID), 8);
                        return;
                    }
                }
            }
            message.setText("All fields are require. Please fill out all fields");
            message.setTextFill(Color.rgb(255, 82, 83));
        });

        login.setOnAction(e -> {
            if (loginFieldNotFillOut()){
                incorrectLogin.setVisible(true);
            }

        });

        viewCartItem.setOnAction(e -> {
            viewMessage.setVisible(false);
            cartTable();
            enableCartTable();
        });

        guessUser.setOnAction(e -> {
            insertOrder(guessUser.getText().trim());
        });
    }

    @FXML
    private void insertOrder(String userType){
        int orderIDGenerated = generateOrderID();
        if (userType.equals("Guess User"))
            if (statement.insertOrderItems(cartList, orderIDGenerated,0))
                Message.successful(("Data Insert Successfully and " +
                        "your order id is: " + orderIDGenerated), 6);

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
    private void disableCartTable(){
        cartTable.setVisible(false);
    }

    private void enableCartTable(){
        cartTable.setVisible(true);
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
    private int generateOrderID() {
        Random random = new Random();
        return (random.nextInt(900000) + 900000);
    }

    @FXML
    private int generateUserID() {
        Random random = new Random();
        return (random.nextInt(90000) + 90000);
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
                "If you do not have an Account, click on the Sign Up\nbutton and create " +
                "an Account, or Checkout as a Guess";
    }
}
