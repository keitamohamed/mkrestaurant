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

    public static String fxmlLocation(String className, String userType) {
        String fxmlLocation = null;
        className = changeMainClassName(className, userType);
        try {
            fxmlLocation = url("FxmlFileURL").getProperty(className);
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

    public static String fxmlLocation(String subClass) {
        String fxmlSubClassUrl = null;
        subClass = changeSubClassName(subClass);
        try {
            fxmlSubClassUrl = url("FxmlFileURL").getProperty(subClass);
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

    public static String cssLocation(String className, String userType) {
        String stylesheetUrl = null;
        className = changeMainClassName(className, userType);
        try {
            stylesheetUrl = url("StylesheetURL").getProperty(className);
        }catch (Exception e) {
            Message.operationFailed("Connection Frillier", e.getMessage());
        }
        finally {
            assert inputStream != null;
            try {
                inputStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return stylesheetUrl;
    }

    public static String cssLocation(String className) {
        String stylesheetUrl = null;
        className = changeSubClassName(className);
        try {
            stylesheetUrl = url("StylesheetURL").getProperty(className);
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
        return stylesheetUrl;
    }

    private static String changeMainClassName(String value, String userType){
        if (value.equals("Main") && !userType.equals("Employee")) {
            return "Login";
        }
        else if ((value.equals("Login") || value.equals("Employee")
                || value.equals("Checkout")) && !userType.equals("Employee")) {
            return "Main";
        }
        else if (value.equals("Sign Out"))
            return "Main";
        return "Employee";
    }

    private static String changeSubClassName(String textValue){
        if (textValue.equals("Main")) {
            return "Checkout";
        }
        else if (textValue.equals("Sign Out"))
            return "Main";
        else if (textValue.equals("Sign up / Register") || textValue.equals("Sign Up"))
            return "Register";
        return "Main";
    }

    private static Properties url(String propertiesFile) throws IOException {
        Properties properties = new Properties();
        String drURL = "config/" + propertiesFile + ".properties";
        inputStream = new FileInputStream(drURL);
        properties.load(inputStream);

        return properties;
    }

}
