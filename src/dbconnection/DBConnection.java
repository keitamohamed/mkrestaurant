package dbconnection;

import message.Message;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class DBConnection implements DBCInterface{
    private InputStream inputStream;
    private Connection connection;

    private String sName, sPassword, dbName, drName, url;
    private static  DBConnection instance;

    public static DBConnection getInstance() throws Throwable {
        if (instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    private DBConnection () throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        connection = mySQL();
    }

    @Override
    public Connection mySQL() throws ClassNotFoundException, IOException,
            IllegalAccessException, InstantiationException{
        injectPropertiesValue();

        try {
            Class.forName(drName).getDeclaredConstructor().newInstance();
            Enumeration enumeration = DriverManager.getDrivers();
            while (enumeration.hasMoreElements())
                enumeration.nextElement();
            System.out.printf("Successfully connected 1");
        } catch (NoSuchMethodException | InvocationTargetException e) {
            Message.connectionFailed("No-Such-Method-Exaction", e.getMessage());
        }

        try {
            connection = DriverManager.getConnection((url + dbName), sName, sPassword);
            System.out.printf("Successfully connected 2");
        } catch (SQLException | NullPointerException e) {
            Message.connectionFailed("Connection Failed", e.getMessage());
        }

        return connection;
    }

    @Override
    public void injectPropertiesValue() throws IOException {
        try {
            Properties properties = new Properties();
            String drURL = "config/config.properties";
            inputStream = new FileInputStream(drURL);
            properties.load(inputStream);

            sName = properties.getProperty("sName");
            sPassword = properties.getProperty("sPassword");
            dbName = properties.getProperty("dbName");
            drName = properties.getProperty("drName");
            url = properties.getProperty("url");

        }catch (Exception e) {
            Message.connectionFailed("Connection Failed", e.getMessage());}
        finally {
            assert inputStream != null;
            inputStream.close();
        }
    }
}
