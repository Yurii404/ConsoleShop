package ua.issoft.yurii.kupchyn.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {


    public String getPropertyValue(String propertyName) {
        Properties properties = new Properties();

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);

            return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new FailedLoadPropertiesFileException("Failed load properties file", e);
        }

    }
}
