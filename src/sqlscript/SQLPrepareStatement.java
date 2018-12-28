package sqlscript;

import blueprint.Cart;
import blueprint.Order;
import blueprint.Product;
import blueprint.User;
import dbconnection.DBConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import message.Message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLPrepareStatement {
    private SQLQuery query = new SQLQuery();
    private PreparedStatement pst;
    private ResultSet rs;
    private DBConnection dbConnection = new DBConnection();

    public SQLPrepareStatement() {
        try {
            if (dbConnection.getConnection() == null)
                dbConnection = DBConnection.getInstance();
        }catch (Throwable te) {
            Message.operationFailed("Throwable", te.getMessage());
        }
    }

    public String checkLoginInfo(TextField name, String password, Button userFirstName) {
        try {
            pst = dbConnection.getConnection().prepareStatement(query.getUserLogin());
            pst.setString(1, name.getText().trim());
            pst.setString(2, password.trim());
            rs = pst.executeQuery();

            if (rs.first()) {
                name.setText(Integer.toString(rs.getInt("UserID")));
                userFirstName.setText(rs.getString("FirstName"));
                return  rs.getString("UserType");
            }
        }catch (SQLException ex) {
            String message = "Username: " + name.getText().trim() + ", password " + password.trim() +
                    " is in correct. " + ex.getMessage();
            Message.operationFailed("Exception", message);
        }
        return null;
    }

    private void getUserInfo (String userID, Button userFirstName) {
        try {
            pst = dbConnection.getConnection().prepareStatement(query.getUserInfo());
            pst.setInt(1, Integer.parseInt(userID));
            rs = pst.executeQuery();

            if (rs.first()) {
                userFirstName.setText("Hello, " + rs.getString("FirstName"));
            }

        }catch (SQLException ex) {
            Message.operationFailed("Exception", ex.getMessage());
        }
    }

    public void getUserInfo (String userID, ObservableList<User> users) {
        try {
            pst = dbConnection.getConnection().prepareStatement(query.getUserInfo());
            pst.setInt(1, Integer.parseInt(userID));
            rs = pst.executeQuery();

            if (rs.first()) {
                users.add(new User(rs.getInt("UserID"), rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("UserName"), rs.getString("Password"),
                        rs.getString("UserAddress"), rs.getString("City"), rs.getString("State"),
                        rs.getInt("ZipCode")));
            }

        }catch (SQLException ex) {
            Message.operationFailed("Exception", ex.getMessage());
        }
    }

    public Button getProducts(ObservableList<Product> products, String userID, Button userFirstName) {
        try {
            if (userID != null) {
                getUserInfo(userID, userFirstName);
            }
            pst = dbConnection.getConnection().prepareStatement(query.getLoadProduct());
            rs = pst.executeQuery();

            while (rs.next()) {
                products.add(new Product(rs.getInt("ProductID"), rs.getString("PName"),
                        rs.getInt("Quantity"), rs.getDouble("Price"), rs.getString("ImageName")));
            }
        }catch (SQLException ex) {
            Message.operationFailed("Exception", ex.getMessage());
        }
        return userFirstName;
    }

    public void getOrder(ObservableList<Order> order, String userID) {
        try {
            if (userID == null) {
                pst = dbConnection.getConnection().prepareStatement(query.getOrder());
            }
            else {
                pst = dbConnection.getConnection().prepareStatement(query.getLoadProduct());
                pst.setInt(1, Integer.parseInt(userID));
            }
            rs = pst.executeQuery();

            while (rs.next()) {
                order.add(new Order(rs.getInt("OrderID"), rs.getInt("UserID"), rs.getInt("ProductID"),
                        rs.getString("ProductName"), rs.getInt("Quantity"),
                        rs.getDouble("Price")));
            }
        }catch (SQLException ex) {
            Message.operationFailed("Exception", ex.getMessage());
        }
    }

    public boolean setUserLogin(int userID, String fName, String lName, String  userName,
                                String password, String userType) {
        try {
            pst = dbConnection.getConnection().prepareStatement(query.setUserLogin());
            setField(pst, userID, fName, lName, userName, password);
            pst.setString(6, userType);

            pst.executeUpdate();
            return true;
        }catch (SQLException ex) {
            Message.operationFailed(null, ex.getMessage());
        }
        return false;
    }

    public boolean setUserAddressInfo(int userID, String address, String city, String  state,
                                String zipCode) {
        try {
            pst = dbConnection.getConnection().prepareStatement(query.setUserAddressInfo());
            setField(pst, userID, address, city, state, zipCode);
            pst.executeUpdate();
            return true;
        }catch (SQLException ex) {
            Message.operationFailed(null, ex.getMessage());
        }
        return false;
    }

    public boolean insertOrderItems(ObservableList<Cart> carts, int orderID, int userID) {
        try {

            for (Cart item : carts) {
                pst = dbConnection.getConnection().prepareStatement(query.setInsertOrderTable());
                pst.setInt(1, orderID);
                pst.setInt(2, userID);
                pst.setInt(3, item.getItemID());
                pst.setString(4, item.getItemName());
                pst.setInt(5, item.getItemQuantity());
                pst.setDouble(6, item.getItemPrice());

                pst.executeUpdate();
            }
            return true;
        }catch (SQLException ex) {
            System.out.println("Print this");
            Message.operationFailed(null, ex.getMessage());
        }
        return false;
    }

    private void setField(PreparedStatement pst, int id, String value1, String value2, String value3, String value4) {
        try {
            pst.setInt(1, id);
            pst.setString(2, value1);
            pst.setString(3, value2);
            pst.setString(4, value3);
            pst.setString(5, value4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
