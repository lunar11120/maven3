/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.test;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Theam
 */
public class TestAppConfig {
    public static void main(String[] args) {
        
        System.out.println("Test App start 10001 ------------");
        System.out.println("Loading application.properties...");
        Properties properties = new Properties();
        InputStream input = null;
        boolean foundFile = false;    
        
        // Method 1: Try classpath locations
        String[] classpathLocations = {
            "conf/application.properties"

        };        
              
        
    }
    

    
}
