package com.rtrk.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ReadBytesTest extends TestCase {
	
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public ReadBytesTest(String testName) {
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
	public void testReadBytes() {
		assertTrue(true);
	}
	
	/**
	 * Test run method
	 */
    public void testRun() {
        ReadBytes thread = new ReadBytes("http://localhost:8080/server/server", "C:\\Users\\djekanovic\\Desktop\\sendinghttp", "C:\\Users\\djekanovic\\Desktop\\senthttp");
        thread.run();
    }

}
