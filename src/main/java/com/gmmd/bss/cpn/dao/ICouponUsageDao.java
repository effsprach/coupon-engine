package com.gmmd.bss.cpn.dao;

import java.util.List;

import com.gmmd.bss.dom.cpn.CouponUsage;

public interface ICouponUsageDao extends IGenericDao<CouponUsage, Integer>{
	
	public CouponUsage getCouponUsageByFullCodeAndOwnerId(String couponFullCode, String extCustId, String extSysId, String usageStatus);
	
	public List<CouponUsage> getCouponUsageListByFullCodeAndStatus(String couponFullCode, List<String> usageStatus);

	public CouponUsage getCouponUsageFromCouponFullCodeAndCaId(String couponFullCode, String extCustId, String extSysId);

}
