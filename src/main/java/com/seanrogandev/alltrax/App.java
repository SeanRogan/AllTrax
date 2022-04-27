package com.seanrogandev.alltrax;

import java.util.List;
import java.util.Properties;

public class App {
    final private static PropertiesController propControl = new PropertiesController(new Properties());
    final private static AuthorizationService auth = new AuthorizationService(propControl);
    final private static InputController in = new InputController();
    public static void main( String[] args ) {
            //todo build a way to load properties from config file
        {
            propControl.setProperty("client_id", "b18942eaca6d48d0909ce9e208562bc0");
            propControl.setProperty("client_secret" , "fdd54982e0b042d8b83696f6f3dc7e96");
        }
        List<Category> categoryList;
        RequestService request = new RequestService(auth.getAccess());
        request.findArtist(in.takeInput());
    }



}
