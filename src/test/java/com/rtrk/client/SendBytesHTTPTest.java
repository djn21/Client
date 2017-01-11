package com.rtrk.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SendBytesHTTPTest extends TestCase{

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public SendBytesHTTPTest(String testName) {
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
	public void testSendBytesHTTP() {
		assertTrue(true);
	}
	
	/**
	 * Test run method
	 */
    public void testRun() {
        SendBytesHTTP thread = new SendBytesHTTP("http://localhost:8080/server/server", "someBytes".getBytes());
        thread.start();
        assertEquals(thread.getState(), Thread.State.RUNNABLE);
    }
	
}
