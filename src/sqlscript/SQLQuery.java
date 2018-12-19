package sqlscript;

public class SQLQuery {

    public SQLQuery(){}

    public String getUserLogin() {
        return "SELECT * FROM UserTable " +
                "WHERE Username = ? AND Password = ?";
    }

    public String getUserInfo() {
        return "SELECT Username" +
                "FROM UserTable" +
                "WHERE UserID = ?";
    }

    public String getLoadProduct() {
        return "SELECT ProductID, PName, Quantity, Price, ImageName " +
                "FROM ProductTable";
    }

    public String getUpdateProduct() {
        return "UPDATE ProductTable SET Quantity = ?, Price = ?, ImageName = ? " +
                "WHERE ProductID = ?";
    }

    public String getInsertProduct() {
        return "INSERT INTO ProductTable (ProductID, PName, Quantity, Price, ImageName) " +
                "VALUE (?, ?, ?, ?, ?)";
    }
}
