package com.doorstep.process;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.doorstep.controllers.EntityManagerFactoryManager;
import com.doorstep.dao.Category;
import com.doorstep.dao.Person;
import com.doorstep.dao.Product;
import com.doorstep.dao.SubCategory;

/**
 * This class pre-populates the table needed
 * 
 *
 */
public class PopulateTables {

	public PopulateTables() {
		EntityManagerFactory entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Category> result = entityManager.createQuery("from Category", Category.class).getResultList();

		if (result.size() != 0) {
			/*
			 * close the connection and return if the tables are already
			 * populated
			 */
			entityManager.close();
			return;
		}

		// add vegetable category to category table
		Category vegetableCategory = new Category(Constants.VEGETABLE_ID, Constants.VEGETABLE, true);
		entityManager.persist(vegetableCategory);

		// add fruit category to category table
		Category fruitCategory = new Category(Constants.FRUIT_ID, Constants.FRUIT, true);
		entityManager.persist(fruitCategory);

		// add bakery category to category table
		Category BakeryCategory = new Category(Constants.BAKERY_ID, Constants.BAKERY, true);
		entityManager.persist(BakeryCategory);

		/*
		 * Add different sub Categories to SubCategory table
		 */
		SubCategory BakerySlicedBread = new SubCategory(Constants.SLICED_BREAD_ID, Constants.SLICED_BREAD, true,
				BakeryCategory);
		entityManager.persist(BakerySlicedBread);

		SubCategory BakeryWrap = new SubCategory(Constants.WRAP_ID, Constants.WRAP, true, BakeryCategory);
		entityManager.persist(BakeryWrap);

		/*
		 * Add diffrent products to product table
		 */
		entityManager.persist(new Product(Constants.TOMATO_ID, Constants.TOMATO, "Vegetable Brand 1", "Its a vegetable",
				Constants.TOMATO_URL, 5, 6, Constants.TAXABLE_PERCENT, true, vegetableCategory, null));
		entityManager.persist(new Product(Constants.ONION_ID, Constants.ONION, "Vegetable Brand 2", "Its a vegetable",
				Constants.ONION_URL, 6, 7, Constants.TAXABLE_PERCENT, true, vegetableCategory, null));
		entityManager.persist(new Product(Constants.POTATO_ID, Constants.POTATO, "Vegetable Brand 1", "Its a vegetable",
				Constants.POTATO_URL, 3, 7, Constants.TAXABLE_PERCENT, true, vegetableCategory, null));

		entityManager.persist(new Product(Constants.BANANA_ID, Constants.BANANA, "Fruit Brand 1", "Its a Fruit",
				Constants.BANANA_URL, 4, 7, Constants.TAXABLE_PERCENT, true, fruitCategory, null));
		entityManager.persist(new Product(Constants.APPLE_ID, Constants.APPLE, "Fruit Brand 2", "Its a Fruit",
				Constants.APPLE_URL, 5, 8, Constants.TAXABLE_PERCENT, true, fruitCategory, null));

		entityManager.persist(
				new Product(Constants.WHITE_BREAD_ID, Constants.WHITE_BREAD, "Bread Brand 2", "Its a Bakery Item",
						Constants.WHITE_BREAD_URL, 2, 4, Constants.TAXABLE_PERCENT, true, BakeryCategory, BakerySlicedBread));
		entityManager.persist(new Product(Constants.WHOLE_WHEAT_BREAD_ID, Constants.WHOLE_WHEAT_BREAD, "Bread Brand 2",
				"Its a Bakery Item", Constants.WHOLE_WHEAT_BREAD_URL, 2, 4, Constants.TAXABLE_PERCENT, true, BakeryCategory,
				BakerySlicedBread));
		entityManager
				.persist(new Product(Constants.RYE_BREAD_ID, Constants.RYE_BREAD, "Bread Brand 1", "Its a Bakery Item",
						Constants.RYE_BREAD_URL, 2, 4, Constants.TAXABLE_PERCENT, true, BakeryCategory, BakerySlicedBread));

		entityManager
				.persist(new Product(Constants.WHITE_WRAP_ID, Constants.WHITE_WRAP, "Wrap Brand 2", "Its a Bakery Item",
						Constants.WHITE_WRAP_URL, 2, 4, Constants.TAXABLE_PERCENT, true, BakeryCategory, BakeryWrap));
		entityManager.persist(new Product(Constants.WHOLE_WHEAT_WRAP_ID, Constants.WHOLE_WHEAT_WRAP, "Wrap Brand 2",
				"Its a Bakery Item", Constants.WHOLE_WHEAT_WRAP_URL, 2, 4, Constants.TAXABLE_PERCENT, true, BakeryCategory,
				BakeryWrap));

		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
