package sqlscript;

public class SQLQuery {

    public SQLQuery(){}

    public String getUserLogin() {
        return "SELECT * FROM UserTable " +
                "WHERE Username = ? AND Password = ?";
    }

    public String getUserInfo() {
        return "SELECT * " +
                "FROM UserTable " +
                "WHERE UserID = ?";
    }

    public String setUserLogin() {
        return "INSERT INTO UserTable (UserID, FirstName, LastName, UserName, Password, UserType) " +
                "VALUE (?, ?, ?, ?, ?, ?)";
    }

    public String setUserAddressInfo() {
        return "INSERT INTO Address (UserID, UserAddress, City, State, ZipCode) " +
                "VALUE (?, ?, ?, ?, ?)";
    }

    public String getLoadProduct() {
        return "SELECT ProductID, PName, Quantity, Price, ImageName " +
                "FROM ProductTable";
    }

    public String getOrderTableItems() {
        return "SELECT OrderID, UserID, PID, Quantity, Price, ImageName" +
                "FROM OrderTable";
    }

    public String getUserOrderItem() {
        return "SELECT OrderID, UserID, PID, Quantity, Price, ImageName" +
                "FROM OrderTable" +
                "WHERE UserID = ?";
    }

    public String setUpdateProduct() {
        return "UPDATE ProductTable SET Quantity = ?, Price = ?, ImageName = ? " +
                "WHERE ProductID = ?";
    }

    public String setInsertProduct() {
        return "INSERT INTO ProductTable (ProductID, PName, Quantity, Price, ImageName) " +
                "VALUE (?, ?, ?, ?, ?)";
    }

    public String setInsertOrderTable() {
        return "INSERT INTO OrderTable (OrderID, UserID, PID, ProductName, Quantity, Price) " +
                "VALUE (?, ?, ?, ?, ?, ?)";
    }
}
