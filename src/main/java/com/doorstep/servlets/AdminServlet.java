package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.doorstep.controllers.PersonDBManager;
import com.doorstep.controllers.PersonManager;
import com.doorstep.controllers.PlaceOrderDBManager;
import com.doorstep.dao.Order;
import com.doorstep.dao.Person;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject usersJson = new JSONObject();

		PersonDBManager personDBManager = new PersonDBManager();
		List<Person> users = personDBManager.getAllUsers();

		usersJson = getAllUserJson(users);

		PrintWriter sendResponse = response.getWriter();
		sendResponse.print(usersJson);
		sendResponse.flush();

	}

	public JSONObject getAllUserJson(List<Person> users) {
		JSONObject usersJson = new JSONObject();
		try {
			for (int i = 0; i < users.size(); i++) {
				JSONObject user = new JSONObject();

				user.put("firstName", users.get(i).getFirst_name());

				user.put("lastName", users.get(i).getLast_name());
				user.put("email", users.get(i).getEmail());
				user.put("address", users.get(i).getAddress());
				user.put("city", users.get(i).getCity());
				user.put("postalCode", users.get(i).getPostalcode());
				user.put("country", users.get(i).getCountry());
				user.put("driver", users.get(i).isIs_driver());
				user.put("admin", users.get(i).isIs_admin());
				usersJson.put("" + (i + 1) + "", user);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usersJson;
	}

	public JSONObject getAllOrderJson(List<Order> orders) {
		JSONObject ordersJson = new JSONObject();
		PersonDBManager personDBManager = new PersonDBManager();
		try {
			for (int i = 0; i < orders.size(); i++) {
				JSONObject order = new JSONObject();

				order.put("orderNumber", orders.get(i).getId());
				order.put("cartId", orders.get(i).getCart_id().getId());
				order.put("orderDate", orders.get(i).getCreated_date());
				order.put("deliverDate", orders.get(i).getDelivery_date());
				order.put("amount", orders.get(i).getTotal_price());
				order.put("OrderedBy", personDBManager.getPerson(orders.get(i).getPerson_id().getId()).getFirst_name()
						+ " " + personDBManager.getPerson(orders.get(i).getPerson_id().getId()).getLast_name());

				ordersJson.put("" + (i + 1) + "", order);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ordersJson;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Get whatever is in the request and store it in reader
		 */
		BufferedReader reader = request.getReader();

		JSONObject responseJson = new JSONObject();

		try {
			JSONObject requestObject = new JSONObject(reader.readLine());
			PersonDBManager personDBManager = new PersonDBManager();
			if ("changeAdminState".equals(requestObject.getString("operation"))) {
				personDBManager.updateAdminState(requestObject.getString("email"));
				responseJson = getAllUserJson(personDBManager.getAllUsers());
			} else if ("changeDriverState".equals(requestObject.getString("operation"))) {
				personDBManager.updateDriverState(requestObject.getString("email"));
				responseJson = getAllUserJson(personDBManager.getAllUsers());
			} else if ("getOrders".equals(requestObject.getString("operation"))) {
				PlaceOrderDBManager placeOrderDBManager = new PlaceOrderDBManager();
				List<Order> orders = placeOrderDBManager.getAllOrders();
				responseJson = getAllOrderJson(orders);
			} else if ("orderItems".equals(requestObject.getString("operation"))) {
				PersonManager personManager = new PersonManager();
				PreviousOrdersServlet previousOrdersServlet = new PreviousOrdersServlet();
				responseJson = previousOrdersServlet.createItemListObject(
						personManager.getCartItemList(Integer.parseInt(requestObject.getString("cartId"))));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter sendResponse = response.getWriter();
		sendResponse.print(responseJson);
		sendResponse.flush();
	}

}
