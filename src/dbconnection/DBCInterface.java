package dbconnection;

import java.io.IOException;
import java.sql.Connection;

public interface DBCInterface {
    Connection mySQL() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException;
    void injectPropertiesValue() throws IOException;
}
