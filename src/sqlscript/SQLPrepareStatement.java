package sqlscript;

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
            Message.loginFailed("Throwable", te.getMessage());
        }
    }
    public boolean checkLogin (TextField name, PasswordField password) {
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
            Message.loginFailed("Exception", message);
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.loginFailed("SQL-Exception", sql.getMessage());
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
            Message.loginFailed("Exception", ex.getMessage());
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.loginFailed("SQL-Exception", sql.getMessage());
            }
        }
        return null;
    }

    public void product (ObservableList<Product> products, String userID, Label uAccount) {
        try {
            System.out.println("Print: " + userID);
            if (uAccount.getText() != null && userID != null) {
                System.out.println("Print: " + userID);
                pst = dbConnection.getConnection().prepareStatement(query.getUserInfo());
                pst.setString(1, userID);
                rs = pst.executeQuery();

                if (rs.first()) {
                    System.out.println("Print: " + rs.getString("Username"));
                    uAccount.setText("Hello, " + rs.getString("Username"));
                }
//                userInfo(userID, uAccount);
            }
            pst = dbConnection.getConnection().prepareStatement(query.getLoadProduct());
            rs = pst.executeQuery();

            while (rs.next()) {
                products.add(new Product(rs.getInt("ProductID"), rs.getString("PName"),
                        rs.getInt("Quantity"), rs.getDouble("Price"), rs.getString("ImageName")));
            }
        }catch (SQLException ex) {
            Message.loginFailed("Exception", ex.getMessage());
        }finally {
            try {
                dbConnection.getConnection().close();
            }catch (SQLException sql) {
                Message.loginFailed("SQL-Exception", sql.getMessage());
            }
        }
    }
}
