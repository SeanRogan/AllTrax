package com.seanrogandev.alltrax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertiesManager {
    private final Properties prop;
    private final String propertiesFilePath = "src/main/resources/config.properties";
    private static final Logger logger = LoggerFactory.getLogger(PropertiesManager.class);
    PropertiesManager(Properties prop) {
        this.prop = prop;
    }
    public String getPropertiesFilePath() {
        return propertiesFilePath;
    }

    public void setProperty(String key, String value) {
        try (OutputStream output = new FileOutputStream(getPropertiesFilePath())) {

            // set the properties value
            prop.setProperty(key, value);
            // save properties to project root folder
            prop.store(output, null);
        } catch (
                IOException io) {
            io.printStackTrace();
        }
    }

    public String loadProperties(String key) {
        logger.trace("attempting to load properties from config file");

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream(getPropertiesFilePath());
            Properties prop = new Properties();
            if(in != null) {
                prop.load(in);
                return prop.get(key).toString();
            }
        } catch (IOException e) {
            logger.warn("An IOException occurred while attempting to load config.properties");
            e.printStackTrace();
        }
        return null;
    }
}
