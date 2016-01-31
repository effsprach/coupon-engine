package com.gmmd.bss.cm.bs.handle.coupon.type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmmd.bss.cm.bs.handle.coupon.AbstractCouponHandler;
import com.gmmd.bss.common.util.BSSConstantUtil;
import com.gmmd.bss.common.util.CPNConstantUtil;
import com.gmmd.bss.common.util.CPNConstantUtil.CouponStatus;
import com.gmmd.bss.common.util.LogMessageHelper;
import com.gmmd.bss.common.util.LogMessageHelper.POINT;
import com.gmmd.bss.cpn.dao.ICouponCampaignDao;
import com.gmmd.bss.cpn.dao.ICouponDao;
import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.dom.cpn.CouponUsage;
import com.gmmd.bss.exception.BusinessException;


@Component("CPNCodeOnlyUseOneTimeHandler")
public class CPNCodeOnlyUseOneTimeHandler extends AbstractCouponHandler{
	
	@Autowired ICouponDao couponDao;
	@Autowired ICouponCampaignDao couponCampaignDao;
	
	private static final Integer LIMIT_NUMBER_USAGE_PER_COUPON = 1;
	
	@Override
	protected List<Coupon> getAvalCampaignCpn(CouponCampaign couponCampaign
			, Integer numberOfGetCoupon, List<CampaignConditionParam> specCpCondParams) throws BusinessException {
		
		String methodName = "getAvalCampaignCpn";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		List<Coupon> cpnAvalList = null;
		List<String> cpnStatusList = new ArrayList<String>(0);
		cpnStatusList.add(CouponStatus.READY_TO_USE.getValue());
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", ""));
		cpnAvalList = couponDao.getAvalCouponByCampaignIdAndStatus(couponCampaign.getCouponCampaignId(), numberOfGetCoupon);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnAvalList;
	}

	@Override
	protected Integer getLimitNumberCampaignUsageCoupon(Integer campaignId, List<CampaignConditionParam> specCpCondParams) {
		return LIMIT_NUMBER_USAGE_PER_COUPON; // Coupon usage once 
	}
	
	
	/*
	 * update coupon after usage(mark use)
	 * count++ :  number of usage, update date, by
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Coupon updateUsageCoupon(Coupon coupon, CouponUsage couponUsage, String remarkUsage, String passCode) throws BusinessException{
		
		String methodName = "updateUsageCoupon";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		Date dateCurr = new Date();
		
		List<CouponUsage> couponUsages = new ArrayList<CouponUsage>(0);
		// update coupon
		coupon.setStatus(CouponStatus.USED.getValue());
		coupon.setUpdateDate(dateCurr);
		coupon.setUpdateBy(BSSConstantUtil.CREATE_UPDATE_BY);
		
		// update usage
		if(passCode != null && !("").equals(passCode)){
			couponUsage.setPassCode(passCode);
		}
		couponUsage.setRemark(remarkUsage);
		couponUsage.setUsageDate(dateCurr);
		couponUsage.setStatus(CPNConstantUtil.CouponUsageStatus.USED.getValue());
		couponUsage.setUpdateDate(dateCurr);
		couponUsage.setUpdateBy(CPNConstantUtil.CREATE_UPDATE_BY);
		couponUsages.add(couponUsage);
		
		coupon.setCouponUsages(couponUsages);
		Coupon couponUpdated = couponDao.saveOrUpdateObject(coupon);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return couponUpdated;
	}
	
}
