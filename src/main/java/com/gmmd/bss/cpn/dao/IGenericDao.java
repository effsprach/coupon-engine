package com.gmmd.bss.cpn.dao;

import java.io.Serializable;
import java.util.List;

import com.gmmd.bss.dom.DomObject;

public interface IGenericDao<DomainObject extends DomObject, KeyType extends Serializable> {

	/**
	 * Insert a new object
	 */
	public void insertObject(DomainObject object) ;

	/**
	 * Get all list of the objects in the given catalog.
	 */
	public List<DomainObject> getAllObject(DomainObject object) ;
	
	/**
	 * Get the object in the given catalog.
	 */
	public DomainObject findByPK(KeyType key) ;
	
	
	/**
	 * save or update a new object
	 */
	public DomainObject saveOrUpdateObject(DomainObject object) ;
	
	/**
	 * delete object
	 */
	public void delete(DomainObject object) ;
	
	/**
	 * Insert a new object list
	 */
	public void bulkInsertObject(List<DomainObject> objectList);
	
	
	public List<DomainObject> bulkSaveOrUpdateObject(List<DomainObject> objectList);
	
}

