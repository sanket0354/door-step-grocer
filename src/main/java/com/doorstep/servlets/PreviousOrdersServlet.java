package com.doorstep.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.doorstep.controllers.PersonManager;
import com.doorstep.controllers.ProductDBManager;
import com.doorstep.dao.CartItem;
import com.doorstep.dao.Order;
import com.doorstep.dao.Person;

/**
 * Servlet implementation class PreviousOrdersServlet
 */
public class PreviousOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Locale languageSelected = Locale.ENGLISH;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PreviousOrdersServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject previousOrders = new JSONObject();

		HttpSession session = request.getSession();

		PersonManager personManager = (PersonManager) session.getAttribute("personManager");

		List<Order> orders = personManager.getPersonOrders();
		/*
		 * get the details of the person placing the order
		 */
		Person person = personManager.getPersonDetails();
		try {
			/*
			 * loop through all order orjects to create a JSON object containing
			 * all details of customer's previous orders
			 */
			for (int i = 0; i < orders.size(); i++) {
				List<CartItem> cartItems = personManager.getCartItemList(orders.get(i).getCart_id().getId());
				JSONObject singleOrder = new JSONObject();
				singleOrder.put("items", createItemListObject(cartItems));
				singleOrder.put("order_number", orders.get(i).getOrder_number());
				singleOrder.put("order_date", orders.get(i).getCreated_date());
				singleOrder.put("delivery_date", orders.get(i).getDelivery_date());
				singleOrder.put("person_name", person.getFirst_name() + " " + person.getLast_name());
				singleOrder.put("delivery_address", orders.get(i).getComplete_address());
				singleOrder.put("total_price", orders.get(i).getTotal_price());
				previousOrders.put("" + (i + 1) + "", singleOrder);
			}

		} catch (JSONException ex) {
			ex.printStackTrace();
		}

		PrintWriter sendResponse = response.getWriter();
		sendResponse.print(previousOrders);
		sendResponse.flush();

	}

	/**
	 * used to create the items list JSON object for previous orders
	 * 
	 * @param cartItems
	 * @return
	 */
	public JSONObject createItemListObject(List<CartItem> cartItems) {
		JSONObject cartItemList = new JSONObject();
		ProductDBManager productDBManager = new ProductDBManager();

		/*
		 * To display the previous order items in the language selected by user
		 */
		ResourceBundle previousOrderDisplayProductBundle = ResourceBundle.getBundle("StoreFront", languageSelected);

		try {
			/**
			 * loop through all items related to particular order and make the
			 * object
			 */
			for (int i = 0; i < cartItems.size(); i++) {
				JSONObject singleItem = new JSONObject();
				singleItem.put("name",
						previousOrderDisplayProductBundle.getString(productDBManager.serachProductOnId(cartItems.get(i).getProduct_id().getId()).getProduct_name()));
				singleItem.put("quantity", cartItems.get(i).getQuantity());
				singleItem.put("price", cartItems.get(i).getPrice());
				cartItemList.put("" + (i + 1) + "", singleItem);
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return cartItemList;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
