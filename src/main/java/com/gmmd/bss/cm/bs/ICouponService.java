package com.gmmd.bss.cm.bs;

import java.util.List;
import java.util.Map;

import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.exception.BusinessException;

public interface ICouponService {
	
	public CouponCampaign validateCoupon(String couponFullCode) throws BusinessException;
	
	public Coupon markUseCoupon(String couponFullCode, String extCustId, String extSysId, String remark, String passCode) throws BusinessException;

	public Coupon releaseCoupon(String couponFullCode, String remark) throws BusinessException;
	 
	//getCampaignCouponCode : Get AVAL & Create coupon by campaignId
	public List<Coupon> getCampaignCouponCode(Integer campaignId, Integer numberOfGetCoupon, String extCustId, String extSysId, String remark, List<CampaignConditionParam> specCpCondParams) throws BusinessException;
	
	public List<Coupon> storeSaveOrUpdateCouponList(List<Coupon> cpnList);
	
	public List<Coupon> storeInsertCouponList(List<Coupon> cpnList);

	public CouponCampaign getCouponCampaignInformation(Integer couponCampaignId) throws BusinessException;
	
	public Map<String, List<Coupon>> markUseCouponList(List<Coupon> couponList);
	
	//Mandatory Field : couponFullCode, extCustId(caId), extSysId, extRefOpt1(assetId)
	public Coupon updateCouponOwner(String couponFullCode, String extCustId, String extSysId, String extRefOpt1, String extRefOpt2, String extRefOpt3, String remarkUsage) throws BusinessException;

	//Call by batch
	public void updateCouponExpire() throws BusinessException;

	public Coupon storeSaveOrUpdateCoupon(Coupon coupon) throws BusinessException;
	
	
	public List<Coupon> createCouponForTest(Integer campaignId, Integer numberCreateCoupon, String extCustId, String extSysId, String remark, List<CampaignConditionParam> specCpCondParams) throws BusinessException;
}
