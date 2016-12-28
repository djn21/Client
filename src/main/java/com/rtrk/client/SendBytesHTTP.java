package com.rtrk.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SendBytesHTTP extends Thread {

	private String serverURL;
	private byte[] bytes;

	public SendBytesHTTP(String serverURL, byte[] bytes) {
		this.serverURL = serverURL;
		this.bytes = bytes;
	}

	public void run() {
		// Open connection
		HttpURLConnection connection;
		try {
			URL url = new URL(serverURL);
			connection = (HttpURLConnection) url.openConnection();
			// Add reuqest header
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// Send post request
			connection.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.write(bytes);
			out.flush();
			out.close();
			// Response status
			connection.getResponseCode();
			connection.disconnect();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
