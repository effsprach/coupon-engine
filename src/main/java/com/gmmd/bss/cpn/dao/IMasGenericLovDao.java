package com.gmmd.bss.cpn.dao;

import java.util.List;

import com.gmmd.bss.dom.cm.MasGenericLov;

public interface IMasGenericLovDao extends IGenericDao<MasGenericLov, Integer>{
	
	public List<MasGenericLov> getGenericByNameListAndCategoty(List<String> list_name, String category_name);
	
	public List<MasGenericLov> getGenericByCategoty(String category_name);
}
