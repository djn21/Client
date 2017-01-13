package com.rtrk.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * 
 * @author djekanovic
 * 
 *         Read bytes from file
 */

public class ReadBytes extends Thread {

	private static final int bufferSize = 65;

	private String serverURL;
	private String sendingPath;
	private String sentPath;

	public ReadBytes() {
		serverURL = "http://localhost:8080/server/servera";
		sendingPath = "C:\\Users\\djekanovic\\Desktop\\sendinghttp";
		sentPath = "C:\\Users\\djekanovic\\Desktop\\senthttp";
	}

	public ReadBytes(String serverURL, String sendingPath, String sentPath) {
		this.serverURL = serverURL;
		this.sendingPath = sendingPath;
		this.sentPath = sentPath;
	}

	public void run() {
		while (!App.end) {
			// Read file if exists
			File folder = new File(sendingPath);
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					sendFile(file);
				}
			}
			// Sleep 0.5 seconds
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send file to servlet
	 * 
	 * @param file
	 *            - sending file
	 */
	public void sendFile(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			byte[] buffer = new byte[bufferSize];
			while (fin.read(buffer, 0, buffer.length) != -1) {
				byte[] bytes = new byte[buffer.length];
				System.arraycopy(buffer, 0, bytes, 0, bytes.length);
				new SendBytesHTTP(serverURL, bytes).start();
			}
			System.out.println("File sent HTTP");
			fin.close();
			// Copy file to sent folder
			replaceFile(file, new File((sentPath + File.separator + file.getName())).toPath());
		} catch (IOException e) {
		}
	}

	/**
	 * Replace file from sending to sent folder
	 * 
	 * @param file
	 *            - sending file
	 * @param path
	 *            - path of sent folder
	 */
	public void replaceFile(File file, Path path) {
		try {
			FileInputStream fin = new FileInputStream(file);
			Files.copy(fin, path, StandardCopyOption.REPLACE_EXISTING);
			fin.close();
			file.delete();
		} catch (Exception e) {
		}
	}

}
