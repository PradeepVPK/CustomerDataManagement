<%@ page import="java.io.*, java.net.*, java.nio.charset.*, org.json.*, javax.net.ssl.HttpsURLConnection" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.customer.Customer, com.customer.SSLUtils" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer List</title>
    <!-- Include Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2 class="text-center">Customer List</h2>
        <button class="btn btn-primary" onclick="window.location.href = 'create_customer.jsp'">Add Customer</button>
        <br><br><table class="table table-bordered">
            <thead>
                <tr class="text-center">
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Street</th>
                    <th>Address</th>
                    <th>City</th>
                    <th>State</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th colspan="2" class="text-center">Actions</th>
                </tr>
            </thead>
            <tbody>
                <% String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
                    URL url = new URL(apiUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", "Bearer " + session.getAttribute("bearerToken"));

                    // Turn off SSL certificate verification
                    if (conn instanceof HttpsURLConnection) {
                        ((HttpsURLConnection) conn).setSSLSocketFactory(SSLUtils.getNoOpSSLSocketFactory());
                    }

                    int statusCode = conn.getResponseCode();
                    if (statusCode == 200) {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                            StringBuilder responseBuilder = new StringBuilder();
                            String responseLine;
                            while ((responseLine = br.readLine()) != null) {
                                responseBuilder.append(responseLine);
                            }

                            JSONArray customers = new JSONArray(responseBuilder.toString());
                            for (int i = 0; i < customers.length(); i++) {
                                JSONObject customer = customers.getJSONObject(i);
                %>
                <tr>
                    <td><%= customer.getString("first_name") %></td>
                    <td><%= customer.getString("last_name") %></td>
                    <td><%= customer.getString("street") %></td>
                    <td><%= customer.getString("address") %></td>
                    <td><%= customer.optString("city") %></td>
                    <td><%= customer.optString("state") %></td>
                    <td><%= customer.optString("email") %></td>
                    <td><%= customer.optString("phone") %></td>
                    <td>
                        <form action="update_customer.jsp" method="post">
                            <% request.setAttribute("customer", customer); %>
                            <input type="hidden" name="uuid" value="<%= customer.getString("uuid") %>">
                            <button class="btn btn-outline-info" type="submit">Update</button>
                        </form><br>
                        
                    </td>
                    <td><form action="DeleteCustomerServlet" method="post">
                            <input type="hidden" name="uuid" value="<%= customer.getString("uuid") %>">
                            <button class="btn btn-outline-danger" type="submit">Delete</button>
                        </form></td>
                </tr>
             <% }}} %>
            </tbody>
        </table>
    </div>

    <!-- Include Bootstrap JS and jQuery -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
