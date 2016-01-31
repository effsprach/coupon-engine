package com.gmmd.bss.common.util;

import java.text.MessageFormat;
import java.util.Properties;

import com.gmmd.bss.exception.ConfigureException;

public abstract class CommonPropertiesHelper {
	
	// COMMON
	protected static String getMsg(Properties properties, String keyName,
			Object[] msgParams) throws ConfigureException {
		String msg = properties.getProperty(keyName);
		if (msg != null) {
			return formatMsg(msg, msgParams);
		} else {
			throw new ConfigureException("410008", new String[] { keyName });
		}
	}

	protected static String formatMsg(String msg, Object[] msgParams) {
		if (msgParams == null) {
			msgParams = new String[] { "null" };
		}
		return MessageFormat.format(msg, msgParams);
	}
}
