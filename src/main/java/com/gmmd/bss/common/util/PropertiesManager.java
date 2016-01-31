/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 * 
 */
package com.gmmd.bss.common.util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.gmmd.bss.exception.BSSException;
import com.gmmd.bss.exception.ConfigureException;

public class PropertiesManager {

	private static PropertiesManager instance = null;
	private Map<String,Properties> propertiesBasket  = new HashMap<String,Properties>();
	//private final String CONFIG_PATH = ConfigurationVariableSystemManager.getVariableSystem("ConfigPath");
	private final String CONFIG_PATH = "";

	//This private constructor manages a single instance to access the properties.
	private PropertiesManager() {
		super();
	}
	
	public static PropertiesManager getInstance(){
		if (instance == null){
			instance = new PropertiesManager();
		}
		return instance;
	}
	
	public Set<String> listPropsInUse(){
		return propertiesBasket.keySet();
	}
	
	/**
	 * @param bssErrorMessage
	 * @return
	 * @throws ConfigureException 
	 */
	public Properties getProperty(PropertiesFileContants propertiesFileContant) throws ConfigureException {
		Properties propMap = null;
		try{
			if((propMap = propertiesBasket.get(propertiesFileContant.getPropertyName())) == null){
				propMap = loadProperties(propertiesFileContant.getPropertyPath());
				synchronized (propertiesBasket){	
					propertiesBasket.put(propertiesFileContant.getPropertyName(), propMap);
				}
			} 
			return propMap;
		}catch (ConfigureException e) {
			throw e;
		}catch (Exception e) {
			throw new ConfigureException(e, "430009", new String[] { propertiesFileContant.getPropertyPath() });		
		}
	}
	
	public Properties loadProperties(String propertyPath) throws ConfigureException{
		Properties propertiesLoad = new Properties();
		try{
			propertiesLoad.load(new FileInputStream(CONFIG_PATH+propertyPath));
		}catch(Exception e){
			throw new ConfigureException(e, "430009", new String[] { propertyPath });		
		}
		return propertiesLoad;
	}
	
	
	/**
	 * @param propertyName 
	 */
	
	public void resetPropertisBasket(String propertyName) throws BSSException{
		try{
			PropertiesFileContants.valueOf(propertyName);
		} catch(IllegalArgumentException e){
			throw new BSSException(e, "810012", new String[]{"PropertyName : "+propertyName});
		}
		synchronized (propertiesBasket){			
			propertiesBasket.remove(propertyName);
		}
	}
		
	public void resetAllPropertiesBasket(){
	   synchronized (propertiesBasket) {
		   propertiesBasket.clear();
	   }
	}
}

