package com.gmmd.bss.cpn.dao.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gmmd.bss.cpn.dao.ICouponDao;
import com.gmmd.bss.dom.cpn.Coupon;


@Repository("couponDao")
public class CouponJpaDao extends AbstractGenericJpaDao<Coupon, Integer> implements ICouponDao{

	public CouponJpaDao() {
		super(Coupon.class);
	}

	@Override
	public Coupon getCouponByCouponFullCode(String couponFullCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coupon> getAvalCouponByCampaignIdAndStatus(Integer campaignId,
			Integer numberOfGetCoupon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coupon> getAvalCouponByCampaignIdAndStatusAtleastUsage(
			Integer campaignId, Integer numberOfGetCoupon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coupon> getCouponExpireList(Integer maxResult,
			List<String> statusList, Date expireDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
