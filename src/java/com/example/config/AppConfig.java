package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getClientId() {
        return properties.getProperty("clientId");
    }

    public static String getClientSecret() {
        return properties.getProperty("clientSecret");
    }

    public static String getAuthorizationUri() {
        return properties.getProperty("authorizationUri");
    }

    public static String getTokenUri() {
        return properties.getProperty("tokenUri");
    }

    public static String getUserInfoUri() {
        return properties.getProperty("userInfoUri");
    }
    
    public static String getScope() {
        return properties.getProperty("scope");
    }

    public static String getRedirectUri() {
        return properties.getProperty("redirectUri");
    }
}
