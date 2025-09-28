/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;


import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.google.gson.Gson;
import com.example.model.User;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login/github")
public class GithubCallbackServlet extends HttpServlet {
    
     private static final long serialVersionUID = 1L;
     
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
                 //get code from Github
                 String code = request.getParameter("code");
                 System.out.println("[GithubCallbackServlet] invoking >>>> Code = "+code);              
         
                InputStream input = null; 
                Properties properties = new Properties();                       
                
                 //load propertire file
                        try {
                            String appPropertiesLocation = "/WEB-INF/application.properties";
                            input = getServletContext().getResourceAsStream(appPropertiesLocation);   
                            properties.load(input);
                            System.out.println("[GithubCallbackServlet] Found application.properties at web location: appPropertiesLocation = " + appPropertiesLocation);
                            System.out.println("[GithubCallbackServlet] Successfully loaded application.properties: " + properties.toString());
                        } catch (Exception e) {
                            System.err.println("[GithubCallbackServlet] Error accessing file system: " + e.getMessage());  
                            System.out.println("[GithubCallbackServlet] cannot load application properties ===== ");            
                        }
                 
                 OAuth20Service service = new ServiceBuilder(properties.getProperty("clientId"))
                .apiSecret(properties.getProperty("clientSecret"))
                .defaultScope(properties.getProperty("scope"))
                .callback(properties.getProperty("redirectUri"))
                .build(com.github.scribejava.apis.GitHubApi.instance());
                 
                 System.out.println("OAuth20Service load complete :"+service.toString());
              
                  try {
                       OAuth2AccessToken accessToken = service.getAccessToken(code);
                       OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, properties.getProperty("userInfoUri"));
                       service.signRequest(accessToken, oauthRequest);
                       Response oauthResponse = service.execute(oauthRequest);

                       Gson gson = new Gson();
                       User user = gson.fromJson(oauthResponse.getBody(), User.class);

                       request.getSession().setAttribute("user", user);
                       response.sendRedirect(request.getContextPath() + "/views/user.jsp");

                 } catch (InterruptedException | ExecutionException e) {
                       throw new ServletException(e);
                 }//end try  catch               
                 
              
        }        
                
    
}
