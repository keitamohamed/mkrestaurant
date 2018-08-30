package sqlscript;

import blueprint.Product;
import dbconnection.DBConnection;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import message.Message;

import java.sql.Connection;
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

    public void product (ObservableList<Product> products) {
        try {
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
