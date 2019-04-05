package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(asyncSupported = true, urlPatterns = { "/doorstep/InternationalizationServlet" })
public class InternationalizationServlet extends HttpServlet {

	public InternationalizationServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * Get whatever is in the request and store it in reader
		 */
		BufferedReader reader = request.getReader();

		JSONObject responseObject = new JSONObject();

		PopulateStoreFrontServlet populateStoreFrontServlet = new PopulateStoreFrontServlet();

		/*
		 * depending on the language user wants send the response object
		 */
		try {
			JSONObject languageSelected = new JSONObject(reader.readLine());

			if ("English".equals(languageSelected.getString("language"))) {
				CartServlet.languageSelected = Locale.ENGLISH;
				PreviousOrdersServlet.languageSelected = Locale.ENGLISH;
				responseObject = populateStoreFrontServlet.buildStoreFrontObject(Locale.ENGLISH);
			} else if ("French".equals(languageSelected.getString("language"))) {
				CartServlet.languageSelected = Locale.FRENCH;
				PreviousOrdersServlet.languageSelected = Locale.FRENCH;
				responseObject = populateStoreFrontServlet.buildStoreFrontObject(Locale.FRENCH);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		PrintWriter sendResponse = response.getWriter();
		sendResponse.print(responseObject);
		sendResponse.flush();

	}

}
