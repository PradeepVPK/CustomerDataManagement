package com.customer;

//SSLUtils.java
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class SSLUtils {
	public static javax.net.ssl.SSLSocketFactory getNoOpSSLSocketFactory()
			throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
			}

			public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} }, new SecureRandom());

		return sslContext.getSocketFactory();
	}
}
