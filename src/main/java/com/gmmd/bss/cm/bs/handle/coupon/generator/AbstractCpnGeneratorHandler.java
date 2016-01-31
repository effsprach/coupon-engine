package com.gmmd.bss.cm.bs.handle.coupon.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmmd.bss.common.util.CPNConstantUtil.CampaignConditionParamName;
import com.gmmd.bss.common.util.CPNConstantUtil.GenericLovName;
import com.gmmd.bss.common.util.LogMessageHelper;
import com.gmmd.bss.common.util.LogMessageHelper.POINT;
import com.gmmd.bss.cpn.dao.ICampaignConditionParamDao;
import com.gmmd.bss.cpn.dao.ICouponDao;
import com.gmmd.bss.cpn.dao.IMasGenericLovDao;
import com.gmmd.bss.dom.cm.MasGenericLov;
import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.exception.BusinessException;

public abstract class AbstractCpnGeneratorHandler implements ICpnGeneratorHandler{
	
	protected final Logger logger;
	
	@Autowired ICouponDao couponDao;
	@Autowired IMasGenericLovDao masGenericLovDao;
	@Autowired ICampaignConditionParamDao campaignConditionParamDao;
	
	public AbstractCpnGeneratorHandler() {
		logger = Logger.getLogger(this.getClass());
	}
	
	// Generate get new requirement implement at here.
	protected abstract List<CampaignConditionParam> preSpecificCampaignCondParam(CouponCampaign couponCampaign
			, List<CampaignConditionParam> specCpCondParams)  throws BusinessException;
	
	protected abstract List<Coupon> generate(CouponCampaign couponCampaign, Integer numberOfGenCoupon, Integer limitNOUsage
			, String remark, List<CampaignConditionParam> campaignConditionParams) throws BusinessException;
	
	@Override
	@Transactional(readOnly = true)
	public List<Coupon> executeCoupon(CouponCampaign couponCampaign, Integer numberOfGenCoupon, Integer limitNOUsage, String remark
			, List<CampaignConditionParam> specCpCondParams) throws BusinessException {
		
		String methodName = "executeCoupon";
		String keyLog = "couponCampaignId";
		String keyValue = String.valueOf(couponCampaign.getCouponCampaignId());
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		// Prepare common campaign condition param
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Step1: PreCommonCampaignConditionParam"));
		specCpCondParams = preCommonCampaignConditionParam(couponCampaign, specCpCondParams);
		
		// Prepare specific campaign condition param on concrete
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Step2: PreSpecificCampaignCondParam"));
		List<CampaignConditionParam> campaignGenerateCondParams = preSpecificCampaignCondParam(couponCampaign, specCpCondParams);
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Step3: Generate"));
		List<Coupon> cpnList = generate(couponCampaign, numberOfGenCoupon, limitNOUsage, remark, campaignGenerateCondParams);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnList;
	}
	
	
	/* Common condition : 
	 * 1) coupon start date & end date
	 * 2) ... not requirement
	 * 3) ...
	 * 
	 * prepare in preCommonCampaignConditionParam, Inhibit new specCpCondParams.
	 * Please modify common campaignConditionParam add only specCpCondParams 
	 */
	@Transactional(readOnly = true)
	public List<CampaignConditionParam> preCommonCampaignConditionParam(CouponCampaign couponCampaign
			, List<CampaignConditionParam> specCpCondParams) throws BusinessException{
		
		String methodName = "getCommonGenerateConditionParam";
		String keyLog = "couponCampaignId";
		String keyValue = String.valueOf(couponCampaign.getCouponCampaignId());
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		List<CampaignConditionParam> commonGenerateConditionParams = null;
		if(specCpCondParams == null){
			specCpCondParams = new ArrayList<CampaignConditionParam>(0);
		}
		
		// Check request sCpnDate & eCpnDate
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, " Check spec request & get date duration usage coupon"));
		commonGenerateConditionParams = checkAndGetCouponUsageDuration(couponCampaign, specCpCondParams);

		/* add common campaign condition param at here
		 * ...
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		
		return commonGenerateConditionParams;
	}
	
	public List<CampaignConditionParam> checkAndGetCouponUsageDuration(CouponCampaign couponCampaign, List<CampaignConditionParam> specCpCondParams){
		
		String methodName = "getCouponUsageDuration";
		String keyLog = "couponCampaignId";
		String keyValue = String.valueOf(couponCampaign.getCouponCampaignId());
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Boolean useCpnSDateSpec = false;
		Boolean useCpnEDateSpec = false;
		List<CampaignConditionParam> commonGenerateConditionParams = null;
		
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, " Check date duration usage coupon in request"));
		for(CampaignConditionParam specCpCondParam : specCpCondParams){
			if((CampaignConditionParamName.DEFAULT_CPN_USAGE_SDATE).equals(specCpCondParam.getCondParamName())){
				useCpnSDateSpec = true;
			}
		
			if((CampaignConditionParamName.DEFAULT_CPN_USAGE_EDATE).equals(specCpCondParam.getCondParamName())){
				useCpnEDateSpec = true;
			}
		}
		
		if(useCpnSDateSpec == false || useCpnEDateSpec == false){
			// Get default & add cpConditionParam
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
					, "cpn s/e date has not request, get default cpn s/e date"));
			commonGenerateConditionParams = getCampaignConditionParamDefaultDurationCpn(couponCampaign.getCouponCampaignId());
			for(CampaignConditionParam commonGenCondDefaultDurationCpnParam : commonGenerateConditionParams){
				specCpCondParams.add(commonGenCondDefaultDurationCpnParam);
			}
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return specCpCondParams;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public synchronized String createCommonCouponCode(){
		
		String methodName = "createCouponCode";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		String couponCode = "";
		Integer couponCodeNew = null;
		List<String> genericLovNameList = new ArrayList<String>(0);
		genericLovNameList.add(GenericLovName.GENERATE_COUPON_CODE_NUMBER);
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get running number by conf GenericLov"));
		List<MasGenericLov> masGenericLovList = masGenericLovDao.getGenericByNameListAndCategoty(
				genericLovNameList, GenericLovName.CPN_SIMPLE_GENERATE);
		MasGenericLov cpnGenericLov = masGenericLovList.get(0); // mark sure only value
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "CouponCode at get: "+cpnGenericLov.getValue()));
		couponCode = cpnGenericLov.getValue();
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Update running number by conf GenericLov"));
		couponCodeNew = Integer.valueOf(couponCode)+1;
		cpnGenericLov.setValue(String.valueOf(couponCodeNew));
		masGenericLovDao.saveOrUpdateObject(cpnGenericLov);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return couponCode;
	}
	
	
	public String createCommonCouponFullCode(String couponCode, String prefix, String suffix){
		
		String methodName = "createCommonCouponFullCode";
		String keyLog = "couponCode";
		String keyValue = couponCode+"";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		if(("").equals(prefix) || prefix == null){
			prefix = "";
		}
		if(("").equals(suffix) || suffix == null){
			suffix = "";
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return prefix + couponCode + suffix;
	}
	
	
	@Transactional(readOnly = true)
	public List<CampaignConditionParam> getCampaignConditionParamDefaultDurationCpn(Integer campaignId){
		
		String methodName = "getCampaignConditionParamDefaultDurationCpn";
		String keyLog = "campaignId";
		String keyValue = String.valueOf(campaignId);
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		
		List<CampaignConditionParam> defaultDurationCpnCpCondParams = new ArrayList<CampaignConditionParam>(0);
		
		CampaignConditionParam cpCondParamCpnSDate = campaignConditionParamDao.getCampaignConditionParamByCampaignIdAndParamName(
				campaignId, CampaignConditionParamName.DEFAULT_CPN_USAGE_SDATE);
		if(cpCondParamCpnSDate != null){
			defaultDurationCpnCpCondParams.add(cpCondParamCpnSDate);
		}
		
		CampaignConditionParam cpCondParamCpnEDate = campaignConditionParamDao.getCampaignConditionParamByCampaignIdAndParamName(
				campaignId, CampaignConditionParamName.DEFAULT_CPN_USAGE_EDATE);
		
		if(cpCondParamCpnEDate != null){
			defaultDurationCpnCpCondParams.add(cpCondParamCpnEDate);
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return defaultDurationCpnCpCondParams;
	}
	
	// Mark sure campaignConditionParamName is unique into campaignConditionParams
	public CampaignConditionParam getCampaignConditionParamByName(String campaignConditionParamName, List<CampaignConditionParam> campaignConditionParams){
		
		String methodName = "getCampaignConditionParamByName";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		CampaignConditionParam cpCondParamTarget = new CampaignConditionParam();
		
		for(CampaignConditionParam cpCondParam : campaignConditionParams){
			if(campaignConditionParamName.equals(cpCondParam.getCondParamName())){
				cpCondParamTarget = cpCondParam;
			}
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpCondParamTarget;
	}
}
