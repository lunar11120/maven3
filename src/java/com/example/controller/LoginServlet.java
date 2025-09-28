package com.example.controller;

import com.example.config.AppConfig;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Loading application.properties...");
        Properties properties = new Properties();
        InputStream input = null;
        boolean foundFile = false;
        
        
        if (!foundFile) {
         String appPropertiesLocation = "/WEB-INF/application.properties";
         input = getServletContext().getResourceAsStream(appPropertiesLocation);
         foundFile = true;
         System.out.println("Found application.properties at web location: appPropertiesLocation = " + appPropertiesLocation);
        }

        
        
        // Method 3: Try loading from file system (as fallback)
        if (!foundFile) {
            System.out.println("Web resource search failed. Trying file system...");
            try {
                String realPath = getServletContext().getRealPath("/");
                System.out.println("Web application real path: " + realPath);
                
                // Try loading from various file system locations
                java.io.File[] possibleFiles = {
                    new java.io.File(realPath, "application.properties"),
                    new java.io.File(realPath, "WEB-INF/application.properties"),
                    new java.io.File(realPath, "WEB-INF/classes/application.properties"),
                    new java.io.File(realPath, "../src/application.properties"),
                    new java.io.File(realPath, "../src/conf/application.properties")
                };
                
                for (java.io.File file : possibleFiles) {
                    System.out.println("Trying file: " + file.getAbsolutePath());
                    if (file.exists() && file.canRead()) {
                        System.out.println("Found application.properties at: " + file.getAbsolutePath());
                        input = new java.io.FileInputStream(file);
                        foundFile = true;
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Error accessing file system: " + e.getMessage());
            }
        }
        
        try {
            if (!foundFile || input == null) {
                System.err.println("Error: application.properties not found in any location");
                // Print debug information
                System.err.println("Debug information:");
                System.err.println("ServletContext real path: " + getServletContext().getRealPath("/"));
                System.err.println("Context path: " + getServletContext().getContextPath());
                
                // For now, use hardcoded values as fallback
                System.out.println("Using fallback hardcoded configuration");
                properties.setProperty("clientId", "Ov23lir1F4lZqXkEOVIs");
                properties.setProperty("redirectUri", "http://localhost:8080/maven3/login/github");
                properties.setProperty("scope", "user:email");
                properties.setProperty("authorizationUri", "https://github.com/login/oauth/authorize");
            } else {
                properties.load(input);
                System.out.println("Successfully loaded application.properties: " + properties.toString());
            }
            
            // Get configuration values from properties
            String clientId = properties.getProperty("clientId");
            String redirectUri = properties.getProperty("redirectUri");
            String scope = properties.getProperty("scope", "user:email"); // default fallback
            String authorizationUri = properties.getProperty("authorizationUri"); // default fallback
            
            
            if (clientId == null || redirectUri == null || authorizationUri == null) {
                System.err.println("Error: Missing required properties (clientId, redirectUri, or authorizationUri)");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Missing configuration values");
                return;
            }
            
            String authUrl = authorizationUri +
                    "?client_id=" + clientId +
                    "&redirect_uri=" + redirectUri +
                    "&response_type=code" +
                    "&scope=" + scope +
                    "&access_type=online";
            
            System.out.println("AuthURL = "+authUrl);
                    
            response.sendRedirect(authUrl);
            
        } catch (IOException e) {
            System.err.println("Error loading application.properties: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Configuration error");
            throw new ServletException("Failed to load configuration", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println("Error closing input stream: " + e.getMessage());
                }
            }
        }
    }
}
