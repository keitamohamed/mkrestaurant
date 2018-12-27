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

    public static String fxmlLocation(String className, String userOrClassType) {
        String fxmlLocation = null;
        className = changeMainClassName(className, userOrClassType);
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

    private static String changeMainClassName(String value, String valueTwo){
        if (value.equals("Main") && !valueTwo.equals("Employee")) {
            return "Login";
        }
        else if ((value.equals("Login") || value.equals("Employee")
                || value.equals("Checkout")) && !valueTwo.equals("Employee")) {
            return "Main";
        }
        else if (value.equals("Sign Out"))
            return "Main";
        return "Employee";
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

    private static String changeSubClassName(String textValue){
        switch (textValue) {
            case "Main":
                return "Checkout";
            case "Sign Out":
                return "Main";
            case "Sign up / Register":
            case "Sign Up":
                return "Register";
            case "Account":
                return "Account";
        }
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
