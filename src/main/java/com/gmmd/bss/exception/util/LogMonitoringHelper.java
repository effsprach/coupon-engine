/**
 * 
 */
package com.gmmd.bss.exception.util;

import org.apache.log4j.Logger;

/**
 * @author HATHAICHANOK
 * 
 */
public class LogMonitoringHelper {

	private static final String LOG_PACKAGE = "com.gmmd.bss.common.util.LogMessageHelper";
	private static final String LOG_MONITORING_ALERT = "com.gmmd.bss.monitoring.alert";
	private static final Logger log = Logger.getLogger(LOG_PACKAGE);
	private static final Logger alert = Logger.getLogger(LOG_MONITORING_ALERT);
	private static enum ERROR_SERVERITY {AO, AD, CO, CD}
	
	public static void monitoringAlert(int severityCode, String errorMsg) {
		
		String codeMsg = "";
		String msg = null;

		if (severityCode != 1) {

			if (severityCode == 2) {
				codeMsg = ERROR_SERVERITY.AO.name()
						+ String.format("%04d", severityCode);
			} else if (severityCode == 3) {
				codeMsg = ERROR_SERVERITY.AD.name()
						+ String.format("%04d", severityCode);
			} else if (severityCode == 4) {
				codeMsg = ERROR_SERVERITY.CO.name()
						+ String.format("%04d", severityCode);
			} else if (severityCode == 5) {
				codeMsg = ERROR_SERVERITY.CD.name()
						+ String.format("%04d", severityCode);
			}

			msg = formatBSSLog(System.getProperty("inst_name") + " monitoringAlert"
						, "ex", "severityCode", String.valueOf(severityCode), "", codeMsg + " " + errorMsg);
			 
			alert.info(msg);
		} else{
			 msg = formatBSSLog(System.getProperty("inst_name") + " monitoringAlert"
						, "ex", "severityCode", String.valueOf(severityCode), "", errorMsg);
		}
		log.debug(msg);
	}
	
	private static String formatBSSLog(String methodName, String point, String keyLog, String keyValue, String timeSpend, String message ){
		return methodName +"|"+ point +"|"+ keyLog +"|"+ keyValue +"|"+ timeSpend +"|"+ message;
	}
	
}
