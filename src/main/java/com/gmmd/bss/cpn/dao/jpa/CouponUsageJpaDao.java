package com.gmmd.bss.cpn.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gmmd.bss.cpn.dao.ICouponUsageDao;
import com.gmmd.bss.dom.cpn.CouponUsage;

@Repository("couponUsageDao")
public class CouponUsageJpaDao extends AbstractGenericJpaDao<CouponUsage, Integer> implements ICouponUsageDao{

	public CouponUsageJpaDao() {
		super(CouponUsage.class);
	}

	@Override
	public CouponUsage getCouponUsageByFullCodeAndOwnerId(
			String couponFullCode, String extCustId, String extSysId,
			String usageStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CouponUsage> getCouponUsageListByFullCodeAndStatus(
			String couponFullCode, List<String> usageStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CouponUsage getCouponUsageFromCouponFullCodeAndCaId(
			String couponFullCode, String extCustId, String extSysId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
