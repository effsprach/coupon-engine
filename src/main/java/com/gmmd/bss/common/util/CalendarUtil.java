/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 * 
 */
package com.gmmd.bss.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {

	private static final String DATETIME_FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat COM_SDF_DATETIME = new SimpleDateFormat(DATETIME_FORMAT_YYYY_MM_DD_HHMMSS, Locale.US);
	private static final SimpleDateFormat OFM_SDF_DATETIME = new SimpleDateFormat(DATETIME_FORMAT_YYYY_MM_DD_HHMMSS, Locale.US);
	
	private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	private static final SimpleDateFormat COM_SDF = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.US);	
	private static final SimpleDateFormat OFM_SDF = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.US);
	private static final SimpleDateFormat AMI_SDF = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.US);
	
	private static final String DATE_FORMAT_MILLISECONDS = "yyyyMMddHHmmssSSS";
	private static final SimpleDateFormat DF_DFMILLISEC = new SimpleDateFormat(DATE_FORMAT_MILLISECONDS, Locale.US);
	
	private static final String DATE_FORMAT_DD_MM_YYYYY = "dd/MM/yyyy";
	private static final SimpleDateFormat GMMZ_SDF = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYYY, Locale.US);	
	
	private static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	private static final SimpleDateFormat GMMZ_EAI_SDF = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD, Locale.US);	
	
	private static final String DATE_FORMAT_DD_MM_YY = "dd/MM/yy";
	private static final SimpleDateFormat GMMZ_SMS_SDF = new SimpleDateFormat(DATE_FORMAT_DD_MM_YY, Locale.US);	
    //Option : DateTime
	
	private static final String DATE_FORMAT_DD_MM_YY_HH_MM = "dd/MM/yy HH:mm";
	private static final SimpleDateFormat TKT_SMS_SDF = new SimpleDateFormat(DATE_FORMAT_DD_MM_YY_HH_MM, Locale.getDefault());	
	
	public static synchronized Calendar convertCOMStrToCalendarDateTime(String strDate) throws ParseException{
    	Calendar cal = Calendar.getInstance(Locale.US);
    	Date date = convertCOMStrToDateTime(strDate);
    	cal.setTime(date);
    	return cal;
    }
    
    public static synchronized Date convertCOMStrToDateTime(String strDate) throws ParseException{
		try {
			Date date = COM_SDF_DATETIME.parse(strDate);
			return date;
		} catch (ParseException e) {
			throw e;
		}    	
    }    
    
    public static synchronized String convertCalDateTimeToCOMStr(Calendar cal){
    	if(cal == null)
    		return null;
        return COM_SDF_DATETIME.format(cal.getTime());
    }

    public static synchronized String convertDateTimeToCOMStr(Date date){
    	if(date == null)
    		return null;
        return COM_SDF_DATETIME.format(date);
    }
    
    //----------------------------
    public static synchronized Date convertTKTStrToDateTime(String strDate) throws ParseException{
		try {
			Date date = TKT_SMS_SDF.parse(strDate);
			return date;
		} catch (ParseException e) {
			throw e;
		}    	
    }    
    
    public static synchronized String convertTKTDateTimeToTKTStr(Calendar cal){
    	if(cal == null)
    		return null;
        return TKT_SMS_SDF.format(cal.getTime());
    }

    public static synchronized String convertDateTimeToTKTStr(Date date){
    	if(date == null)
    		return null;
        return TKT_SMS_SDF.format(date);
    } 
    
    
    public static synchronized Calendar convertOFMStrToCalendarDateTime(String strDate) throws ParseException{
    	Calendar cal = Calendar.getInstance(Locale.US);
    	Date date = convertOFMStrToDateTime(strDate);
    	cal.setTime(date);
    	return cal;
    }
    
    public static synchronized Date convertOFMStrToDateTime(String strDate) throws ParseException{
    	try {
    		Date date = OFM_SDF_DATETIME.parse(strDate);
    		return date;
    	} catch (ParseException e) {
    		throw e;
    	}    	
    }    
    
    public static synchronized String convertCalDateTimeToOFMStr(Calendar cal){
    	if(cal == null)
    		return null;
    	return OFM_SDF_DATETIME.format(cal.getTime());
    }
    
    public static synchronized String convertDateTimeToOFMStr(Date date){
    	if(date == null)
    		return null;
    	return OFM_SDF_DATETIME.format(date);
    }
    
    //Option : Date
    
	public static synchronized Calendar convertCOMStrToCalendar(String strDate) throws ParseException{
    	Calendar cal = Calendar.getInstance(Locale.US);
    	Date date = convertCOMStrToDate(strDate);
    	cal.setTime(date);
    	return cal;
    }
    
    public static synchronized Date convertCOMStrToDate(String strDate) throws ParseException{
		try {
			Date date = COM_SDF.parse(strDate);
			return date;
		} catch (ParseException e) {
			throw e;
		}    	
    }    
    
    public static synchronized String convertCalToCOMStr(Calendar cal){
    	if(cal == null)
    		return null;
        return COM_SDF.format(cal.getTime());
    }

    public static synchronized String convertDateToCOMStr(Date date){
    	if(date == null)
    		return null;
        return COM_SDF.format(date);
    }
    
    public static synchronized Calendar convertOFMStrToCalendar(String strDate) throws ParseException{
    	Calendar cal = Calendar.getInstance(Locale.US);
    	Date date = convertOFMStrToDate(strDate);
    	cal.setTime(date);
    	return cal;
    }
    
    public static synchronized Date convertOFMStrToDate(String strDate) throws ParseException{
    	try {
    		Date date = OFM_SDF.parse(strDate);
    		return date;
    	} catch (ParseException e) {
    		throw e;
    	}    	
    }    
    
    public static synchronized String convertCalToOFMStr(Calendar cal){
    	if(cal == null)
    		return null;
    	return OFM_SDF.format(cal.getTime());
    }
    
    public static synchronized String convertDateToOFMStr(Date date){
    	if(date == null)
    		return null;
    	return OFM_SDF.format(date);
    }
    
    public static synchronized Date convertAMIStrToDate(String strDate) throws ParseException{
		try {
			Date date = AMI_SDF.parse(strDate);
			return date;
		} catch (ParseException e) {
			throw e;
		}    	
    }    
    
    public static synchronized String convertCalToAMIStr(Calendar cal){
    	if(cal == null)
    		return null;
        return AMI_SDF.format(cal.getTime());
    }

    public static synchronized String convertDateToAMIStr(Date date){
    	if(date == null)
    		return null;
        return AMI_SDF.format(date);
    }
    
	public static synchronized Calendar convertGMMZDateStrToCalendar(String strDate) throws ParseException{
    	Calendar cal = Calendar.getInstance(Locale.US);
    	Date date = convertGMMZDateStrToDate(strDate);
    	cal.setTime(date);
    	return cal;
    }
    
    public static synchronized Date convertGMMZDateStrToDate(String strDate) throws ParseException{
		try {
			Date date = GMMZ_SDF.parse(strDate);
			return date;
		} catch (ParseException e) {
			throw e;
		}    	
    }    
    
    public static synchronized String convertCalToGMMZDateStr(Calendar cal){
    	if(cal == null)
    		return null;
        return GMMZ_SDF.format(cal.getTime());
    }

    public static synchronized String convertDateToGMMZDateStr(Date date){
    	if(date == null)
    		return null;
        return GMMZ_SDF.format(date);
    }
    
    public static synchronized String convertDateToSmsGMMZDateStr(Date date){
    	if(date == null)
    		return null;
        return GMMZ_SMS_SDF.format(date);
    }
    
    //----
    public static synchronized Date convertGMMZEAIStrToDateTime(String strDate) throws ParseException{
		try {
			Date date = GMMZ_EAI_SDF.parse(strDate);
			return date;
		} catch (ParseException e) {
			throw e;
		}    	
    }    
    
    public static synchronized String convertCalDateTimeToGMMZEAIStr(Calendar cal){
    	if(cal == null)
    		return null;
        return GMMZ_EAI_SDF.format(cal.getTime());
    }

    public static synchronized String convertDateTimeToGMMZEAIStr(Date date){
    	if(date == null)
    		return null;
        return GMMZ_EAI_SDF.format(date);
    }
    
    //Option : DateTime-Millisec
	
	public static synchronized Calendar convertStrToCalendarDateTimeMillisec(String strDate) throws ParseException{
    	Calendar cal = Calendar.getInstance(Locale.US);
    	Date date = convertStrToDateTimeMillisec(strDate);
    	cal.setTime(date);
    	return cal;
    }
    
    public static synchronized Date convertStrToDateTimeMillisec(String strDate) throws ParseException{
		try {
			Date date = DF_DFMILLISEC.parse(strDate);
			return date;
		} catch (ParseException e) {
			throw e;
		}    	
    }    
    
    public static synchronized String convertCalDateTimeMillisecToStr(Calendar cal){
    	if(cal == null)
    		return null;
        return DF_DFMILLISEC.format(cal.getTime());
    }

    public static synchronized String convertDateTimeMillisecToStr(Date date){
    	if(date == null)
    		return null;
        return DF_DFMILLISEC.format(date);
    }
    
    @SuppressWarnings("deprecation")
	public static Date calculateExpireDate(Date startDate, String productUnitPeriod){
		Date expDate = new Date(startDate.getTime());
		if(productUnitPeriod == null || productUnitPeriod.isEmpty()){
			// TODO
		} else if(productUnitPeriod.endsWith("M")){
			String numStr = productUnitPeriod.substring(0, productUnitPeriod.indexOf("M"));
			Integer num = Integer.parseInt(numStr);
			expDate.setMonth(expDate.getMonth()+num.intValue());
		} else if(productUnitPeriod.endsWith("D")){
			String numStr = productUnitPeriod.substring(0, productUnitPeriod.indexOf("D"));
			Integer num = Integer.parseInt(numStr);
			expDate.setDate(expDate.getDate()+num.intValue());
		} else {
			Integer num = Integer.parseInt(productUnitPeriod);
			expDate.setDate(expDate.getDate()+num.intValue());
		}
		
		return expDate;
	}

	public static String convertDateTimeToGMMZStr(Date date) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
    
}
