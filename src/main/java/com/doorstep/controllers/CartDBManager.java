package com.doorstep.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.doorstep.dao.Cart;
import com.doorstep.dao.Person;

/**
 * To Manage the Cart related functions
 * 
 *
 */
public class CartDBManager {
	private EntityManagerFactory entityManagerFactory;

	public CartDBManager() {

		/**
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();

	}

	/**
	 * create new cart for the specific person
	 * 
	 * @param personID
	 *            id of the person for which the cart will be created
	 * @return the new cart assigned to the person
	 */
	public Cart createNewCart(int personID) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		/*
		 * Find the person from database and then in next step map it to new
		 * cart that is being made
		 */
		Person person = entityManager.find(Person.class, personID);

		/*
		 * create the new cart for current person and new cart will always be
		 * false for is_checkout value
		 */
		Cart cart = new Cart(person, false);

		entityManager.persist(cart);
		/*
		 * Commit new changes
		 */
		entityManager.getTransaction().commit();
		/*
		 * close the connection
		 */
		entityManager.close();

		return cart;
	}

	/**
	 * This will be useful if we want to search the current active cart for
	 * particular person
	 * 
	 * @param personID
	 *            id of the person for which the cart will be searched
	 * @return cart found then the current cart otherwise null
	 */
	public Cart searchActiveCart(int personID) {
		Cart activeCart = null;

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			/*
			 * Get all the carts from database
			 */
			List<Cart> result = entityManager.createQuery("from Cart", Cart.class).getResultList();

			/*
			 * find the current active cart for particular person
			 */
			for (Cart cart : result) {
				if (cart.getPerson_id().getId() == personID)
					if (cart.isIs_checked_out() == false) {
						activeCart = cart;
						break;
					}
			}
		} finally {
			entityManager.close();
		}
		/*
		 * this will return null if no active cart for the person is found
		 */
		return activeCart;
	}

	/**
	 * This method will checkout the cart, so set the is_checkoed_out to true
	 * 
	 * @param cartId
	 *            the id of the cart to checkout
	 */
	public void checkoutCart(int cartId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Cart cart = entityManager.find(Cart.class, cartId);
			cart.setIs_checked_out(true);
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}

	}
}
