package com.gmmd.bss.dom;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class description : For some values in the response type special
 * @author Prachyawut
 * @version 1.00 Dec 17, 2014
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ObjectReference extends DomObject{
	
	private static final long serialVersionUID = 1L;
	private Map<String, Object> extraData;

	public ObjectReference() {
		//this.extraData = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getExtraData() {
		return extraData;
	}

	public void setExtraData(Map<String, Object> extraData) {
		this.extraData = extraData;
	}

	public void add(String key, Object value) {
		if(extraData == null){
			this.extraData = new HashMap<String, Object>();
		}
		extraData.put(key, value);
	}
}