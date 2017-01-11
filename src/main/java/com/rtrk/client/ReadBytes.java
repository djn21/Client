package com.rtrk.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * 
 * @author djekanovic
 *	
 * Reading bytes from files in folder
 * and sending to server
 */

public class ReadBytes extends Thread {

	private static final int bufferSize = 65;

	private String serverURL;
	private String sendingPath;
	private String sentPath;

	public ReadBytes(String serverURL, String sendingPath, String sentPath) {
		this.serverURL = serverURL;
		this.sendingPath=sendingPath;
		this.sentPath=sentPath;
	}

	public void run() {
		while (!App.end) {
			// Read file if exists
			File folder = new File(sendingPath);
			File[] files = folder.listFiles();
			if (files.length != 0) {
				for (File file : files) {
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
						fin = new FileInputStream(file);
						Files.copy(fin, new File((sentPath + File.separator + file.getName())).toPath(),
								StandardCopyOption.REPLACE_EXISTING);
						fin.close();
						file.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
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

}
