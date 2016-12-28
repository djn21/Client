package com.rtrk.client.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSocket extends Thread {

	private static final int TCP_PORT = 9090;
	private static final String filePath = ".\\reqsocket";
	private static final int bufferSize = 65;

	public void run() {
		try {
			System.out.println("ServerSocket started.");
			java.net.ServerSocket serverSocket = new java.net.ServerSocket(TCP_PORT);
			while (!com.rtrk.client.App.end) {
				Socket socket = serverSocket.accept();
				System.out.println("Client accepted");
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
						true);
				// Receive file length
				int fileLength = Integer.parseInt(in.readLine());
				// Receive bytes
				InputStream inBytes = socket.getInputStream();
				FileOutputStream fout = new FileOutputStream(new File(filePath));
				byte[] bytes = new byte[bufferSize];
				int received = 0, count;
				while ((count = inBytes.read(bytes)) > 0) {
					fout.write(bytes, 0, count);
					received += count;
					if (fileLength == received) {
						break;
					}
				}
				// Send response status
				out.println("OK");
				fout.close();
				inBytes.close();
				in.close();
				out.close();
				socket.close();
			}
			serverSocket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
