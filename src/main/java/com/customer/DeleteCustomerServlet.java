package com.customer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//DeleteCustomerServlet.java
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String bearerToken = (String) session.getAttribute("bearerToken");
		if (bearerToken == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		// Get the UUID of the customer to delete from the request parameter
		String uuid = request.getParameter("uuid");

		// Make the API call to delete the customer
		String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";
		apiUrl += "?cmd=delete&uuid=" + uuid;

		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + bearerToken);

			// Turn off SSL certificate verification if needed
			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection) conn).setSSLSocketFactory(SSLUtils.getNoOpSSLSocketFactory());
			}

			int statusCode = conn.getResponseCode();
			if (statusCode == 200) {
				// Successfully deleted
				response.sendRedirect("customer_list.jsp");
			} else {
				// Error while deleting
				response.sendRedirect("error.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}
}
