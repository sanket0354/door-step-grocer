package com.doorstep.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.doorstep.dao.Category;

public class CategoryDBManager {
	private EntityManagerFactory entityManagerFactory;

	public CategoryDBManager() {

		/**
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();

	}

	/**
	 * to get all the categories in database
	 * 
	 * @return the list of categories
	 */
	public List<Category> getAllCategory() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Category> result = null;
		try {
			/*
			 * take all Category from database
			 */
			result = entityManager.createQuery("from Category", Category.class).getResultList();
		} finally {
			entityManager.close();
		}

		return result;

	}

}
