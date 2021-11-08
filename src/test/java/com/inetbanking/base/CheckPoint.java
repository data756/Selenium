package com.inetbanking.base;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class CheckPoint {

	public static HashMap<String, String> resultMap = new HashMap<String, String>();
	private static String PASS = "PASS";
	private static String FAIL = "FAIL";
	private static final Logger log= LogManager.getLogger(CheckPoint.class.getName());
	/*
	 * Clears the previous saved result of HashMap.
	 */

	public static void clearHashMap() {
		log.info("Clearning Hash Map");
		resultMap.clear();
	}

	/*
	 * set the HashMap value with results.
	 * 
	 * @param mapKey
	 * 
	 * @param Status
	 */
	private static void setStatus(String mapKey, String Status) {
		resultMap.put(mapKey, Status);
		log.info(mapKey + " :-> " + resultMap.get(mapKey));
	}

	public static void mark(String testName, boolean result, String resultMessage) {
		testName = testName.toLowerCase();
		String MapKey = testName + " . " + resultMessage;
		try {
			if (result) {
				setStatus(MapKey, PASS);
			} else {
				setStatus(MapKey, FAIL);
			}
		} catch (Exception e) {
			log.error("Exception occured");
			setStatus(MapKey, FAIL);
			e.printStackTrace();
		}
	}

	public static void markFinal(String testName, boolean result, String resultMessage) {
		testName = testName.toLowerCase();
		String MapKey = testName + " . " + resultMessage;
		try {
			if (result) {
				setStatus(MapKey, PASS);
			} else {
				setStatus(MapKey, FAIL);
			}
		} catch (Exception e) {
			log.error("Exception occured");
			setStatus(MapKey, FAIL);
			e.printStackTrace();
		}
		ArrayList<String> resultList = new ArrayList<String>();

		for (String key : resultMap.keySet()) {
			resultList.add(resultMap.get(key));
		}
		if (resultList.contains(FAIL)) {
			log.info("Test Failed");
			Assert.assertTrue(false);
		} else {
			log.info("Test Successful");
			Assert.assertTrue(true);
		}
	}

}
