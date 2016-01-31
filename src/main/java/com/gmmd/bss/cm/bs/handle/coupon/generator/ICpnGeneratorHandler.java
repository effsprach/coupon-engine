package com.gmmd.bss.cm.bs.handle.coupon.generator;

import java.util.List;

import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.exception.BusinessException;

public interface ICpnGeneratorHandler {
	
	public List<Coupon> executeCoupon(CouponCampaign couponCampaign, Integer numberOfGenCoupon
			, Integer limitNOUsage, String remark, List<CampaignConditionParam> specCpCondParams) throws BusinessException;
	
	public Coupon importCoupon() throws BusinessException;
}
