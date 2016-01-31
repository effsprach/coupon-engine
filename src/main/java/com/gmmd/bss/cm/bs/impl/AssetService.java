/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 */
package com.gmmd.bss.cm.bs.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmmd.bss.cm.bs.IAssetService;
import com.gmmd.bss.dom.cm.Asset;
import com.gmmd.bss.dom.cm.CustomerAccount;
import com.gmmd.bss.exception.BSSException;
import com.gmmd.bss.exception.BusinessException;

/**
 * @author prachyawut
 * @version 1.00
 */

@Service("assetService")
public class AssetService implements IAssetService{
	
	//private Logger logger = Logger.getLogger(AssetService.class);
	
	@Override
	public Asset storeAsset(Asset asset)throws BSSException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Throwable.class)
	public boolean updateAssetStatusByCaId(CustomerAccount ca, List<Asset> assetList) throws BusinessException{
		// TODO Auto-generated method stub
		return false;
	}
	

}
