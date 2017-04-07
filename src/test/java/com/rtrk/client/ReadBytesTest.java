package com.rtrk.client;

import java.io.File;

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
		File file = new File("C:\\Users\\djekanovic\\Desktop\\randombytes");
		new ReadBytes().sendFile(file);
		assertFalse(file.exists());
		file = new File("noFile");
		new ReadBytes().sendFile(file);
		assertFalse(file.exists());
	}

	/**
	 * Test replaceFile method
	 */
	public void testReplaceFile() {
		File file = new File("C:\\Users\\djekanovic\\Desktop\\sendinghttp\\randombytes");
		assertTrue(file.exists());
		new ReadBytes().replaceFile(file, new File("C:\\Users\\djekanovic\\Desktop\\senthttp\\randombytes").toPath());
		File filesending = new File("C:\\Users\\djekanovic\\Desktop\\sendinghttp\\randombytes");
		assertFalse(filesending.exists());
		File filesent = new File("C:\\Users\\djekanovic\\Desktop\\senthttp\\randombytes");
		assertTrue(filesent.exists());
	}

}
