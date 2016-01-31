/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 */
package com.gmmd.bss.cpn.dao.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.gmmd.bss.cpn.dao.IGenericDao;
import com.gmmd.bss.dom.DomObject;

/**
 * @author Prachyawut
 * @version 1.00 April 01 2015
 */
public class AbstractGenericJpaDao <DomainObject extends DomObject, KeyType extends Serializable> implements IGenericDao<DomainObject, KeyType>{

	private Class<DomainObject> type;
	protected EntityManager entityManager;
	protected final Logger logger;
	
	/**
	 * @param type
	 */
	public AbstractGenericJpaDao(Class<DomainObject> type) {
		super();
		this.type = type;
		logger = Logger.getLogger(this.getClass());
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void insertObject(DomainObject object){
		entityManager.persist(object);
	}
	
	public void bulkInsertObject(List<DomainObject> objectList){
		for(DomainObject obj :objectList){
			entityManager.persist(obj);
		}
	}
	
	public List<DomainObject> bulkSaveOrUpdateObject(List<DomainObject> objectList){
		List<DomainObject> domainObjList = new ArrayList<DomainObject>();
		for(DomainObject obj :objectList){
			domainObjList.add(entityManager.merge(obj));
		}
		return objectList;
	}
	
	@SuppressWarnings("unchecked")
	public List<DomainObject> getAllObject(DomainObject object) {
		List<DomainObject> resultList = entityManager.createQuery("select o from " + type.getName() + " o").getResultList();
		return resultList;
	}

	public DomainObject findByPK(KeyType key) {
		if (key == null) {
			return null;
		} else {
		return (DomainObject)entityManager.find(type, key);
		}
	}

	@Transactional
	public DomainObject saveOrUpdateObject(DomainObject object) {	
		return (DomainObject) entityManager.merge(object);
	}
	
	public void delete(DomainObject object) {
		entityManager.remove(object);
	}

}
