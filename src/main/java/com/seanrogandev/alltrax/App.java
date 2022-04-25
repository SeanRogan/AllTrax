package com.seanrogandev.alltrax;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class App {
    final private static AuthorizationService auth = new AuthorizationService();


    public static void main( String[] args ) {
        List<Category> categoryList;
        RequestService request = new RequestService(auth.getAccess());
        categoryList = request.getCategories();
        for (Category cat : categoryList) {
            System.out.println(cat.getName());
        }
    }



}
