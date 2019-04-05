package com.doorstep.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.doorstep.dao.Cart;
import com.doorstep.dao.CartItem;
import com.doorstep.dao.Product;

public class CartItemDBManager {
	private EntityManagerFactory entityManagerFactory;

	public CartItemDBManager() {

		/**
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();

	}

	/**
	 * This is used to add the items to cart, so it will add a row in cart_item
	 * table and map it to current cart of the customer
	 * 
	 * @param quantity
	 *            The quantity that customent wants to add
	 * @param price
	 *            price of the item
	 * @param tax
	 *            tax for the item
	 * @param note
	 *            any special note as needed
	 * @param cart_id
	 *            id of the cart that the item will be added
	 * @param product_id
	 *            id of the product to map it to cart item
	 */
	public void addCartItem(int quantity, float price, float tax, String note, Cart cart_id, Product product_id) {
		CartItem cartItem = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			/*
			 * search if the item already exits in cart then update only the
			 * quantity by 1
			 */
			Query query = entityManager.createQuery("SELECT c FROM CartItem c WHERE product_id=" + product_id.getId()
					+ " AND " + "cart_id=" + cart_id.getId());

			/*
			 * get the result list by executing the query
			 * 
			 * NOTE: There will be only one result that will have same product
			 * in one cart only the quantity will be change. We can reduce the
			 * duplicates by doing so.
			 */
			List<CartItem> cartItemList = (List<CartItem>) query.getResultList();

			/*
			 * if the item is not in the table then create a new item else get
			 * that item update the quantity ad commit the changes to database
			 */
			if (cartItemList.size() == 0)
				cartItem = new CartItem(quantity, (quantity * price), tax, note, cart_id, product_id);
			else {
				cartItem = cartItemList.get(0);
				cartItem.setQuantity(cartItemList.get(0).getQuantity() + 1);
				cartItem.setPrice(cartItemList.get(0).getQuantity() * price);
			}

			entityManager.persist(cartItem);
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}
	}

	/**
	 * This will remove the item from cart as requested by user
	 * 
	 * @param quantity
	 *            the quantity that customer wants to remove
	 * @param cart
	 *            the cart that item will be removed from
	 * @param product
	 *            the product that need to be removed
	 */
	public void removeCartItem(int quantity, Cart cart, Product product) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			/*
			 * search if the item already exits in cart then update only the
			 * quantity by 1
			 */
			Query query = entityManager.createQuery("SELECT c FROM CartItem c WHERE product_id=" + product.getId()
					+ " AND " + "cart_id=" + cart.getId());

			/*
			 * get the result list by executing the query
			 * 
			 * NOTE: There will be only one result that will have same product
			 * in one cart only the quantity will be change. We can reduce the
			 * duplicates by doing so.
			 */
			List<CartItem> cartItemList = (List<CartItem>) query.getResultList();

			/*
			 * if quantity becomes is one and user selects to remove one, then
			 * delete the item from cart_item in database else just decrease the
			 * quantity by one
			 */
			if (cartItemList.get(0).getQuantity() == 1)
				entityManager.remove(cartItemList.get(0));
			else {
				cartItemList.get(0).setQuantity(cartItemList.get(0).getQuantity() - 1);
				cartItemList.get(0).setPrice(cartItemList.get(0).getPrice() - product.getSelling_price());
				entityManager.persist(cartItemList.get(0));
			}
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}

	}

	/**
	 * This helps to get the list of cart items related to particular id
	 * 
	 * @param cartId
	 *            the id of the cart to get the list of items for
	 * @return the list of cart items
	 */
	public List<CartItem> getCartItemListFromCartId(int cartId) {
		List<CartItem> cartItemList = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			/*
			 * Get the list of items
			 */
			Query query = entityManager.createQuery("SELECT c FROM CartItem c WHERE cart_id=" + cartId);
			cartItemList = (List<CartItem>) query.getResultList();
		} finally {

			entityManager.close();

		}
		return cartItemList;
	}

}
