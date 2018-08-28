package dbconnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface DBCInterface {
    Connection mySQL() throws ClassNotFoundException, IOException,
            IllegalAccessException, InstantiationException;
    void injectPropertiesValue() throws IOException;
}
