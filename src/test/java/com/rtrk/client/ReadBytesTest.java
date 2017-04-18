package com.rtrk.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ReadBytesTest extends TestCase {

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ReadBytesTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ReadBytesTest.class);
	}
	
	/**
	 * Test sendFile method
	 */
	public void testSendFile() {
		File file = new File("C:\\Users\\djekanovic\\Desktop\\sendinghttp\\randombytes");
		FileOutputStream fos;
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write("TEST SEND FILE\n".getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new ReadBytes(App.config.get("serveraddress"), App.config.get("filepathsending"), App.config.get("filepathsent")).sendFile(file);
		assertFalse(file.exists());
	}

	/**
	 * Test replaceFile method
	 */
	public void testReplaceFile() {
		File file = new File("C:\\Users\\djekanovic\\Desktop\\senthttp\\randombytes");
		assertTrue(file.exists());
		new ReadBytes().replaceFile(file, new File("C:\\Users\\djekanovic\\Desktop\\sendinghttp\\randombytes").toPath());
		assertFalse(file.exists());
		File filesending = new File("C:\\Users\\djekanovic\\Desktop\\sendinghttp\\randombytes");
		assertTrue(filesending.exists());
	}

}
