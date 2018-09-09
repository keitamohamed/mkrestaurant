package sqlscript;

public class SQLQuery {
    private String userLogin;
    private String loadProduct;
    private String updateProduct;
    private String insertProduct;
    private String userInfo;

    public SQLQuery(){}

    public String getUserLogin() {
        return "SELECT * FROM User " +
                "WHERE Username = ? AND Password = ?";
    }

    public String getUserInfo() {
        return "SELECT Username" +
                "FROM User" +
                "WHERE UserID = ?";
    }

    public String getLoadProduct() {
        return "SELECT ProductID, PName, Quantity, Price, ImageName " +
                "FROM Product";
    }

    public String getUpdateProduct() {
        return "UPDATE Product SET Quantity = ?, Price = ?, ImageName = ? " +
                "WHERE ProductID = ?";
    }

    public String getInsertProduct() {
        return "INSERT INTO Product (ProductID, PName, Quantity, Price, ImageName) " +
                "VALUE (?, ?, ?, ?, ?)";
    }
}
