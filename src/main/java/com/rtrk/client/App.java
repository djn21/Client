package com.rtrk.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.rtrk.client.socket.ServerSocket;

/**
 * Hello world!
 *
 */
public class App {

	public static boolean end = false;

	public static void main(String[] args) {
		// Start server socket
		new ServerSocket().start();
		// Get and test connection
		String serverURL = connectToServer();
		// Send bytes
		if (serverURL != null) {
			new ReadBytes(serverURL).start();
			System.out.println("Connected to server.");
		} else {
			System.out.println("Can't connect to server.");
		}
	}

	private static String connectToServer() {
		// Read URL from config file
		String serverURL = "";
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File("config\\ServerURL.txt"))));
			serverURL = in.readLine();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Connecting to srever
		try {
			URL url = new URL(serverURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				connection.disconnect();
				return serverURL;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
