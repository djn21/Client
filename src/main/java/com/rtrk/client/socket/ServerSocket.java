package com.rtrk.client.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

public class ServerSocket extends Thread {

	private static final int TCP_PORT = 9090;
	private static final String filePath = "\\reqsocket";
	private static final int bufferSize = 1024;

	public void run() {
		try {
			System.out.println("ServerSocket started.");
			java.net.ServerSocket serverSocket = new java.net.ServerSocket(TCP_PORT);
			while (!com.rtrk.client.App.end) {
				Socket socket = serverSocket.accept();
				System.out.println("Client accepted");
				DataInputStream in=new DataInputStream(socket.getInputStream());
				DataOutputStream out=new DataOutputStream(socket.getOutputStream());
				// Receive file length
				int fileLength = in.readInt();
				System.out.println("File length: "+fileLength);
				// Receive bytes
				FileOutputStream fout = new FileOutputStream(new File(filePath), true);
				byte[] bytes = new byte[bufferSize];
				int received = 0, count;
				while ((count = in.read(bytes)) > 0) {
					fout.write(bytes, 0, count);
					received += count;
					if (fileLength == received) {
						break;
					}
				}
				// Send response status
				out.writeBoolean(true);
				fout.close();
				in.close();
				socket.close();
			}
			serverSocket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
