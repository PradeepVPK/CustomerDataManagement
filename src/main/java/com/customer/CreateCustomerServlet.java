package com.customer;
//CreateCustomerServlet.java
import javax.servlet.http.*;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;


@WebServlet("/CreateCustomerServlet")
public class CreateCustomerServlet extends HttpServlet {
 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     HttpSession session = request.getSession();
     String bearerToken = (String) session.getAttribute("bearerToken");
     if (bearerToken == null) {
         response.sendRedirect("login.jsp");
         return;
     }

     String firstName = request.getParameter("first_name");
     String lastName = request.getParameter("last_name");
     String street = request.getParameter("street");
     String address = request.getParameter("address");
     String city = request.getParameter("city");
     String state = request.getParameter("state");
     String email = request.getParameter("email");
     String phone = request.getParameter("phone");

     // Validate mandatory parameters (first_name and last_name)
     if (firstName.isEmpty() || lastName.isEmpty()) {
         response.sendRedirect("error.jsp");
         return;
     }

     // Make the API call to create a new customer
     String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";

     try {
         URL url = new URL(apiUrl);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("POST");
         conn.setRequestProperty("Authorization", "Bearer " + bearerToken);
         conn.setRequestProperty("Content-Type", "application/json");
         conn.setDoOutput(true);

         // Turn off SSL certificate verification
         if (conn instanceof HttpsURLConnection) {
             ((HttpsURLConnection) conn).setSSLSocketFactory(SSLUtils.getNoOpSSLSocketFactory());
         }

         JSONObject requestBody = new JSONObject();
         requestBody.put("first_name", firstName);
         requestBody.put("last_name", lastName);
         requestBody.put("street", street);
         requestBody.put("address", address);
         requestBody.put("city", city);
         requestBody.put("state", state);
         requestBody.put("email", email);
         requestBody.put("phone", phone);

         try (OutputStream os = conn.getOutputStream()) {
             byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
             os.write(input, 0, input.length);
         }

         int statusCode = conn.getResponseCode();
         if (statusCode == 201) {
             // Customer created successfully
        	 
             response.sendRedirect("customer_list.jsp");
         } else {
             // Handle API error
             response.sendRedirect("error.jsp");
         }
     } catch (Exception e) {
         e.printStackTrace();
         response.sendRedirect("error.jsp");
     }
 }
}
