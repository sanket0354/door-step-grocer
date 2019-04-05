package com.doorstep.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.doorstep.dao.CartItem;
import com.doorstep.dao.SubCategory;

public class SubCategoryDBManager {
	private EntityManagerFactory entityManagerFactory;

	public SubCategoryDBManager() {

		/**
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();

	}

	/**
	 * get the list of sub-category mapped to particular category
	 * 
	 * @param categoryId
	 *            the id of the category
	 * @return the list of sub-category
	 */
	public List<SubCategory> getAllSubCategory(int categoryId) {
		List<SubCategory> result = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			/*
			 * search if the item already exits in cart then update only the
			 * quantity by 1
			 */
			Query query = entityManager.createQuery("SELECT c FROM SubCategory c WHERE category_id=" + categoryId);

			/*
			 * get the result list by executing the query
			 * 
			 * NOTE: There will be only one result that will have same product
			 * in one cart only the quantity will be change. We can reduce the
			 * duplicates by doing so.
			 */
			result = (List<SubCategory>) query.getResultList();
		} finally {
			entityManager.close();
		}

		return result;

	}

}
