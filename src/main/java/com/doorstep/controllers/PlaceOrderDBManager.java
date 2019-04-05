package com.doorstep.controllers;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.doorstep.dao.Cart;
import com.doorstep.dao.CartItem;
import com.doorstep.dao.Order;
import com.doorstep.dao.Person;
import com.doorstep.process.Constants;

public class PlaceOrderDBManager {
	private EntityManagerFactory entityManagerFactory;

	public PlaceOrderDBManager() {
		/**
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();

	}

	/**
	 * This one updates the order details in person table
	 * 
	 * @param personID
	 *            take the id of the customer
	 * @param currentCart
	 *            current cart of the customer
	 * @param address
	 *            address entered by the customer
	 * @param city
	 *            city of customer
	 * @param country
	 *            country of customer
	 * @param postalCode
	 *            postalcode of the address
	 * @return the order that was just added to the Orders table
	 */
	public Order storePersonOrderDetails(int personID, Cart currentCart, String address, String city, String country,
			String postalCode) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Order order = null;
		try {
			entityManager.getTransaction().begin();

			/*
			 * find the person using the id
			 */
			Person person = entityManager.find(Person.class, personID);

			/*
			 * update the information of the person with new one
			 */
			person.setAddress(address);
			person.setCity(city);
			person.setCountry(country);
			person.setPostalcode(postalCode);

			/*
			 * now create a new order for the person and map the cart id and
			 * person to whom its supposed to be delivered
			 */
			order = createNewOrder(person, currentCart);

			/*
			 * get the order list: - if null create new order list - else add
			 * the order to order list
			 */
			Set<Order> lastOrders = person.getSet_of_last_orders();
			if (lastOrders == null)
				lastOrders = new HashSet<Order>();

			lastOrders.add(order);
			person.setSet_of_last_orders(lastOrders);

			/*
			 * commit the updated changes
			 */
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}

		return order;
	}

	/**
	 * Used to create new order for customer
	 * 
	 * @param person
	 *            the customer for whom the order is palced
	 * @param cart
	 *            the cart for the order
	 * @return the order added to the table
	 */
	public Order createNewOrder(Person person, Cart cart) {

		Order order = new Order();
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			/*
			 * get the current date and set the current date
			 */
			Calendar currentDate = Calendar.getInstance();
			order.setCreated_date(currentDate.getTime());

			/*
			 * increment the date by one and set it delivery date
			 */
			currentDate.add(Calendar.DATE, 1);
			order.setDelivery_date(currentDate.getTime());

			/*
			 * get all cart items to calculate total price that the customer
			 * will pay
			 */
			CartItemDBManager cartItemDBManager = new CartItemDBManager();
			List<CartItem> cartItems = cartItemDBManager.getCartItemListFromCartId(cart.getId());

			float totalPrice = 0.0f;

			/*
			 * calculate the total price by looping through list of items
			 */
			for (CartItem item : cartItems)
				totalPrice += item.getPrice();

			/*
			 * set total price for Order object
			 */
			order.setTotal_price(totalPrice);

			/*
			 * calculate the tax amount using tax percentage as 13 percent
			 */

			float totalTax = (totalPrice * Constants.TAXABLE_PERCENT) / 100;
			/*
			 * set total tax for order object
			 */
			order.setTax(totalTax);

			/*
			 * set delivery charge,cartID and personID for order object
			 */
			order.setDelivery_charge(Constants.DELIVERY_CHARGE);
			order.setCart_id(cart);
			order.setPerson_id(person);
			order.setOrder_number(cart.getId());
			order.setComplete_address(person.getAddress() + ", " + person.getCity() + ", " + person.getCountry());

			entityManager.persist(order);
			entityManager.getTransaction().commit();
		} finally {
			/*
			 * This ensures entity manager is always closed
			 */
			entityManager.close();
		}
		return order;
	}

	/**
	 * get the list of orders of the personId passed
	 * 
	 * @param personID
	 *            the id of the person to get list of order
	 * @return the list of order
	 */
	public List<Order> getOrders(int personID) {
		List<Order> orders;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			Query query = entityManager.createQuery("SELECT o FROM Order o WHERE person_id=" + personID);
			orders = (List<Order>) query.getResultList();
		} finally {
			entityManager.close();
		}
		return orders;
	}

	/**
	 * if the users wants to repeat any previous order
	 * 
	 * @param order_number
	 *            the order number that will be repeated
	 */
	public void repeatOrder(int order_number) {
		List<Order> orders;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			/*
			 * get the order to be repeated
			 */
			Query query = entityManager.createQuery("SELECT o FROM Order o WHERE order_number=" + order_number);
			orders = (List<Order>) query.getResultList();

			/*
			 * create new order same as the order that is fetched above
			 */
			createNewOrder(orders.get(0).getPerson_id(), orders.get(0).getCart_id());

		} finally {
			entityManager.close();
		}
	}

	public void updatePersonAddress(int personID, Cart currentCart, String address, String city, String country,
			String postalCode) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		// Order order = null;
		try {
			entityManager.getTransaction().begin();

			/*
			 * find the person using the id
			 */
			Person person = entityManager.find(Person.class, personID);

			/*
			 * update the information of the person with new one
			 */
			person.setAddress(address);
			person.setCity(city);
			person.setCountry(country);
			person.setPostalcode(postalCode);
			entityManager.getTransaction().commit();

		} finally {
			entityManager.close();
		}

	}

	public List<Order> getAllOrders() {
		List<Order> orders = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			orders = entityManager.createQuery("SELECT o FROM Order o").getResultList();
		} finally {
			entityManager.close();
		}

		return orders;
	}

}
