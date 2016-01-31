package com.gmmd.bss.cpn.dao;

import com.gmmd.bss.dom.cpn.CampaignConditionParam;

public interface ICampaignConditionParamDao {
	
	public CampaignConditionParam getCampaignConditionParamByCampaignIdAndParamName(Integer campaignId, String paramName);

}
