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
import com.gmmd.bss.common.util.LogMessageHelper;
import com.gmmd.bss.common.util.CPNConstantUtil.CouponStatus;
import com.gmmd.bss.common.util.LogMessageHelper.POINT;
import com.gmmd.bss.cpn.dao.ICampaignConditionParamDao;
import com.gmmd.bss.cpn.dao.ICouponCampaignDao;
import com.gmmd.bss.cpn.dao.ICouponDao;
import com.gmmd.bss.cpn.dao.ICouponUsageDao;
import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.dom.cpn.CouponUsage;
import com.gmmd.bss.exception.BusinessException;


@Component("CPNCodeUseManyTimeHandler")
public class CPNCodeUseManyTimeHandler extends AbstractCouponHandler{
	
	@Autowired ICouponDao couponDao;
	@Autowired ICouponUsageDao couponUsageDao;
	@Autowired ICouponCampaignDao couponCampaignDao;
	@Autowired ICampaignConditionParamDao campaignConditionParamDao;

	@Override
	protected List<Coupon> getAvalCampaignCpn(CouponCampaign couponCampaign
			, Integer numberOfGetCoupon, List<CampaignConditionParam> specCpCondParams) throws BusinessException {
		String methodName = "getAvalCampaignCpn";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", ""));
		
		List<Coupon> cpnAvalList = null;
		List<String> cpnStatusList = new ArrayList<String>(0);
		cpnStatusList.add(CouponStatus.READY_TO_USE.getValue());
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", ""));
		cpnAvalList = couponDao.getAvalCouponByCampaignIdAndStatusAtleastUsage(couponCampaign.getCouponCampaignId(), numberOfGetCoupon);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnAvalList;
	}
	

	@Override
	@Transactional(readOnly = true)
	protected Integer getLimitNumberCampaignUsageCoupon(Integer campaignId, List<CampaignConditionParam> specCpCondParams) throws BusinessException {
		
		String methodName = "getLimitNumberCampaignUsageCoupon";
		String keyLog = "campaignId";
		String keyValue = campaignId+"";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", ""));
		
		Integer limitNumberUsageCpn = null;
		
		
		// if limit in request use by config
		for(CampaignConditionParam specCpCondParam : specCpCondParams){
			if(CPNConstantUtil.CampaignConditionParamName.LIMIT_NUMBER_USAGE_PER_COUPON.equals(specCpCondParam.getCondParamName())){
				limitNumberUsageCpn = Integer.valueOf(specCpCondParam.getCondParamValue());
				break;
			}
		}
		
		if(limitNumberUsageCpn == null){
		
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get campaignCondParm by campId and parmName"));
			CampaignConditionParam campaignCondiParam = campaignConditionParamDao.getCampaignConditionParamByCampaignIdAndParamName(
					campaignId, CPNConstantUtil.CampaignConditionParamName.LIMIT_NUMBER_USAGE_PER_COUPON);
			
			// check config limit number usage cpn
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
					, "Check config max number cpn per campaign"));
			if(campaignCondiParam == null){
				// miss config campaign condition param
				throw new BusinessException("110001", new Object[] {"miss config limit number usage cpn"}); 
			}
			
			limitNumberUsageCpn = Integer.valueOf(campaignCondiParam.getCondParamValue());
			
		}
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "limit number usage cpn: "+limitNumberUsageCpn));

		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return limitNumberUsageCpn;
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
		String couponStatus = "";
		List<CouponUsage> couponUsages = new ArrayList<CouponUsage>(0);
		List<String> usageStatusList = new ArrayList<String>(0);
		usageStatusList.add(CPNConstantUtil.CouponUsageStatus.USED.getValue());
		
		// update coupon
		Integer LimitUsageCount = coupon.getLimitUsageCount();
		List<CouponUsage> couponUsagesCurr = couponUsageDao.getCouponUsageListByFullCodeAndStatus(
												coupon.getCouponFullCode(), usageStatusList);
		Integer usageNumberCurr = couponUsagesCurr.size();
		
		if(LimitUsageCount.equals((usageNumberCurr+1))){
			couponStatus = CouponStatus.USED.getValue();
		}else{
			couponStatus = CouponStatus.READY_TO_USE.getValue();
		}
		
		coupon.setStatus(couponStatus);
		coupon.setUpdateDate(dateCurr);
		coupon.setUpdateBy(BSSConstantUtil.CREATE_UPDATE_BY);
		
		// update usage
		if(passCode != null && !("").equals(passCode)){
			couponUsage.setPassCode(passCode);
		}
		couponUsage.setRemark(remarkUsage);
		couponUsage.setUsageDate(dateCurr);
		couponUsage.setUpdateBy(CPNConstantUtil.CREATE_UPDATE_BY);
		couponUsage.setStatus(CPNConstantUtil.CouponUsageStatus.USED.getValue());
		couponUsage.setUpdateDate(dateCurr);
		couponUsages.add(couponUsage);
		
		coupon.setCouponUsages(couponUsages);
		Coupon couponUpdated = couponDao.saveOrUpdateObject(coupon);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return couponUpdated;
	}


}
