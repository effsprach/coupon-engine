/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 * 
 */
package com.gmmd.bss.common.util;

import java.util.Date;

import org.apache.log4j.Logger;

public class LogMessageHelper {

	public static enum POINT {s, e, b, ex}
	public static String LOG_PERFORMANCE = "com.gmmd.bss.performance";
	public static Logger performance = Logger.getLogger(LOG_PERFORMANCE);
	
	public static String formatBSSLog(String methodName, POINT point, String keyLog, String keyValue, String timeSpend, String message ){
		return methodName +"|"+ point +"|"+ keyLog +"|"+ keyValue +"|"+ timeSpend +"|"+ message;
	}
	
	public static String formatBSSPerformanceLog(Date date, String Severity, String appId, String messageId, String groupKey, 
													String method, String version, String timeSpend, String statusCode , String error,String freeText){
		
		return CalendarUtil.convertDateToCOMStr(date)+ "|" + Severity + "|" + appId + "|" + messageId + "|" + groupKey + "|" 
				+ method + "|" + version + "|" + timeSpend + "|" +statusCode+ "|" + error + "|" +freeText;
	}
	
	public static void logPerformance(String appId, String messageId, String groupKey, 
			String method, String version, String timeSpend, String statusCode , String error,String freeText){
		performance.info(formatBSSPerformanceLog(new Date(), "INFO", appId, messageId, groupKey, method, version, timeSpend, statusCode, error, freeText));
	}
}
