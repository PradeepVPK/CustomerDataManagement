package com.customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//AuthServlet.java
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");

		// Make the authentication API call
		String authApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
		JSONObject requestBody = new JSONObject();
		requestBody.put("login_id", loginId);
		requestBody.put("password", password);

		try {
			URL url = new URL(authApiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			// Turn off SSL certificate verification
			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection) conn).setSSLSocketFactory(SSLUtils.getNoOpSSLSocketFactory());
			}

			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			int statusCode = conn.getResponseCode();
			if (statusCode == 200) {
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
					StringBuilder responseBuilder = new StringBuilder();
					String responseLine;
					while ((responseLine = br.readLine()) != null) {
						responseBuilder.append(responseLine);
					}

					JSONObject responseJson = new JSONObject(responseBuilder.toString());
					String bearerToken = responseJson.getString("access_token");

					// Store the bearer token in the session
					HttpSession session = request.getSession();
					session.setAttribute("bearerToken", bearerToken);

					response.sendRedirect("customer_list.jsp");
				}
			} else {
				// Handle authentication failure
				response.sendRedirect("login.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("login.jsp");
		}
	}
}
