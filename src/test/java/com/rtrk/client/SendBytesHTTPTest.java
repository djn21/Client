package com.rtrk.client;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SendBytesHTTPTest extends TestCase {

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public SendBytesHTTPTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(SendBytesHTTPTest.class);
	}
	
	public void testSend(){
		File filebefore=new File("C:\\Users\\djekanovic\\Desktop\\requestshttp");
		long sizebefore=filebefore.length();
		SendBytesHTTP send=new SendBytesHTTP(App.config.get("serveraddress"), "TEST SEND BYTES\n".getBytes());
		
		send.send();
		
		File fileafter=new File("C:\\Users\\djekanovic\\Desktop\\requestshttp");
		long sizeafter=fileafter.length();
		
		assertEquals(sizebefore, sizeafter-"TEST SEND BYTES\n".length());
	}

}
