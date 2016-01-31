package com.gmmd.bss.cm.bs.handle.coupon;

import org.springframework.context.ApplicationContext;

import com.gmmd.bss.cm.bs.handle.coupon.generator.ICpnGeneratorHandler;
import com.gmmd.bss.cm.bs.util.ApplicationContextUtils;

public class CouponHandlerFactory {
	
	private static ApplicationContext ctx ;

	public static ICouponHandler getCouponInstance(String classname){
		ctx = ApplicationContextUtils.getApplicationContext();
		ICouponHandler handler = (ICouponHandler) ctx.getBean(classname);
		return handler;
	}
	
	public static ICpnGeneratorHandler getCpnGenerator(String classname){
		ctx = ApplicationContextUtils.getApplicationContext();
		ICpnGeneratorHandler cpnGeneratorHandler = (ICpnGeneratorHandler) ctx.getBean(classname);
		return cpnGeneratorHandler; 
	}
	
}
