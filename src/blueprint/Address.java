package blueprint;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Address {
    private SimpleStringProperty address;
    private SimpleStringProperty city;
    private SimpleStringProperty state;
    private SimpleIntegerProperty zipCode;

    public Address (String a, String c, String s, int z){
        this.address = new SimpleStringProperty(a);
        this.city = new SimpleStringProperty(c);
        this.state = new SimpleStringProperty(s);
        this.zipCode = new SimpleIntegerProperty(z);
    }

    @Override
    public String toString() {
        return "\nAddress: " + address + " " + city + " " + state + " " + zipCode;
    }

    public String getAddress() {
        return address.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getState() {
        return state.get();
    }

    public int getZipCode() {
        return zipCode.get();
    }
}
