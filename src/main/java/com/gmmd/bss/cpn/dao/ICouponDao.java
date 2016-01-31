package com.gmmd.bss.cpn.dao;

import java.util.Date;
import java.util.List;

import com.gmmd.bss.dom.cpn.Coupon;

public interface ICouponDao extends IGenericDao<Coupon, Integer>{

	public Coupon getCouponByCouponFullCode(String couponFullCode);
	
	public List<Coupon> getAvalCouponByCampaignIdAndStatus(Integer campaignId, Integer numberOfGetCoupon);
	
	public List<Coupon> getAvalCouponByCampaignIdAndStatusAtleastUsage(Integer campaignId, Integer numberOfGetCoupon);

	public List<Coupon> getCouponExpireList(Integer maxResult, List<String> statusList, Date expireDate);
	
}
