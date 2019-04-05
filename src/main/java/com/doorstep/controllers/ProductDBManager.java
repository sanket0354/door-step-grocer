package com.doorstep.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.doorstep.dao.Product;

public class ProductDBManager {
	private EntityManagerFactory entityManagerFactory;

	public ProductDBManager() {

		/*
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();

	}

	/**
	 * This method is use to search a particular product from database
	 * 
	 * @param productName
	 *            name of the product to be searched
	 * @return the product found
	 */
	public Product searchProduct(String productName) {
		Product productToBeSearched = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			/*
			 * take all product from database
			 */
			List<Product> result = entityManager.createQuery("from Product", Product.class).getResultList();

			/*
			 * Search for the product we want and break the loop if found
			 */
			for (Product product : result) {
				if (product.getProduct_name().equals(productName)) {
					productToBeSearched = product;
					break;
				}
			}
		} finally {
			entityManager.close();
		}
		/*
		 * return the product if found otherwise null
		 */
		return productToBeSearched;
	}

	/**
	 * To get the list of product of particular category
	 * 
	 * @param categoryId
	 *            the id of the category
	 * @return the list of products
	 */
	public List<Product> getProducts(int categoryId) {
		List<Product> productList = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			Query query = entityManager.createQuery("SELECT c FROM Product c WHERE category_id=" + categoryId);
			productList = (List<Product>) query.getResultList();
		} finally {
			entityManager.close();
		}
		return productList;

	}

	/**
	 * List of product mapped to a particular category and sub-category
	 * 
	 * @param categoryId
	 *            the id of category
	 * @param subCategoryID
	 *            the id of sub-category
	 * @return list of product
	 */
	public List<Product> getProductsBasedOnCatIdAndSubCatId(int categoryId, int subCategoryID) {
		List<Product> productList = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			Query query = entityManager.createQuery("SELECT c FROM Product c WHERE category_id=" + categoryId
					+ " AND sub_category_id=" + subCategoryID);
			productList = (List<Product>) query.getResultList();
		} finally {
			entityManager.close();
		}
		return productList;

	}

	/**
	 * the product for particular id
	 * 
	 * @param productId
	 *            the id of the product to search for
	 * @return the product
	 */
	public Product serachProductOnId(int productId) {
		Product product = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			product = entityManager.find(Product.class, productId);
		} finally {
			entityManager.close();
		}
		return product;

	}

}
