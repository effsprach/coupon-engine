package com.gmmd.bss.cm.bs.handle.coupon.generator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmmd.bss.common.util.CPNConstantUtil;
import com.gmmd.bss.common.util.CPNConstantUtil.CampaignConditionParamName;
import com.gmmd.bss.common.util.CPNConstantUtil.CouponStatus;
import com.gmmd.bss.common.util.CalendarUtil;
import com.gmmd.bss.common.util.LogMessageHelper;
import com.gmmd.bss.common.util.LogMessageHelper.POINT;
import com.gmmd.bss.cpn.dao.ICampaignConditionParamDao;
import com.gmmd.bss.cpn.dao.ICouponDao;
import com.gmmd.bss.cpn.dao.IMasGenericLovDao;
import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.exception.BusinessException;

@Component("GMMDigitalGeneratorHandler")
public class GMMDigitalGeneratorHandler extends AbstractCpnGeneratorHandler{
	
	@Autowired ICouponDao couponDao;
	@Autowired IMasGenericLovDao masGenericLovDao;
	@Autowired ICampaignConditionParamDao campaignConditionParamDao;
	
	@Override
	public Coupon importCoupon() throws BusinessException {return null;}
	
	
	@Override
	@Transactional(readOnly = true)
	protected List<CampaignConditionParam> preSpecificCampaignCondParam(CouponCampaign couponCampaign
			, List<CampaignConditionParam> specCpCondParams) throws BusinessException {
		// TODO ... modify campaignConditionParam for generate coupon
		return specCpCondParams;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	protected List<Coupon> generate(CouponCampaign couponCampaign, Integer numberOfGenCoupon, Integer limitNOUsage, String remark
			, List<CampaignConditionParam> campaignGenerateCondParams) throws BusinessException {
		
		String methodName = "generate";
		String keyLog = "couponCampaignId";
		String keyValue = String.valueOf(couponCampaign.getCouponCampaignId());
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Coupon cpn = null;
		List<Coupon> cpnList = new ArrayList<Coupon>(0);
		Date currDate = new Date();
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Get start & end date in campaignGenerateCondParams"));
		CampaignConditionParam campaignCondParamCpnSDate =  getCampaignConditionParamByName(
				CampaignConditionParamName.DEFAULT_CPN_USAGE_SDATE, campaignGenerateCondParams);
		CampaignConditionParam campaignCondParamCpnEDate =  getCampaignConditionParamByName(
				CampaignConditionParamName.DEFAULT_CPN_USAGE_EDATE, campaignGenerateCondParams);
		
		try {
			
			for(int i = 0; i<=numberOfGenCoupon-1; i++){
				cpn = new Coupon();
				cpn.setCouponCampaign(couponCampaign);
				String couponCode = createCommonCouponCode();
				logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
						, "Get coupon code: "+couponCode));
				cpn.setCouponCode(couponCode);
				cpn.setCouponFullCode(createCommonCouponFullCode(couponCode
						, couponCampaign.getPrefixCode(), couponCampaign.getSuffixCode()));
				cpn.setLimitUsageCount(limitNOUsage);
				cpn.setNumberOfUsage(new Integer(0));
				cpn.setStatus(CouponStatus.READY_TO_USE.getValue());
				cpn.setRemark(remark);
				cpn.setStartDate(CalendarUtil.convertCOMStrToDateTime(campaignCondParamCpnSDate.getCondParamValue()));
				cpn.setEndDate(CalendarUtil.convertCOMStrToDateTime(campaignCondParamCpnEDate.getCondParamValue()));
				cpn.setCreateBy(CPNConstantUtil.CREATE_UPDATE_BY);
				cpn.setCreateDate(currDate);
				cpnList.add(cpn);
			}
		
		} catch (ParseException e) {
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
					, "Got some error at parse cpn date: "+e.getMessage()),e);
			throw new BusinessException("110001", new Object[] {"ParseException:"+ e.getMessage()}); 
		}catch (Exception e) {
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
					, "Got some error : "+e.getMessage()),e);
			throw new BusinessException("110001", new Object[] {e.getMessage()}); 
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnList;
	}
	
	

}
