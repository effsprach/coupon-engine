/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 * 
 */
package com.gmmd.bss.exception.util;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Properties;


/**
 * Class description :
 * @author prachyawut
 * @version 1.00	 Jun 2, 2013
 */
public class ErrorMessageHelper {
	
	private static Properties properties = new Properties();
//	private static final String CONFIG_PATH = ConfigurationVariableSystemManager.getVariableSystem("ConfigPath");
	private static final String CONFIG_PATH = "";
	private static final String BSS_ERROR_MESSAGE_PATH = CONFIG_PATH + "/resources/bss_error_msg.properties";
	
	
	private static final String DEFAULT_ERROR_CODE = "110001";
	
	private static void load(){
		try{
			synchronized (properties){	
				properties.load(new FileInputStream(BSS_ERROR_MESSAGE_PATH));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static boolean isErrorMsgInUse(){
		return ((properties == null || properties.size() < 1)?false:true);
	}
	
	public static void resetPropErrorMsg(){
		synchronized (properties){			
			properties.clear();
		}
	}

	/**
	 * get BSS error code standard from property
	 * @param message
	 * @param msgParams
	 */
	public static String getErrorMessage(String errorCode, Object[] msgParams){
		String msg = null;
		if(!isErrorMsgInUse()){
			load();
		}
		if(properties.getProperty(errorCode) == null){
			msg = properties.getProperty(DEFAULT_ERROR_CODE);
		} else {
			msg = properties.getProperty(errorCode);
		}
		
		if(msgParams == null){
			msgParams = new String[]{"null"};
		}
		
		return MessageFormat.format(msg, msgParams);
	}
}
