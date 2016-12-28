package com.rtrk.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ReadBytes extends Thread {

	private static final int bufferSize = 65;
	private static final String filesPath = "sending";
	private static final String sentFilesPath = "sent";

	private String serverURL;

	public ReadBytes(String serverURL) {
		this.serverURL = serverURL;
	}

	public void run() {
		while (!App.end) {
			// Read file if exists
			File folder = new File(filesPath);
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
						fin.close();
						// Copy file to sent folder
						fin = new FileInputStream(file);
						Files.copy(fin, new File((sentFilesPath + File.separator + file.getName())).toPath(),
								StandardCopyOption.REPLACE_EXISTING);
						fin.close();
						file.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// Sleep 0.5 second
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
