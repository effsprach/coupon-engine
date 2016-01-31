package com.gmmd.bss.cm.bs.handle.coupon;

import java.util.List;

import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.exception.BusinessException;

public interface ICouponHandler {
		
	public CouponCampaign validate(Coupon cpn) throws BusinessException;
	
	public List<Coupon> createCoupon(CouponCampaign couponCampaign, Integer numberCreateCoupon, String extCustId
			, String extSysId, String remark, List<CampaignConditionParam> cpCondParams) throws BusinessException;
	
	public Coupon markUse(Coupon cpn, String extCustId, String extSysId, String remark, String passCode) throws BusinessException;  
	
	public void hold(Coupon cpn) throws BusinessException;   
	
	public Coupon release(Coupon cpn, String remark) throws BusinessException; 
	
	public List<Coupon> getCampaignCouponCode(CouponCampaign couponCampaign, Integer numberOfGetCoupon
			, String extCustId, String extSysId, String remark, List<CampaignConditionParam> specCpCondParams) throws BusinessException;
	
}
