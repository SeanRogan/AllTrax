package com.seanrogandev.alltrax;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class App
{
    public static void main( String[] args ) {

    }

    public static void setProperty(String key, String value) {
        try (
                OutputStream output = new FileOutputStream("src/main/resources/config.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty(key, value);
            // save properties to project root folder
            prop.store(output, null);
        } catch (
                IOException io) {
            io.printStackTrace();
        }
    }


}
