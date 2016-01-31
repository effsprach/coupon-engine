package com.gmmd.bss.cm.bs.handle.coupon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmmd.bss.cm.bs.ICouponService;
import com.gmmd.bss.cm.bs.handle.coupon.generator.ICpnGeneratorHandler;
import com.gmmd.bss.common.util.BSSConstantUtil;
import com.gmmd.bss.common.util.CPNConstantUtil;
import com.gmmd.bss.common.util.CPNConstantUtil.CouponStatus;
import com.gmmd.bss.common.util.CPNConstantUtil.CouponType;
import com.gmmd.bss.common.util.CPNConstantUtil.CouponUsageStatus;
import com.gmmd.bss.common.util.CPNPropertiesHelper;
import com.gmmd.bss.common.util.LogMessageHelper;
import com.gmmd.bss.common.util.LogMessageHelper.POINT;
import com.gmmd.bss.cpn.dao.ICampaignConditionParamDao;
import com.gmmd.bss.cpn.dao.ICouponDao;
import com.gmmd.bss.cpn.dao.ICouponUsageDao;
import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.dom.cpn.CouponUsage;
import com.gmmd.bss.exception.BusinessException;

public abstract class AbstractCouponHandler implements ICouponHandler{
	
	protected final Logger logger;
	
	public AbstractCouponHandler() {
		logger = Logger.getLogger(this.getClass());
	}
	
	@Resource ICouponService couponService;
	
	@Autowired ICouponUsageDao couponUsageDao;
	@Autowired ICouponDao couponDao;
	@Autowired ICampaignConditionParamDao campaignConditionParamDao;
	
	// ***Abstract method
	// method used by getCampaignCouponCode
	protected abstract List<Coupon> getAvalCampaignCpn(CouponCampaign couponCampaign, Integer numberOfGetCoupon,
			List<CampaignConditionParam> specCpCondParams) throws BusinessException ;
	// method used by createCoupon
	protected abstract Integer getLimitNumberCampaignUsageCoupon(Integer campaignId, List<CampaignConditionParam> specCpCondParams) throws BusinessException ;
	// method used by mark used coupon
	protected abstract Coupon updateUsageCoupon(Coupon coupon, CouponUsage couponUsage, String remarkUsage, String passCode) throws BusinessException;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public List<Coupon> getCampaignCouponCode(CouponCampaign couponCampaign, Integer numberOfGetCoupon
			, String extCustId, String extSysId, String remark, List<CampaignConditionParam> specCpCondParams) throws BusinessException{
		
		String methodName = "getCampaignCouponCode";
		String keyLog = "couponCampaignId";
		String keyValue = String.valueOf(couponCampaign.getCouponCampaignId());
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		List<Coupon> cpnCreateList =  new ArrayList<Coupon>(0);
		List<Coupon> cpnAvalList = new ArrayList<Coupon>(0);
//		List<Coupon> cpnTargetList = new ArrayList<Coupon>(0);
		
		// Step1: Get aval campaign coupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Step1: Get aval campaign coupon number:"+numberOfGetCoupon));
		cpnAvalList = getAvalCampaignCpn(couponCampaign, numberOfGetCoupon, specCpCondParams);
		
		if(cpnAvalList != null && cpnAvalList.size() != 0){
			
			Integer numberCpnAval = Integer.valueOf(cpnAvalList.size());
			if(numberOfGetCoupon.equals(numberCpnAval)){
				logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
						, "Found coupon avaliable completely, return value"));
				cpnAvalList = updateReserveCoupon(couponCampaign.getCouponType().getCouponTypeId(), cpnAvalList
						, extCustId, extSysId, remark);
				return cpnAvalList; 
			}
			
			// The remain required number cpn
			numberOfGetCoupon = numberOfGetCoupon-numberCpnAval;
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
					, "The remain required number cpn :"+numberOfGetCoupon));
		}
		
		// Step2: Check max number coupon on campiagn
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Step2: Check max number coupon on campiagn"));
		if(!chkNumberCpnPerCampaign(couponCampaign, numberOfGetCoupon)){
			throw new BusinessException("818004", new Object[] {couponCampaign.getCouponCampaignId()}); 
		}
		
		// Step3: Create coupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Step3: Create coupon"));
		cpnCreateList = createCoupon(couponCampaign, numberOfGetCoupon, extCustId, extSysId, remark, specCpCondParams);
		
		// Step3.1: Merge cpn list at found and create
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Step3.1:  Merge cpn list at found and create"));
		cpnAvalList.addAll(cpnCreateList);
		
		//Step4: Reserve coupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Step4: Check for reserve coupon list"));
		List<Coupon> cpnUpdatedList = updateReserveCoupon(couponCampaign.getCouponType().getCouponTypeId(), cpnAvalList, extCustId, extSysId, remark);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnUpdatedList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public List<Coupon> updateReserveCoupon(Integer couponTypeId, List<Coupon> cpnList, String extCustId, String extSysId, String remark){
		
		String methodName = "updateReserveCoupon";
		String keyLog = "couponTypeId";
		String keyValue = String.valueOf(couponTypeId);
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Date currDate = new Date();
		CouponUsage cpnUsage = null;
		List<CouponUsage> cpnUsages = null;
		
		for(Coupon cpn : cpnList){
			
			if(CouponType.CPN_ONE_USE_ONCE.getId().equals(couponTypeId)){
				cpn.setStatus(CouponStatus.RESERVE.getValue()); // one to one status: reserve, one to one status: ready to use
			}
			
			// update coupon
			cpn.setUpdateBy(CPNConstantUtil.CREATE_UPDATE_BY);
			cpn.setUpdateDate(currDate);
			cpn.setNumberOfUsage(cpn.getNumberOfUsage()+1);
			
			// add usage reserve
			cpnUsage = new CouponUsage();
			cpnUsages = new ArrayList<CouponUsage>();
			cpnUsage.setExtCusId(extCustId);
			cpnUsage.setCoupon(cpn);
			cpnUsage.setExtSystemId(extSysId);
			cpnUsage.setRemark(remark);
			cpnUsage.setStatus(CPNConstantUtil.CouponUsageStatus.RESERVE.getValue());
			cpnUsage.setCreateBy(CPNConstantUtil.CREATE_UPDATE_BY);
			cpnUsage.setCreateDate(currDate);
			cpnUsages.add(cpnUsage);
			
			cpn.setCouponUsages(cpnUsages);
		}
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Persist update coupon list"));
		List<Coupon> cpnUpdatedList = couponDao.bulkSaveOrUpdateObject(cpnList);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnUpdatedList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public List<Coupon> createCoupon(CouponCampaign couponCampaign, Integer numberCreateCoupon, String extCustId
			, String extSysId, String remark, List<CampaignConditionParam> specCpCondParams)throws BusinessException { 
		
		String methodName = "createCoupon";
		String keyLog = "couponCampaignId";
		String keyValue = String.valueOf(couponCampaign.getCouponCampaignId());
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		String clsGenerateHandler = null;
		
		// Step1: Get limit numberc ampaign usage coupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Step1: Get limit numberc ampaign usage coupon"));
		Integer limitNOUsage = getLimitNumberCampaignUsageCoupon(couponCampaign.getCouponCampaignId(), specCpCondParams);
		
		// Step2: Get handler class generate
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Step2: Get handler class generate"));
		try {
			
			clsGenerateHandler = CPNPropertiesHelper.getGeneratorHandler(String.valueOf(couponCampaign.getOrgByCreaterId().getOrgId()));
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "cls at lookup: "+clsGenerateHandler));
			
		} catch (Exception e) {
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.ex, keyLog, keyValue, ""
					, "Got some error handler creater id: "+couponCampaign.getOrgByCreaterId().getOrgId()), e);
			throw new BusinessException("110001", new Object[] {"Got some error handler creater id: "
					+couponCampaign.getOrgByCreaterId()}); 
		}
		
		// Step3: Get Factory & concrete executeCoupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Step3: Get Factory & concrete executeCoupon"));
		ICpnGeneratorHandler cpnGeneratorHandler = CouponHandlerFactory.getCpnGenerator(clsGenerateHandler);
		List<Coupon> cpnList = cpnGeneratorHandler.executeCoupon(couponCampaign, numberCreateCoupon, limitNOUsage, remark, specCpCondParams);
		
		// Step4: Persist insert coupon list
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Step4: Persist insert coupon list"));
		couponService.storeInsertCouponList(cpnList);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnList;
	}
	
	@Transactional(readOnly = true)
	private Integer getMaxNumberCpnPerCampaign(CouponCampaign couponCampaign) throws BusinessException{
		
		String methodName = "getMaxNumberCpnPerCampaign";
		String keyLog = "couponCampaignId";
		String keyValue = String.valueOf(couponCampaign.getCouponCampaignId());
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Integer maxNumberCpnPerCampaign = null;
		
		// get max number cpn per campaign
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get campaignCondParm by campId and parmName"));
		CampaignConditionParam campaignCondiParam = campaignConditionParamDao.getCampaignConditionParamByCampaignIdAndParamName(
				couponCampaign.getCouponCampaignId(), CPNConstantUtil.CampaignConditionParamName.MAX_NUMBER_COUPON_PER_CAMPAIGN);
		
		// check config max number cpn per campaign
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Check config max number cpn per campaign"));
		if(campaignCondiParam == null){
			// miss config campaign condition param
			throw new BusinessException("110001", new Object[] {"miss config campaign condition param"}); 
		}
		
		maxNumberCpnPerCampaign = Integer.valueOf(campaignCondiParam.getCondParamValue());
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Get max number cpn per campaign value is "+maxNumberCpnPerCampaign));
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return maxNumberCpnPerCampaign;
	}
	
	@Transactional(readOnly = true)
	public Boolean chkNumberCpnPerCampaign(CouponCampaign couponCampaign, Integer numberOfGetCoupon) throws BusinessException {
		
		String methodName = "chkNumberCpnPerCampaign";
		String keyLog = "couponCampaignId";
		String keyValue = String.valueOf(couponCampaign.getCouponCampaignId());
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Integer maxNumberCpnPerCampaign = null;
		
		// Get max number cpn per campaign
		maxNumberCpnPerCampaign = getMaxNumberCpnPerCampaign(couponCampaign);
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Get max number cpn per campaign: "+maxNumberCpnPerCampaign));
		
		Integer numberCurrCpnOnCampaign = couponCampaign.getCoupons().size();
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Current number cpn on campaign: "+numberCurrCpnOnCampaign));
		
		Integer numberCpnRemainAtCreate = maxNumberCpnPerCampaign-numberCurrCpnOnCampaign;
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
				, "Number remain at create : "+numberCpnRemainAtCreate));
		
		if(numberOfGetCoupon > numberCpnRemainAtCreate){
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, ""
					, "Over limit for create coupon in campaign id: "+couponCampaign.getCouponCampaignId()));
			return false;
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return true;
	}
	
	@Override
	public void hold(Coupon cpn) throws BusinessException {}
	
	@Override
	@Transactional(readOnly = true)
	public CouponCampaign validate(Coupon cpn) throws BusinessException {
		
		String methodName = "validate";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		// Check expire date 
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Check expire date"));
		checkCouponExpireTime(cpn);
		
		// Check limit usage coupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Check limit usage coupon"));
		checkCouponLimitUsage(cpn);
		
		// Validate Pass
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue
				, "", "coupon full code: "+cpn.getCouponFullCode()+" is pass"));
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpn.getCouponCampaign();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Coupon markUse(Coupon cpn, String extCustId, String extSysId, String remark, String passCode) throws BusinessException {
		
		String methodName = "markUse";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		List<CouponUsage> cpnUsages = new ArrayList<CouponUsage>(0);
		CouponUsage cpnUsage = null;
		
		// Check coupon expire time
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Check coupon expire time"));
		checkCouponExpireTime(cpn);
		
		// Get Usage relate coupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get Usage relate coupon"));
		cpnUsage = couponUsageDao.getCouponUsageByFullCodeAndOwnerId(cpn.getCouponFullCode(), extCustId, extSysId
				, CPNConstantUtil.CouponUsageStatus.READY_TO_USE.getValue());
		
		// Update coupon, count usage
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Update coupon & usage"));
		Coupon cpnUpdated = updateUsageCoupon(cpnUsage.getCoupon(), cpnUsage, remark, passCode);
			
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnUpdated;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Coupon release(Coupon cpn, String remark) throws BusinessException {
		
		String methodName = "release";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		Date currDate = new Date();
		
		//validate
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "validate coupon"));
		validate(cpn);
		
		//relesase coupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "relesase coupon"));
		cpn.setRemark(remark);
		cpn.setStatus(CouponStatus.READY_TO_USE.getValue());
		cpn.setUpdateDate(currDate);
		cpn.setUpdateBy(BSSConstantUtil.CREATE_UPDATE_BY);
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "update coupon"));
		Coupon cpnReleased = couponDao.saveOrUpdateObject(cpn);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnReleased;
	}
	
	// Default check expire date (It is currently not over coupon end date)
	@Transactional(readOnly = true)
	public void checkCouponExpireTime(Coupon cpn) throws BusinessException{
		
		String methodName = "checkCouponExpireTime";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Date currDate = new Date();
		Date cpnExpireDate = cpn.getEndDate();
		
		if(currDate.compareTo(cpnExpireDate) > 0){
            throw new BusinessException("818001", new Object[] {cpn.getCouponCode()});
        }
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
	}  
	
	// Default check over limit usage coupon
	@Transactional(readOnly = true)
	public void checkCouponLimitUsage(Coupon cpn) throws BusinessException{
		
		String methodName = "checkCouponLimitUsage";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Integer limitNumberUsage = cpn.getLimitUsageCount();
		Integer numberOfUsage = cpn.getNumberOfUsage();
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue
				, "", "LimitNumberUsage : "+ limitNumberUsage+", numberOfUsage:"+numberOfUsage));
		if(limitNumberUsage.equals(numberOfUsage)){
			throw new BusinessException("818002", new Object[] {cpn.getCouponFullCode()});
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
	} 
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public CouponUsage insertCouponUsage(Coupon coupon, String extCustId, String extSysId, String remark) throws BusinessException {
		
		String methodName = "insertUsageCoupon";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Date dateCurr = new Date();
		CouponUsage cpnUsage = new CouponUsage();
		
		cpnUsage.setCoupon(coupon);
		cpnUsage.setExtCusId(extCustId);
		cpnUsage.setExtSystemId(extSysId);
		cpnUsage.setUsageDate(dateCurr);
		cpnUsage.setStatus(CouponUsageStatus.USED.getValue());
		cpnUsage.setCreateDate(dateCurr);
		cpnUsage.setCreateBy(BSSConstantUtil.CREATE_UPDATE_BY);
		cpnUsage.setRemark(remark);
		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "insert cpn usage"));
		couponUsageDao.insertObject(cpnUsage);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnUsage;
	}
	
}
