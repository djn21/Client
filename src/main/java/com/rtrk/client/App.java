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

import com.rtrk.client.socket.ServerSocket;

/**
 * Hello world!
 *
 */
public class App {

	public static boolean end = false;

	public static void main(String[] args) {
		// Get config
		Map<String, String> config = getConfig();
		String servletAddress=config.get("servletaddress");
		int socketPort=Integer.parseInt(config.get("socketport"));
		String socketFilePath=config.get("filepathsocket");
		String sendingFilePath=config.get("filepathsending");
		String sentFilePath=config.get("filepathsent");
		//Start socket server
		new ServerSocket(socketPort, socketFilePath).start();
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

	public static Map<String, String> getConfig() {
		// Read URL from config file
		Map<String, String> config=new HashMap<String, String>();
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File("config\\config.txt"))));
			String row="";
			while((row=in.readLine())!=null){
				config.put(row.split("#")[0], row.split("#")[1]);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return config;
	}

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
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
