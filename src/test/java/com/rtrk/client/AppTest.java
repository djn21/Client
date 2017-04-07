package com.rtrk.client;

import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Test connectToServer
	 */
	public void testConnectToServer() {
		// Normal connection
		String serverURL = "http://localhost:8080/server/server";
		boolean actual = App.connectToServer(serverURL);
		assertEquals(true, actual);
		// Bad URL
		serverURL = "";
		actual = App.connectToServer(serverURL);
		assertEquals(false, actual);
		// Different response code
		serverURL = "http://localhost:8080/server/responsecode";
		actual = App.connectToServer(serverURL);
		assertEquals(false, actual);
	}

	/**
	 * Test getConfig
	 */
	public void testGetConfig() {
		String filePath = "config\\config.cfg";
		Map<String, String> config = App.getConfig(filePath);
		assertNotNull(config);
		filePath = "config\\emptyconfig.cfg";
		config = App.getConfig(filePath);
		assertNull(config);
		filePath = "fileNotFound";
		config = App.getConfig(filePath);
		assertNull(config);
	}

}
