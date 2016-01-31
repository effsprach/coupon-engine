package com.gmmd.bss.common.util;

import java.util.Properties;

import com.gmmd.bss.exception.ConfigureException;

public class CPNPropertiesHelper {
	
	public static String getGeneratorHandler(String keyCreater) throws ConfigureException{
		
		String classHandler;
		
		if(("").equals(keyCreater) || keyCreater == null){
			return null;
		}
		
		Properties properties = PropertiesManager.getInstance().getProperty(PropertiesFileContants.CPN_LOOKUP_COUPON_GENERATOR);
		classHandler = (String) properties.get(keyCreater);
		return classHandler;
	}

}
