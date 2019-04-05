package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.doorstep.controllers.PersonManager;
import com.doorstep.controllers.PlaceOrderDBManager;

/**
 * Servlet implementation class OrderAgainServlet
 */
public class OrderAgainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderAgainServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		/*
		 * Get whatever is in the request and store it in reader
		 */
		BufferedReader reader = request.getReader();
		/*
		 * Create a JSON object by reading the data in reader
		 */
		try {
			JSONObject orderAgain = new JSONObject(reader.readLine());
			PersonManager personManager = (PersonManager) session.getAttribute("personManager");
			personManager.updatePersonAddress(orderAgain.getString("address"), orderAgain.getString("city"),
					orderAgain.getString("country"), orderAgain.getString("postal code"));

			/*
			 * get the order number from request and place the order again
			 */
			PlaceOrderDBManager placeOrderDBManager = new PlaceOrderDBManager();
			placeOrderDBManager.repeatOrder(Integer.parseInt(orderAgain.getString("orderNumber")));

			JSONObject createResponseObject = new JSONObject();
			createResponseObject.put("status", true);

			PrintWriter sendResponse = response.getWriter();
			sendResponse.print(createResponseObject);
			sendResponse.flush();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
