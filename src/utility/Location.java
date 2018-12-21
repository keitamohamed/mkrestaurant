package utility;

import dbconnection.Interface;
import message.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class Location implements Interface {
    private static InputStream inputStream;
    @Override
    public Connection mySQL() {
        return null;
    }

    @Override
    public void injectPropertiesValue() {

    }

    public static String location(String className, boolean value) {
        String fxmlLocation = null;
        className = changeMainClassName(className, value);
        try {
            fxmlLocation = url().getProperty(className);
        }catch (Exception e) {
            Message.operationFailed("Connection Failed", e.getMessage());
        }
        finally {
            assert inputStream != null;
            try {
                inputStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return fxmlLocation;
    }

    public static String location(String subClass) {
        String fxmlSubClassUrl = null;
        subClass = changeSubClassName(subClass);
        try {
            fxmlSubClassUrl = url().getProperty(subClass);
        }catch (Exception e) {
            Message.operationFailed("", e.getMessage());
        }
        finally {
            assert inputStream != null;
            try {
                inputStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return fxmlSubClassUrl;
    }

    private static String changeMainClassName(String className, boolean value){
        if (className.equals("Main") && !value) {
            return "Login";
        }
        else if ((className.equals("Login") || className.equals("Admin")
                || className.equals("Checkout")) && !value) {
            return "Main";
        }
        return "Admin";
    }

    private static String changeSubClassName(String className){
        if (className.equals("Main")) {
            return "Checkout";
        }
        return "Main";
    }

    private static Properties url() throws IOException {
        Properties properties = new Properties();
        String drURL = "config/ClassFxmlURL.properties";
        inputStream = new FileInputStream(drURL);
        properties.load(inputStream);

        return properties;
    }

}
