package com.rtrk.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * 
 * Read bytes from file
 * 
 * @author djekanovic
 * 
 */

public class ReadBytes extends Thread {

	private static final int bufferSize = 1024; // 1 kB

	private String serverURL;
	private String sendingPath;
	private String sentPath;

	public ReadBytes() {
		this.serverURL = "";
		this.sendingPath = "";
		this.sentPath = "";
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
			try {
				// Wait 100 ms
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * Send file to servlet
	 * 
	 * @param file
	 *            Sending file
	 * 
	 */
	public void sendFile(File file) {
		try {

			// Read file and send to server
			FileInputStream fin = new FileInputStream(file);
			byte[] buffer = new byte[bufferSize];
			int readed = 0;
			while ((readed = fin.read(buffer, 0, buffer.length)) != -1) {
				byte[] bytes = new byte[readed];
				System.arraycopy(buffer, 0, bytes, 0, bytes.length);
				new SendBytesHTTP(serverURL, bytes).send();
			}
			System.out.println("File sent HTTP");
			fin.close();

			// Copy file to sent folder
			replaceFile(file, new File((sentPath + File.separator + file.getName())).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Replace file to destination defined in path
	 * 
	 * @param file
	 *            Sending file
	 * @param path
	 *            Path of sent folder
	 * 
	 */
	public void replaceFile(File file, Path path) {
		try {
			FileInputStream fin = new FileInputStream(file);
			Files.copy(fin, path, StandardCopyOption.REPLACE_EXISTING);
			fin.close();
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
