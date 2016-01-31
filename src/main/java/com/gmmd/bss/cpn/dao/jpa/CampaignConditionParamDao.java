package com.gmmd.bss.cpn.dao.jpa;

import org.springframework.stereotype.Repository;

import com.gmmd.bss.cpn.dao.ICampaignConditionParamDao;
import com.gmmd.bss.dom.cpn.CampaignConditionParam;



@Repository("campaignConditionParamDao")
public class CampaignConditionParamDao extends AbstractGenericJpaDao<CampaignConditionParam, Integer> implements ICampaignConditionParamDao{

	public CampaignConditionParamDao() {
		super(CampaignConditionParam.class);
	}

	@Override
	public CampaignConditionParam getCampaignConditionParamByCampaignIdAndParamName(
			Integer campaignId, String paramName) {
		// TODO Auto-generated method stub
		return null;
	}
}
