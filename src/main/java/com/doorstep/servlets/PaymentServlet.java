package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.doorstep.controllers.PersonManager;
import com.doorstep.dao.CartItem;
import com.doorstep.process.ProcessPayment;

/**
 * Servlet implementation class PaymentServlet
 */
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String currencySelected = "cad";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaymentServlet() {
		super();
		// TODO Auto-generated constructor stub
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
		// Create a session object if it is already not created.
		HttpSession session = request.getSession();
		PersonManager personManager = (PersonManager) session.getAttribute("personManager");

		/*
		 * Get whatever is in the request and store it in reader
		 */
		BufferedReader reader = request.getReader();

		try {
			JSONObject paymentTokenObject = new JSONObject(reader.readLine());
			String paymentToken = paymentTokenObject.getString("token");

			List<CartItem> cartItems = personManager.getCartItemListFromCartId();
			int totalPrice = 0;
			for (CartItem item : cartItems)
				totalPrice += item.getPrice();

			boolean status = ProcessPayment.processPayment(paymentToken, totalPrice * 100, currencySelected, "Order Payment",
					cartItems.get(0).getCart_id().toString());

			JSONObject responseObject = new JSONObject();
			responseObject.put("status", status);

			PrintWriter out = response.getWriter();
			out.print(responseObject);
			out.close();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
