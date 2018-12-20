package sqlscript;

import blueprint.Cart;
import blueprint.Product;
import dbconnection.DBConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    public boolean checkLoginInfo(TextField name, PasswordField password) {
        try {
            pst = dbConnection.getConnection().prepareStatement(query.getUserLogin());
            pst.setString(1, name.getText().trim());
            pst.setString(2, password.getText().trim());
            rs = pst.executeQuery();

            if (rs.first()) {
                name.setText("" + Integer.toString(rs.getInt("UserID")));
                if (rs.getString("UserType").equals("Admin"))
                    return true;
            }
        }catch (SQLException ex) {
            String message = "Username: " + name.getText().trim() + ", password " + password.getText().trim() +
                    " is in correct. " + ex.getMessage();
            Message.operationFailed("Exception", message);
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.operationFailed("SQL-Exception", sql.getMessage());
            }
        }
        return false;
    }

    public String userInfo (String userID, Label uAccount) {
        try {
            pst = dbConnection.getConnection().prepareStatement(query.getUserInfo());
            pst.setString(1, userID);
            rs = pst.executeQuery();

            if (rs.first()) {
                System.out.println("Print: " + rs.getString("Username"));
                uAccount.setText("Hello, " + rs.getString("Username"));
            }

        }catch (SQLException ex) {
            Message.operationFailed("Exception", ex.getMessage());
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.operationFailed("SQL-Exception", sql.getMessage());
            }
        }
        return null;
    }

    public void getProducts(ObservableList<Product> products, String userID, Label uAccount) {
        try {
            if (uAccount.getText() != null && userID != null) {
                pst = dbConnection.getConnection().prepareStatement(query.getUserInfo());
                pst.setString(1, userID);
                rs = pst.executeQuery();

                if (rs.first()) {
                    uAccount.setText("Hello, " + rs.getString("Username"));
                }
            }
            pst = dbConnection.getConnection().prepareStatement(query.getLoadProduct());
            rs = pst.executeQuery();

            while (rs.next()) {
                products.add(new Product(rs.getInt("ProductID"), rs.getString("PName"),
                        rs.getInt("Quantity"), rs.getDouble("Price"), rs.getString("ImageName")));
            }
        }catch (SQLException ex) {
            Message.operationFailed("Exception", ex.getMessage());
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.operationFailed("SQL-Exception", sql.getMessage());
            }
        }
    }

    public boolean setUserLogin(int userID, String fName, String lName, String  userName,
                                String password, String userType) {
        try {
            pst = dbConnection.getConnection().prepareStatement(query.setUserLogin());
            pst.setInt(1, userID);
            pst.setString(2, fName);
            pst.setString(3, lName);
            pst.setString(4, userName);
            pst.setString(5, password);
            pst.setString(6, userType);

            pst.executeUpdate();
            return true;
        }catch (SQLException ex) {
            Message.operationFailed(null, ex.getMessage());
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.operationFailed("SQL-Exception", sql.getMessage());
            }
        }
        return false;
    }

    public boolean setUserAddressInfo(int userID, String address, String city, String  state,
                                String zipCode) {

        openConnection();
        try {
            pst = dbConnection.getConnection().prepareStatement(query.setUserLogin());
            pst.setInt(1, userID);
            pst.setString(2, address);
            pst.setString(3, city);
            pst.setString(4, state);
            pst.setString(5, zipCode);

            pst.executeUpdate();
            return true;
        }catch (SQLException ex) {
            Message.operationFailed(null, ex.getMessage());
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.operationFailed("SQL-Exception", sql.getMessage());
            }
        }
        return false;
    }

    public boolean insertOrderItems(ObservableList<Cart> carts, int orderID, int userID) {
        try {

            for (Cart item : carts) {
                pst = dbConnection.getConnection().prepareStatement(query.setInsertOrderTable());
                pst.setInt(1, orderID);
                pst.setInt(2, userID);
                pst.setInt(3, item.getProductID());
                pst.setString(4, item.getName());
                pst.setInt(5, item.getQuantity());
                pst.setDouble(6, item.getPrice());

                pst.executeUpdate();
            }
            return true;
        }catch (SQLException ex) {
            Message.operationFailed(null, ex.getMessage());
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.operationFailed("SQL-Exception", sql.getMessage());
            }
        }
        return false;
    }

    private void openConnection(){
        if (dbConnection.getConnection() == null) {
            try {
                dbConnection = DBConnection.getInstance();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
