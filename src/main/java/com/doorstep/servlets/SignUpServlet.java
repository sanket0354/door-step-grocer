package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.doorstep.controllers.EntityManagerFactoryManager;
import com.doorstep.controllers.PersonManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/doorstep/SignUpServlet" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SignUpServlet() {
		
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
		HttpSession session = request.getSession(true);

		try {

			/*
			 * Get whatever is in the request and store it in reader
			 */
			BufferedReader reader = request.getReader();
			/*
			 * Create a JSON object by reading the data in reader
			 */
			JSONObject signUpDetails = new JSONObject(reader.readLine());

			/*
			 * this will manage interactions with dao which then will map to
			 * database
			 */
			PersonManager personManager = new PersonManager();

			/*
			 * add the user information to database
			 */
			boolean status = personManager.addPerson(signUpDetails.getString("first_name"),
					signUpDetails.getString("last_name"), signUpDetails.getString("email"),
					signUpDetails.getString("password"), signUpDetails.getString("phone"));
			/*
			 * now create a response object for web browser
			 */
			JSONObject createResponseObject = new JSONObject();
			/*
			 * for now if everything has been added fine in to the database then
			 * we just return true for the script to know
			 */
			createResponseObject.put("status", status);
			PrintWriter sendResponse = response.getWriter();
			sendResponse.print(createResponseObject);
			sendResponse.flush();

			/*
			 * Now that the response is sent we create the cart we dont do this
			 * before because this will increase the amount of time in returning
			 * the response, so after we give the response it will continue in
			 * background
			 */

			if (status == true)
				/*
				 * Set the attribute so that it can be accessed by multiple
				 * servlets
				 */
				session.setAttribute("personManager", personManager);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
