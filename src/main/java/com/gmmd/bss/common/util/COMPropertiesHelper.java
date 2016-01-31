/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 */
package com.gmmd.bss.common.util;

import java.util.Properties;

import com.gmmd.bss.exception.ConfigureException;

/**
 * @author prachyawut
 * @version 1.00 
 */
public class COMPropertiesHelper extends CommonPropertiesHelper {

	public static String getBusinessBatchConfig(String keyName) throws ConfigureException{		
		Properties properties = PropertiesManager.getInstance().getProperty(PropertiesFileContants.CPN_BUSINESS_BATCH_CONFIG);
        String batchConfig = properties.getProperty("com.gmmd.bss.cpn.bs.["+keyName+"]");                

        return batchConfig;
	}

}
