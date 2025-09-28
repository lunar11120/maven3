# maven2 Java Web Application

This project is a Java web application . It follows the Model-View-Controller (MVC) pattern, uses Java 1.8, and demonstrates OAuth2 login with Github.

## Features
- Java 1.8
- MVC architecture
- POJO for login example
- OAuth2 login with Github
- User info displayed after successful login
- List of libraries used : Location reference (lib directory)

## OAuth2 Configuration
- Authorization URL: https://github.com/login/oauth/authorize
- Redirect URI: http://localhost:8080/maven3/login/github
- Client ID: Ov23lir1F4lZqXkEOVIs
- Client Secret: 26ba121f070c3abe1b17ae33aa5f81704e94f5cc

## How to Run
1. Build the project with Maven: `mvn clean package`
2. Deploy the generated WAR file to a servlet container (e.g., Tomcat)
3. Access the application at http://localhost:8080/maven3

---

This README will be updated as the project is scaffolded and implemented.

