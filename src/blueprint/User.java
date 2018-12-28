package blueprint;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User extends Address{
    private SimpleIntegerProperty userID;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty userName;
    private SimpleStringProperty password;

    public User (int id, String f, String l, String uN, String p,
                 String a, String c, String s, int z) {
        super(a, c, s, z);
        this.userID = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(f);
        this.lastName = new SimpleStringProperty(l);
        this.userName = new SimpleStringProperty(uN);
        this.password = new SimpleStringProperty(p);
    }

    @Override
    public String toString() {
        return "User ID: " + userID +
                "\nFirst Name: " + firstName +
                "\nlast Name=" + lastName +
                "\nUser Name: " + userName +
                "\nPassword" + password + super.toString();
    }

    public int getUserID() {
        return userID.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getPassword() {
        return password.get();
    }
}
