package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.doorstep.process.Constants;

/**
 * Servlet implementation class CurrencyInternationalizationServlet
 */
public class CurrencyInternationalizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CurrencyInternationalizationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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

		JSONObject responseObject = new JSONObject();

		/*
		 * depending on the language user wants send the response object
		 */
		try {
			JSONObject languageSelected = new JSONObject(reader.readLine());

			if ("EURO".equals(languageSelected.getString("currencySelected"))) {
				CartServlet.currencySelected = Constants.CURRENCY_USD_TO_EURO;
			} else if ("USD".equals(languageSelected.getString("currencySelected"))) {
				CartServlet.currencySelected = Constants.CURRENCY_EURO_TO_USD;
			}

			responseObject.put("status", true);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		PrintWriter sendResponse = response.getWriter();
		sendResponse.print(responseObject);
		sendResponse.flush();
	}

}
