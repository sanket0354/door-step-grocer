package com.doorstep.controllers;

import java.util.Collection;
import java.util.List;

import com.doorstep.dao.Cart;
import com.doorstep.dao.CartItem;
import com.doorstep.dao.Order;
import com.doorstep.dao.Person;
import com.doorstep.dao.Product;

/**
 * This class will make our function easier - we can store the id of person here
 * so we can access database directly using id, and not write queries - This
 * will make application fast, as it will reduce the amount of time we need to
 * access the database - it will also consist current cart, which will contain
 * the current cart id will gain will improve the speed of application
 *
 *
 */
public class PersonManager {

	private int personID;
	private String personEmail;
	private Cart currentCart;

	/**
	 * 
	 * @param first_name
	 *            first name of user
	 * @param last_name
	 *            last name of usr
	 * @param email
	 *            user email
	 * @param password
	 *            user password
	 * @param phone
	 *            user phone
	 * @return
	 */
	public boolean addPerson(String first_name, String last_name, String email, String password, String phone) {

		/*
		 * This will build the session factory, look in to class for details
		 */
		SignUpDBManager DBM = new SignUpDBManager();

		int personID = DBM.addUserInfo(first_name, last_name, email, password, phone);

		/*
		 * if there was something wrong in the database return false
		 */
		if (personID == -1)
			return false;

		/*
		 * otherwise assign the ID to class for future use
		 *
		 */
		this.personID = personID;
		this.personEmail = email;

		/*
		 * Now as this method will be running as a part of sign up process we
		 * will create a cart for the person
		 */
		CartDBManager cartDBManager = new CartDBManager();
		this.currentCart = cartDBManager.createNewCart(this.personID);

		return true;
	}

	/**
	 * Checkout the current cart after the order is placed and assign the person
	 * a new cart to add items to
	 */
	public void checkoutCart() {
		CartDBManager cartDBManager = new CartDBManager();
		cartDBManager.checkoutCart(this.currentCart.getId());

		/*
		 * Search the current active cart for the person
		 */
		Cart cart = cartDBManager.searchActiveCart(this.personID);

		/*
		 * If no cart is found then create a new cart
		 */
		if (cart == null)
			cart = cartDBManager.createNewCart(this.personID);

		this.currentCart = cart;

	}

	/**
	 * This will create new cart for customer but first verify his login
	 * 
	 * @param email
	 *            user email
	 * @param password
	 *            user password
	 * @return true or false depending on cart created or not
	 */
	public boolean createCart(String email, String password) {

		LoginDBManager loginDBManager = new LoginDBManager();
		/*
		 * Verify User login
		 */
		Person person = loginDBManager.verifyUserLogin(email, password);

		/*
		 * if there is no such person in database return false to notify client
		 * through servlet
		 */
		if (person == null)
			return false;

		/*
		 * check if person already has cart and is not checked out yet
		 */
		CartDBManager cartDBManager = new CartDBManager();

		/*
		 * Search the current active cart for the person
		 */
		Cart cart = cartDBManager.searchActiveCart(person.getId());

		/*
		 * If no cart is found then create a new cart
		 */
		if (cart == null)
			cart = cartDBManager.createNewCart(person.getId());

		/*
		 * Store all the information for future use and minimize database
		 * interaction
		 */
		this.personEmail = email;
		this.currentCart = cart;
		this.personID = person.getId();

		return true;
	}

	/**
	 * takes parameters from servlet and passes necessary parameters to the
	 * DBManagers and completes the process of adding items to cart
	 * 
	 * @param productName
	 *            name of the product that user wants to add to cart
	 * @param quantity
	 *            number of item the user wants to add
	 * @return true or false depending on operation was successfull or not
	 */
	public boolean addCartItem(String productName, int quantity) {
		/*
		 * Search the product first as we will need its information to map it
		 * into CartItem table
		 */
		ProductDBManager productDBManager = new ProductDBManager();
		Product product = productDBManager.searchProduct(productName);

		/*
		 * add the item to cartItem table
		 */
		CartItemDBManager cartItemDBManager = new CartItemDBManager();
		cartItemDBManager.addCartItem(quantity, product.getSelling_price(), product.getTaxable(), "", this.currentCart,
				product);

		return true;
	}

	/**
	 * takes parameters from servlet and passes necessary parameters to the
	 * DBManagers and completes the process of removing items to cart
	 * 
	 * @param productName
	 *            the name of the product to be removed
	 * @param quantity
	 *            the quantity user wants to remove
	 * @return true or false depending on operation was successfull or not
	 */
	public boolean removeCartItem(String productName, int quantity) {
		/*
		 * Search the product first as we will need its information to map it
		 * into CartItem table
		 */
		ProductDBManager productDBManager = new ProductDBManager();
		Product product = productDBManager.searchProduct(productName);

		/*
		 * stimulates removing item
		 */
		CartItemDBManager cartItemDBManager = new CartItemDBManager();
		cartItemDBManager.removeCartItem(quantity, this.currentCart, product);

		return true;
	}

	/**
	 * get the list of items in the current Cart
	 * 
	 * @return the cartItems list
	 */
	public List<CartItem> getCartItemListFromCartId() {
		CartItemDBManager cartItemDBManager = new CartItemDBManager();
		List<CartItem> cartItems = cartItemDBManager.getCartItemListFromCartId(currentCart.getId());
		return cartItems;

	}

	/**
	 * To get total number items in current cart
	 */
	public int getTotalItemsInCart() {
		int totalItems = 0;
		CartItemDBManager cartItemDBManager = new CartItemDBManager();
		List<CartItem> cartItems = cartItemDBManager.getCartItemListFromCartId(currentCart.getId());

		for (CartItem cartItem : cartItems)
			totalItems += cartItem.getQuantity();

		return totalItems;

	}

	/**
	 * This will update the order details of customer, and return the order
	 * object to construct JSON object to return to display on customer view
	 */
	public Order storeOrderDetails(String address, String city, String country, String postalCode) {

		PlaceOrderDBManager placeOrderDBManager = new PlaceOrderDBManager();
		Order order = placeOrderDBManager.storePersonOrderDetails(this.personID, this.currentCart, address, city,
				country, postalCode);

		return order;

	}

	/**
	 * get the person who is logged in details for delivery
	 */
	public Person getPersonDetails() {
		PersonDBManager personDBManger = new PersonDBManager();
		return personDBManger.getPerson(this.personID);

	}

	/**
	 * Get the orders of the current person
	 * 
	 * @return list or orders of particular person
	 */
	public List<Order> getPersonOrders() {
		PlaceOrderDBManager placeOrderDBManager = new PlaceOrderDBManager();
		List<Order> orders = placeOrderDBManager.getOrders(this.personID);
		return orders;
	}

	/**
	 * get the list of the items of cartId required
	 * 
	 * @param cartId
	 * @return
	 */
	public List<CartItem> getCartItemList(int cartId) {
		CartItemDBManager cartItemDBManager = new CartItemDBManager();
		List<CartItem> cartItems = cartItemDBManager.getCartItemListFromCartId(cartId);
		return cartItems;

	}

	public void updatePersonAddress(String address, String city, String country, String postalCode) {
		PlaceOrderDBManager placeOrderDBManager = new PlaceOrderDBManager();
		placeOrderDBManager.updatePersonAddress(this.personID, this.currentCart, address, city, country, postalCode);
	}

	public Cart getCurrentCart() {
		return this.currentCart;
	}

	public boolean checkAdminstate() {
		PersonDBManager personDBManager = new PersonDBManager();
		Person person = personDBManager.getPerson(this.personID);

		if (person.isIs_admin())
			return true;

		return false;
	}

}
