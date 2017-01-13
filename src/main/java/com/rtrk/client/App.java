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
import java.util.HashMap;
import java.util.Map;

//import com.rtrk.client.socket.ServerSocket;

/**
 * Hello world!
 *
 */
public class App {

	public static boolean end = false;

	public static void main(String[] args) {
		// Get config
		Map<String, String> config = getConfig("config\\config.txt");
		String servletAddress = config.get("servletaddress");
		String sendingFilePath = config.get("filepathsending");
		String sentFilePath = config.get("filepathsent");
		// Test connection
		boolean connected = connectToServer(servletAddress);
		if (connected) {
			// Send bytes
			new ReadBytes(servletAddress, sendingFilePath, sentFilePath).start();
			System.out.println("Connected to HTTP Server.");
		} else {
			System.out.println("Can't connect HTTP to Server.");
		}
	}

	/**
	 * Read config (Servlet address, Sending file path, Sent file path) from
	 * conig file <config/config.txt>
	 * 
	 * @return Map<String, String> if config file exists or null if config file
	 *         doesn't exist or empty
	 */
	public static Map<String, String> getConfig(String filePath) {
		// Read URL from config file
		Map<String, String> config = new HashMap<String, String>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
			String row = "";
			while ((row = in.readLine()) != null) {
				config.put(row.split("#")[0], row.split("#")[1]);
			}
			in.close();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		if (config.size() == 0) {
			return null;
		}
		return config;
	}

	/**
	 * Testing connection to server
	 * 
	 * @param serverURL
	 *            URL of server
	 * @return true if connection established and response code is 200 OK or
	 *         false if can't connect to server or bad response code
	 */
	public static boolean connectToServer(String serverURL) {
		// Connecting to srever
		try {
			URL url = new URL(serverURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				connection.disconnect();
				return true;
			}
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return false;
	}

}
