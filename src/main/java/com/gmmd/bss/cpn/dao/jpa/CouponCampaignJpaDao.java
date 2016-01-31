package com.gmmd.bss.cpn.dao.jpa;

import org.springframework.stereotype.Repository;

import com.gmmd.bss.cpn.dao.ICouponCampaignDao;
import com.gmmd.bss.dom.cpn.CouponCampaign;


@Repository("couponCampaignDao")
public class CouponCampaignJpaDao extends AbstractGenericJpaDao<CouponCampaign, Integer> implements ICouponCampaignDao{

	public CouponCampaignJpaDao() {
		super(CouponCampaign.class);
	}

	@Override
	public CouponCampaign getCouponCampaignDetailByCouponFullCode(
			String couponFullCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CouponCampaign getCouponCampaignById(Integer couponCampaignId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
