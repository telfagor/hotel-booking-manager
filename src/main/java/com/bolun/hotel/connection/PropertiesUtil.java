package com.bolun.hotel.connection;

import lombok.experimental.UtilityClass;
import com.bolun.hotel.exception.ConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException ex) {
            throw new ConnectionException("The application.properties file cannot be loaded!", ex);
        }
    }

    public static String getValue(String key) {
        return PROPERTIES.getProperty(key);
    }
}
