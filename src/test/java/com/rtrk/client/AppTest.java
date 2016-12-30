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
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	/**
	 * Test connectToServer
	 */
	public void testConnectToServer() {
		String serverURL = "http://localhost:8080/server/server";
		boolean actual = App.connectToServer(serverURL);
		assertEquals(true, actual);
	}

	/**
	 * Test getConfig
	 */
	public void testGetConfig() {
		Map<String, String> config = App.getConfig();
		assertNotNull(config);
	}

	/**
	 * Test ReadBytes
	 */
	public void testReadBytes() {
		ReadBytes read = new ReadBytes("http://localhost:8080/server/server",
				"C:\\Users\\djekanovic\\Desktop\\sendinghttp", "C:\\Users\\djekanovic\\Desktop\\senthttp");
		read.start();
	}

	/**
	 * Test SendBytesHTTP
	 */
	public void testSendBytesHTTP() {
		SendBytesHTTP sendingThread = new SendBytesHTTP("http://localhost:8080/server/server", "someBytes".getBytes());
		sendingThread.start();
	}

}
