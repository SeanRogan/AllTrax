package com.seanrogandev.alltrax;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//todo the auth server should be responsible for getting the refresh token,
// starting a countdown until the the token expires, and refreshing authorization

public class AuthorizationService {
    //slf4j logger
    private PropertiesController propController;
    private static final Logger logger = LoggerFactory.getLogger(PropertiesController.class);
    final private String authUrl = "https://accounts.spotify.com/authorize?client_id=b18942eaca6d48d0909ce9e208562bc0&redirect_uri=http://localhost:8080&response_type=code";
    final private String clientID = "b18942eaca6d48d0909ce9e208562bc0";
    final private String clientSecret = "fdd54982e0b042d8b83696f6f3dc7e96";
    private String authCode = "";
    private String redirectUri = "http://localhost:8080";
    private String accessToken;
    private String refreshToken;
    public int countdown;
    public String serverPath = "https://accounts.spotify.com";

    AuthorizationService(PropertiesController propController) {
       this.propController = propController;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAccess(){
        System.out.println(authUrl);
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/", (HttpExchange exchange) -> {
                String query = exchange.getRequestURI().getQuery();
                String request;
                if(query != null && query.contains("code")) {
                    setAuthCode(query.substring(5));
                    System.out.println("Access code received");
                    request = "Got the code. Return back to your program.";
                } else {
                    request = "Authorization code not found. Try again.";
                }
                exchange.sendResponseHeaders(200, request.length());
                exchange.getResponseBody().write(request.getBytes());
                exchange.getResponseBody().close();
            });
            server.start();
            System.out.println("Waiting for code...");
            while(authCode.equals("")) {
                Thread.sleep(100);
            }
            server.stop(5);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        String authorizationResponse = getToken();
        if(authorizationResponse.contains("access_token")) return authorizationResponse;
        else return null;
    }

    private String getToken() {
        String responseBody = "";
        System.out.println("making http request for access_token...\n" +
                "response:");
        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept" , "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(serverPath + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"
                                + "&code=" + authCode
                                + "&client_id=" + clientID
                                + "&client_secret=" + clientSecret
                                + "&redirect_uri=" + redirectUri))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            System.out.println(response.body());
            System.out.println("---SUCCESS---");
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
        processResponse(responseBody);
        return responseBody;
    }

    private void processResponse(String responseBody) {

            JsonObject j = JsonParser.parseString(responseBody).getAsJsonObject();
            accessToken = j.get("access_token").getAsString();
            refreshToken = j.get("refresh_token").getAsString();
            countdown = j.get("expires_in").getAsInt();

    }

    private void refreshAccess() {

    }





}


