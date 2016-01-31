package com.gmmd.bss.cm.bs.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmmd.bss.cm.bs.IAssetService;
import com.gmmd.bss.cm.bs.ICouponService;
import com.gmmd.bss.cm.bs.handle.coupon.CouponHandlerFactory;
import com.gmmd.bss.cm.bs.handle.coupon.ICouponHandler;
import com.gmmd.bss.common.util.BSSConstantUtil;
import com.gmmd.bss.common.util.COMPropertiesHelper;
import com.gmmd.bss.common.util.CPNConstantUtil;
import com.gmmd.bss.common.util.LogMessageHelper;
import com.gmmd.bss.common.util.LogMessageHelper.POINT;
import com.gmmd.bss.cpn.dao.ICouponCampaignDao;
import com.gmmd.bss.cpn.dao.ICouponDao;
import com.gmmd.bss.cpn.dao.ICouponUsageDao;
import com.gmmd.bss.dom.cm.Asset;
import com.gmmd.bss.dom.cm.CustomerAccount;
import com.gmmd.bss.dom.cpn.CampaignConditionParam;
import com.gmmd.bss.dom.cpn.Coupon;
import com.gmmd.bss.dom.cpn.CouponCampaign;
import com.gmmd.bss.dom.cpn.CouponType;
import com.gmmd.bss.dom.cpn.CouponUsage;
import com.gmmd.bss.exception.BSSException;
import com.gmmd.bss.exception.BusinessException;
import com.gmmd.bss.exception.ConfigureException;


@Service("couponService")
public class CouponService implements ICouponService{
	
	private Logger logger = Logger.getLogger(CouponService.class);
	
	@Resource ICouponService couponService;
	@Resource IAssetService assetService;
	
	@Autowired ICouponDao couponDao;
	@Autowired ICouponCampaignDao couponCampaignDao;
	@Autowired ICouponUsageDao couponUsageDao;
	
	public static String logBody(String methodName, String keyLog, String keyValue, String message) {
		return LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", message);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CouponCampaign validateCoupon(String couponFullCode) throws BusinessException{
		
		String methodName = "validateCoupon";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		List<Coupon> cpns = null;
		
		// Step 1: GetCouponByCouponFullCode
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "GetCouponByCouponFullCode"));
		Coupon cpn = couponDao.getCouponByCouponFullCode(couponFullCode);
		if(cpn == null){
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue
					, "", "Not found coupon full code: "+couponFullCode));
			throw new BusinessException("818000", new Object[] {couponFullCode});
		}
		
		// Step 2: Get Campaign & coupon type for look up handler
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get Campaign & coupon type for look up handler"));
		CouponType cpnType = cpn.getCouponCampaign().getCouponType(); // mark sure getCouponCampaign & getCouponType not null
		String classHandle = cpnType.getTypeHandle();
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Class handler name: " + classHandle));
		
		// Step 3: New instance coupon & validate
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "New Instance coupon & validate"));
		ICouponHandler couponHandler = CouponHandlerFactory.getCouponInstance(classHandle);
		CouponCampaign couponCampaign = couponHandler.validate(cpn);
		
		// Step 4: Set Response Coupon Campaign & Coupon (may be change response)
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Set Response"));
		cpns = new ArrayList<Coupon>(0);
		couponCampaign.getCoupons().clear();
		cpns.add(cpn);
		couponCampaign.setCoupons(cpns);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return couponCampaign;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public Coupon markUseCoupon(String couponFullCode, String extCustId, String extSysId, String remark, String passCode) throws BusinessException{
		
		String methodName = "markUseCoupon";
		String keyLog = "couponFullCode";
		String keyValue = couponFullCode;
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		// Step 1: getCouponByCouponFullCode
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "getCouponByCouponFullCode"));
		Coupon cpn = couponDao.getCouponByCouponFullCode(couponFullCode);
		if(cpn == null){
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue
					, "", "Not Found Coupon Full Code: "+couponFullCode));
			throw new BusinessException("818000", new Object[] {couponFullCode});
		}
		
		// Step 2: Get Campaign & coupon type for look up handler
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get Campaign & coupon type for look up handler"));
		CouponType cpnType = cpn.getCouponCampaign().getCouponType(); // mark sure getCouponCampaign & getCouponType not null
		String classHandle = cpnType.getTypeHandle();
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Class handler name: " + classHandle));
		
		// Step 3: New instance coupon & markUse
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "New Instance coupon & markUse"));
		ICouponHandler couponHandler = CouponHandlerFactory.getCouponInstance(classHandle);
		Coupon cpnMarkUesd = couponHandler.markUse(cpn, extCustId, extSysId, remark, passCode);
		
		// Step 4: Call asset service update asset status
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Call asset service update asset status"));
			// Step 4.1: Prepare Ca for call update asset status
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Prepare Ca for call update asset status"));
		CustomerAccount ca = new CustomerAccount();
		ca.setCustomerAccountId(Integer.valueOf(extCustId));
		
			// Step 4.2: Prepare Asset for call update asset status
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Prepare Asset for call update asset status"));
		List<Asset> assetList = new ArrayList<Asset>();
		Asset asset = new Asset();
				// Get coupon usage to get AssetId 
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get coupon usage to get AssetId"));
		CouponUsage couponUsage = couponUsageDao.getCouponUsageByFullCodeAndOwnerId(couponFullCode, extCustId, extSysId, CPNConstantUtil.CouponUsageStatus.USED.getValue());
		asset.setAssetId(Integer.valueOf(couponUsage.getExtRefOpt1()));
				// Set asset status to USED
		asset.setAssetStatus(BSSConstantUtil.AssetStatus.USED.getValue()); 
		assetList.add(asset);
		
			// Step 4.3: Call update asset status 		
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Call update asset status"));
			
		try {
			assetService.updateAssetStatusByCaId(ca, assetList);
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Update asset status success"));
		} catch (BSSException e) {
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Update asset status fail"));
			throw new BusinessException("110001", new Object[] {"Update asset status fail"});
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnMarkUesd;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Map<String, List<Coupon>> markUseCouponList(List<Coupon> couponList){
		
		String methodName = "markUseCouponList";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		Map<String, List<Coupon>> mapCouponExecuted = new HashMap<String, List<Coupon>>(0);
		List<Coupon> couponErrorList = new ArrayList<Coupon>();
		List<Coupon> couponSuccessList = new ArrayList<Coupon>();
		String remark = "", extCustId = "", extSystemId ="", passCode = "";
		Coupon cpnMarkUsed = null;
		
		for(Coupon coupon: couponList){
			
			logger.debug(logBody(methodName, keyLog, keyValue, "each coupon list for mark use"));
			
			try {
				
				String couponFullCode = coupon.getCouponFullCode();
				logger.debug(logBody(methodName, keyLog, keyValue, "couponFullCode: "+couponFullCode));
				
				// ** Mode couponUsage Option is not support. If change request must change logic
				if(coupon.getCouponUsages().size() > 0 && coupon.getCouponUsages().get(0) != null){
					CouponUsage cpnUsage = coupon.getCouponUsages().get(0);	
					extCustId = cpnUsage.getExtCusId();
					passCode = cpnUsage.getPassCode();
					
					logger.debug(logBody(methodName, keyLog, keyValue, "extCustId: " + extCustId));
					extSystemId = cpnUsage.getExtSystemId();
					logger.debug(logBody(methodName, keyLog, keyValue, "extSystemId: " + extSystemId));
					
					if(cpnUsage.getRemark() != null && !("").equals(cpnUsage.getRemark())){
						remark = cpnUsage.getRemark();
					}
					
					logger.debug(logBody(methodName, keyLog, keyValue, "remark: " + remark));
				}
				
				cpnMarkUsed = couponService.markUseCoupon(couponFullCode, extCustId, extSystemId, remark, passCode);
				
				// add coupon mark use success into list
				logger.debug(logBody(methodName, keyLog, keyValue, "add coupon mark use success into list"));
				couponSuccessList.add(cpnMarkUsed);
				
			} catch (BusinessException e) {
				
				logger.debug(logBody(methodName, keyLog, keyValue, "Got some business error fullcode: "
						+coupon.getCouponFullCode()+": " + e.getMessage()));
				coupon.setRemark(e.getErrorMessage());
				
				// add coupon mark use error into list
				logger.debug(logBody(methodName, keyLog, keyValue, "add coupon mark use error into list"));
				couponErrorList.add(coupon);
			} catch (Exception e) {
				
				logger.debug(logBody(methodName, keyLog, keyValue, "Got some internal error fullcode: "
							+coupon.getCouponFullCode()+": " + e.getMessage()));
				
				coupon.setRemark(e.getMessage());
				// add coupon mark use error into list
				logger.debug(logBody(methodName, keyLog, keyValue, "add coupon mark use error into list"));
				couponErrorList.add(coupon);
			}
		}
		
		mapCouponExecuted.put("couponSuccessList", couponSuccessList);
		mapCouponExecuted.put("couponErrorList", couponErrorList);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return mapCouponExecuted;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public Coupon releaseCoupon(String couponFullCode, String remark) throws BusinessException {
		
		String methodName = "releaseCoupon";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		// Step 1: GetCouponByCouponFullCode
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "GetCouponByCouponFullCode"));
		Coupon cpn = couponDao.getCouponByCouponFullCode(couponFullCode);
		if(cpn == null){
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue
					, "", "Not Found Coupon Full Code: "+couponFullCode));
			throw new BusinessException("818000", new Object[] {couponFullCode});
		}
		
		// Step 2: Get Campaign & coupon type for look up handler
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get Campaign & coupon type for look up handler"));
		CouponType cpnType = cpn.getCouponCampaign().getCouponType(); // mark sure getCouponCampaign & getCouponType not null
		String classHandle = cpnType.getTypeHandle();
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Class handler name: " + classHandle));
		
		// Step 3: New instance coupon & markUse
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "New Instance coupon & release"));
		ICouponHandler couponHandler = CouponHandlerFactory.getCouponInstance(classHandle);
		Coupon cpnReleased = couponHandler.release(cpn, remark);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnReleased;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public List<Coupon> getCampaignCouponCode(Integer campaignId, Integer numberOfGetCoupon
			, String extCustId, String extSysId, String remark, List<CampaignConditionParam> specCpCondParams) throws BusinessException {
		
		String methodName = "createCoupon";
		String keyLog = "campaignId";
		String keyValue = campaignId+"";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		// Step 1: Validate Coupon Campaign Id
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Validate coupon campaign by id: "+campaignId));
		CouponCampaign cpnCampaign =  couponCampaignDao.findByPK(campaignId);
		if(cpnCampaign == null){
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue
					, "", "Not Found Coupon campaign Id: "+campaignId));
			throw new BusinessException("818003", new Object[] {campaignId});
		}
		
		// Step 2: Get coupon type for look up handler
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get coupon type for look up handler"));
		CouponType cpnType = cpnCampaign.getCouponType(); // mark sure getCouponCampaign & getCouponType not null
		String classHandle = cpnType.getTypeHandle();
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Class handler name: " + classHandle));
		
		// Step 3: New instance coupon & create coupon
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "New Instance coupon & create"));
		ICouponHandler couponHandler = CouponHandlerFactory.getCouponInstance(classHandle);
		List<Coupon> cpnList = couponHandler.getCampaignCouponCode(cpnCampaign, numberOfGetCoupon, extCustId, extSysId, remark, specCpCondParams);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public List<Coupon> storeSaveOrUpdateCouponList(List<Coupon> cpnList) {
		
		String methodName = "storeSaveOrUpdateCouponList";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		List<Coupon> cpnUpdatedList = couponDao.bulkSaveOrUpdateObject(cpnList);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnUpdatedList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public List<Coupon> storeInsertCouponList(List<Coupon> cpnList) {
		
		String methodName = "storeSaveOrUpdateCouponList";
		String keyLog = "";
		String keyValue = "";
		long st = System.currentTimeMillis();
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		
		couponDao.bulkInsertObject(cpnList);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, "", ""));
		
		return cpnList;
	}

	@Override
	@Transactional(readOnly = true)
	public CouponCampaign getCouponCampaignInformation(Integer couponCampaignId) throws BusinessException {
		String methodName = "getCouponCampaignInformation";
		String keyLog = "couponCampaignId", keyValue = String.valueOf(couponCampaignId);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		long st = System.currentTimeMillis();
		
		// Step 1: get coupon campaign 
		CouponCampaign couponCampaignInq = couponCampaignDao.getCouponCampaignById(couponCampaignId);
		if(couponCampaignInq == null){
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue , "", "Not Found Coupon Campaign Id: "+couponCampaignId));
			throw new BusinessException("818003", new Object[] {couponCampaignId});
		}
		
		// Step 2: load campaign information
		Hibernate.initialize(couponCampaignInq.getOrgByCreaterId());
		Hibernate.initialize(couponCampaignInq.getOrgByPartnerId());
		Hibernate.initialize(couponCampaignInq.getCouponType());
		for(CampaignConditionParam campaignConditionParam : couponCampaignInq.getCampaignConditionParams()){
			Hibernate.initialize(campaignConditionParam);
		}		
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, String.valueOf(ed - st), ""));		
		return couponCampaignInq;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Coupon updateCouponOwner(String couponFullCode, String extCustId, String extSysId, String extRefOpt1, String extRefOpt2, String extRefOpt3, String remarkUsage) throws BusinessException {
		String methodName = "updateCouponOwner";
		String keyLog = "couponFullCode", keyValue = couponFullCode;
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		long st = System.currentTimeMillis();
		
		// Step 1: validate parameter updateCouponOwner
		validateUpdateCouponOwner(couponFullCode, extCustId, extSysId, extRefOpt1);
		
		// Step 2: Get Usage from couponFullCode, extCustId, extSysId
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Get Usage from couponFullCode, extCustId, extSysId"));
		CouponUsage couponUsage = couponUsageDao.getCouponUsageFromCouponFullCodeAndCaId(couponFullCode, extCustId, extSysId);
		if(couponUsage == null){
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue , "", "Not Found couponUsage at couponFullCode : "+couponFullCode));
			throw new BusinessException("818005", new Object[] {couponFullCode});
		}
		
		// Step 3: Update couponStatus Coupon to ready to use for coupon type = 100
		logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "Update couponStatus Coupon to ready to use for coupon type = 100"));
		Coupon coupon = couponUsage.getCoupon();
		if((CPNConstantUtil.CouponType.CPN_ONE_USE_ONCE.getId()).equals(couponUsage.getCoupon().getCouponCampaign().getCouponType().getCouponTypeId())){
			logger.debug(LogMessageHelper.formatBSSLog(methodName, POINT.b, keyLog, keyValue, "", "New coupon status : "+ CPNConstantUtil.CouponStatus.READY_TO_USE.getValue()));
			coupon.setStatus(CPNConstantUtil.CouponStatus.READY_TO_USE.getValue());
		}
		
		// Step 4: prepare and save data		
			// set couponUsage data
		Date dateCurr = new Date();
		if(remarkUsage != null && !("").equalsIgnoreCase(remarkUsage)){
			couponUsage.setRemark(remarkUsage);
		}
		couponUsage.setExtRefOpt1(extRefOpt1);
		couponUsage.setStatus(CPNConstantUtil.CouponUsageStatus.READY_TO_USE.getValue());
		couponUsage.setUpdateDate(dateCurr);
		couponUsage.setUpdateBy(CPNConstantUtil.CREATE_UPDATE_BY);
		
			// add couponUsage to ArrayList CouponUsage
		List<CouponUsage> couponUsageIns = new ArrayList<CouponUsage>();
		couponUsageIns.add(couponUsage);
		
			// set coupon data
		coupon.setUpdateDate(dateCurr);
		coupon.setUpdateBy(CPNConstantUtil.CREATE_UPDATE_BY);
		coupon.setCouponUsages(couponUsageIns);
		
			// save data
		Coupon cpnUpdateOwner = couponDao.saveOrUpdateObject(coupon);
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, String.valueOf(ed - st), ""));			
		return cpnUpdateOwner;
	}
	
	private void validateUpdateCouponOwner(String couponFullCode, String extCustId, String extSysId, String extRefOpt1) throws BusinessException{
		String methodName = "validateUpdateCouponOwner";
		String keyLog = "couponFullCode", keyValue = couponFullCode;
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		long st = System.currentTimeMillis();
		
		if(couponFullCode == null || ("").equalsIgnoreCase(couponFullCode)){
			throw new BusinessException("310001", new Object[] {"couponFullCode"});
		}
		
		if(extCustId == null || ("").equalsIgnoreCase(extCustId)){
			throw new BusinessException("310001", new Object[] {"extCustId"});
		}
		
		if(extSysId == null || ("").equalsIgnoreCase(extSysId)){
			throw new BusinessException("310001", new Object[] {"extSysId"});
		}
		
		if(extRefOpt1 == null || ("").equalsIgnoreCase(extRefOpt1)){
			throw new BusinessException("310001", new Object[] {"extRefOpt1"});
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, String.valueOf(ed - st), ""));			
	}
	
	private Integer getTicketsExpireMaxResult(String keyName){
		Integer maxResult = null;
		try {
			String[] batchConfigStr  = COMPropertiesHelper.getBusinessBatchConfig(keyName).split("\\|");
	        maxResult =  Integer.valueOf(batchConfigStr[0]);
		} catch (ConfigureException e) {
			maxResult = 50;
		}
		return maxResult;
	}
	
	private List<String> getTicketsExpireStatusList(String keyName){
		List<String> statusList = null;
		try {
			String[] batchConfigStr  = COMPropertiesHelper.getBusinessBatchConfig(keyName).split("\\|");
	        String[] statusAll =  batchConfigStr[1].split("\\,");
	        statusList = new ArrayList<String>();
	        for(String status : statusAll){
	            statusList.add(status);
	        }
		} catch (ConfigureException e) {
			statusList = null;
		}
		return statusList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCouponExpire() throws BusinessException{
		String methodName = "updateCouponExpire";
		String keyLog = "", keyValue = "";
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.s, keyLog, keyValue, "", ""));
		long st = System.currentTimeMillis();
		
		// Step 1: Get max result
		logger.debug(logBody(methodName, keyLog, keyValue, "Get max result"));
		Integer maxResult = getTicketsExpireMaxResult("UPDATE_COUPON_EXPIRE");		
		logger.debug(logBody(methodName, keyLog, keyValue, "Max result : " + maxResult));

		// Step 2: Get coupon status that can set to expired
		logger.debug(logBody(methodName, keyLog, keyValue, "Get coupon status that can set to expired"));
		List<String> statusList = getTicketsExpireStatusList("UPDATE_COUPON_EXPIRE");
		logger.debug(logBody(methodName, keyLog, keyValue, "statusList : "+statusList.get(0)+", "+statusList.get(1)));
				
		// Step 3: Get coupon expired list
		logger.debug(logBody(methodName, keyLog, keyValue, "Get coupon expired list"));
		Date currDate = new Date();
		List<Coupon> coupon = couponDao.getCouponExpireList(maxResult, statusList, currDate);
		
		for(Coupon cpn : coupon){
			// Step 4: Set coupon status to expired
			logger.debug(logBody(methodName, keyLog, keyValue, "Set coupon status to expired at couponId : "+cpn.getCouponId()));
			cpn.setStatus(CPNConstantUtil.CouponStatus.EXPIRED.getValue());
			cpn.setUpdateBy(CPNConstantUtil.CREATE_UPDATE_BY);
			cpn.setUpdateDate(currDate);
			
			// Step 5: Save coupon expired status
			logger.debug(logBody(methodName, keyLog, keyValue, "Save coupon expired status at couponId : "+cpn.getCouponId()));
			cpn = storeSaveOrUpdateCoupon(cpn);
			
			// Step 6: Update asset status if Usage is not null
			CouponUsage couponUsage = null;
			try{
				couponUsage = cpn.getCouponUsages().get(0);
			}catch (Exception e){
				continue;
			}
			
			if(couponUsage!=null){
				if(couponUsage.getExtCusId()!=null && !("").equalsIgnoreCase(couponUsage.getExtCusId()) 
						&& couponUsage.getExtRefOpt1()!=null && !("").equalsIgnoreCase(couponUsage.getExtRefOpt1())){
					// Step 6.1: Prepare information to call update asset
					CustomerAccount ca = new CustomerAccount();
					ca.setCustomerAccountId(Integer.valueOf(couponUsage.getExtCusId()));
					
					List<Asset> assetList = new ArrayList<Asset>();
					Asset asset = new Asset();
					asset.setAssetId(Integer.valueOf(couponUsage.getExtRefOpt1()));
					asset.setAssetStatus(BSSConstantUtil.AssetStatus.EXPIRE.getValue()); 
					assetList.add(asset);
									
					
					// Step 6.3: Call update asset	
					logger.debug(logBody(methodName, keyLog, keyValue, "Call update asset status at couponId : "+cpn.getCouponId()+" caId : "+couponUsage.getExtCusId()));
					try {							
						if((CPNConstantUtil.AppId.ECOM.getName()).equalsIgnoreCase(couponUsage.getExtSystemId())){
							assetService.updateAssetStatusByCaId(ca, assetList);	
							logger.debug(logBody(methodName, keyLog, keyValue, "Update asset status success at couponId : "+cpn.getCouponId()));
						}						
						
					} catch (BSSException e) {
						logger.debug(logBody(methodName, keyLog, keyValue, "Update asset status fail at couponId : "+cpn.getCouponId()));
						continue;
					}
				}
			}
		}
		
		long ed = System.currentTimeMillis();
		Long.toString(ed - st);
		logger.info(LogMessageHelper.formatBSSLog(methodName, POINT.e, keyLog, keyValue, String.valueOf(ed - st), ""));	
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public Coupon storeSaveOrUpdateCoupon(Coupon coupon)throws BusinessException {
		
		long st = System.currentTimeMillis();  
		String method = "storeSaveOrUpdateCoupon";
		String keyLog = "";
		String keyValue = "";
		logger.info(LogMessageHelper.formatBSSLog(method, POINT.s, keyLog, keyValue, "", ""));
		
		Coupon couponPersisted = couponDao.saveOrUpdateObject(coupon);
		
		long ed = System.currentTimeMillis();  
		logger.info(LogMessageHelper.formatBSSLog(method, POINT.e, keyLog, keyValue, String.valueOf(ed - st), ""));
		
		return couponPersisted;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public List<Coupon> createCouponForTest(Integer campaignId,
			Integer numberCreateCoupon, String extCustId, String extSysId,
			String remark, List<CampaignConditionParam> specCpCondParams)
			throws BusinessException {
		
		
		// TixGet Campaign OneUsageManyTime
		CouponCampaign couponCampaign = couponCampaignDao.findByPK(campaignId);
		CouponType cpnType = couponCampaign.getCouponType();
		String classHandle = cpnType.getTypeHandle();
		ICouponHandler couponHandler = CouponHandlerFactory.getCouponInstance(classHandle);
		List<Coupon> conList = couponHandler.createCoupon(couponCampaign, numberCreateCoupon, extCustId, extSysId, remark, specCpCondParams);
		
		
		return conList;
	}
}
