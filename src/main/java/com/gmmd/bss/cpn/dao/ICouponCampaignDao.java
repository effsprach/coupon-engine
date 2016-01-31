package com.gmmd.bss.cpn.dao;

import com.gmmd.bss.dom.cpn.CouponCampaign;

public interface ICouponCampaignDao extends IGenericDao<CouponCampaign, Integer>{
	
	public CouponCampaign getCouponCampaignDetailByCouponFullCode(String couponFullCode);

	public CouponCampaign getCouponCampaignById(Integer couponCampaignId);
	
//	public Integer getNumberCouponPerCampaign(Integer campaign, String cuponStatus);

}
