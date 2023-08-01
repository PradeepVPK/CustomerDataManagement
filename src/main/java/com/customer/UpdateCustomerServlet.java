package com.customer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String bearerToken = (String) session.getAttribute("bearerToken");
		if (bearerToken == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		// Get the form data for updating the customer
		String uuid = request.getParameter("uuid");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String street = request.getParameter("street");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		// Make the API call to update the customer
		String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid=" + uuid;

		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + bearerToken);
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setDoOutput(true);

			// Construct the JSON request body
			String jsonRequestBody = "{\"first_name\":\"" + firstName + "\",\"last_name\":\"" + lastName
					+ "\",\"street\":\"" + street + "\",\"address\":\"" + address + "\",\"city\":\"" + city
					+ "\",\"state\":\"" + state + "\",\"email\":\"" + email + "\",\"phone\":\"" + phone + "\"}";
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = jsonRequestBody.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			// Turn off SSL certificate verification if needed
			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection) conn).setSSLSocketFactory(SSLUtils.getNoOpSSLSocketFactory());
			}

			int statusCode = conn.getResponseCode();
			if (statusCode == 200) {
				// Successfully updated
				response.sendRedirect("customer_list.jsp");
			} else {
				// Error while updating
				response.sendRedirect("error.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}
}
