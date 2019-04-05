package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.doorstep.controllers.PersonManager;
import com.doorstep.controllers.ProductDBManager;
import com.doorstep.dao.CartItem;
import com.doorstep.process.Constants;
import com.doorstep.process.ExchangeRate;

@WebServlet(asyncSupported = true, urlPatterns = { "/doorstep/CartServlet" })
public class CartServlet extends HttpServlet {

	static Locale languageSelected = Locale.ENGLISH;
	static String currencySelected = Constants.CURRENCY_USD;

	public CartServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Create a session object if it is already not created.
		HttpSession session = request.getSession(true);
		boolean status = false;
		/*
		 * Get whatever is in the request and store it in reader
		 */
		BufferedReader reader = request.getReader();

		/*
		 * to reconvert the products that are added or removed to english
		 */
		ResourceBundle productBundle = ResourceBundle.getBundle("StoreFront", Locale.ENGLISH);

		/*
		 * now create a response object for web browser
		 */
		JSONObject createResponseObject = new JSONObject();
		/*
		 * Create a JSON object by reading the data in reader
		 */
		try {
			JSONObject itemToCart = new JSONObject(reader.readLine());

			PersonManager personManager = (PersonManager) session.getAttribute("personManager");

			/*
			 * If operation is add, that is if user wants to add item to cart
			 * then we call add method from PersonManager class
			 * 
			 * else If operation is remove, that is if user wants to remove item
			 * from cart then we call remove method from PersonManager class
			 * 
			 * else If the operatio is display, create the JSON object needed to
			 * display
			 * 
			 * else If operatio is totalItems, fetch the total items in cart and
			 * create the JSON oject
			 */
			if (itemToCart.getString("operation").equals("add")) {
				status = personManager.addCartItem(
						productBundle.getString(itemToCart.getJSONObject("item").getString("name")),
						Integer.parseInt(itemToCart.getJSONObject("item").getString("quantity")));
				createResponseObject.put("status", status);

			} else if ((itemToCart.getString("operation").equals("remove"))) {
				status = personManager.removeCartItem(
						productBundle.getString(itemToCart.getJSONObject("item").getString("name")),
						Integer.parseInt(itemToCart.getJSONObject("item").getString("quantity")));
				createResponseObject.put("status", status);

			} else if (itemToCart.getString("operation").equals("display"))
				createResponseObject = createDisplayJSONObject(personManager);

			else if (itemToCart.getString("operation").equals("totalItems")) {
				createResponseObject.put("status", true);
				createResponseObject.put("totalItems", personManager.getTotalItemsInCart());
			}
			/*
			 * return the response to the client in for of JSON object
			 */
			PrintWriter sendResponse = response.getWriter();
			sendResponse.print(createResponseObject);
			sendResponse.flush();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * return the JSON obejct used to display the cart
	 * 
	 * @param personManager
	 *            the current personManager in session
	 * @return the created JSON object
	 */
	private JSONObject createDisplayJSONObject(PersonManager personManager) {
		JSONObject displayObject = new JSONObject();

		try {

			/*
			 * To display the cart in the language selected by user
			 */
			ResourceBundle cartDisplayProductBundle = ResourceBundle.getBundle("StoreFront", languageSelected);

			PersonManager person = personManager;
			/*
			 * fetch the items in cart
			 */
			ProductDBManager productDBManager = new ProductDBManager();
			List<CartItem> cartItems = person.getCartItemListFromCartId();
			float totalPrice = 0.0f;
			ExchangeRate exchangeRate = new ExchangeRate();

			/*
			 * loop through teh items in cart and make a key value pair to
			 * create JSON object
			 */
			for (int i = 0; i < cartItems.size(); i++) {
				JSONObject singleObject = new JSONObject();
				singleObject.put("name", cartDisplayProductBundle.getString(productDBManager
						.serachProductOnId(cartItems.get(i).getProduct_id().getId()).getProduct_name()));
				singleObject.put("quantity", cartItems.get(i).getQuantity());

				if (currencySelected.equals(Constants.CURRENCY_USD)) {
					NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

					singleObject.put("price", numberFormat.format(cartItems.get(i).getPrice()));

				} else if (currencySelected.equals(Constants.CURRENCY_USD_TO_EURO)) {
					NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);

					singleObject.put("price",
							numberFormat.format(exchangeRate.convertUsdToEuro(cartItems.get(i).getPrice())));
				} else if (currencySelected.equals(Constants.CURRENCY_EURO_TO_USD)) {
					NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

					// Currency usd = Currency.getInstance(Locale.US);

					singleObject.put("price", numberFormat.format(cartItems.get(i).getPrice()));

				}
				totalPrice += cartItems.get(i).getPrice();
				displayObject.put("" + (i + 1) + "", singleObject);

			}
			displayObject.put("total_price", totalPrice);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return displayObject;
	}

}
