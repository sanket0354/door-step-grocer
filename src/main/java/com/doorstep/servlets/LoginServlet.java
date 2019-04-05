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
import com.doorstep.controllers.LoginDBManager;
import com.doorstep.controllers.PersonManager;;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/doorstep/LoginServlet" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
		
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
			JSONObject LoginDetails = new JSONObject(reader.readLine());

			/*
			 * Create a person manager which will then manage all the
			 * interactions with database
			 */
			PersonManager personManager = new PersonManager();

			/*
			 * this will verify the login first and the create cart accordingly
			 * and return status true o false
			 */
			boolean status = personManager.createCart(LoginDetails.getString("email"),
					LoginDetails.getString("password"));

			/*
			 * now create a response object for web browser
			 */
			JSONObject createResponseObject = new JSONObject();
			/*
			 * for now if evertting has been added fine in to the database then
			 * we just return true for the script to know
			 */
			createResponseObject.put("status", status);
			
			if (status == true)
				/*
				 * Set the attribute so that it can be accessed by multiple
				 * servlets
				 */
				session.setAttribute("personManager", personManager);
			
			PrintWriter sendResponse = response.getWriter();
			sendResponse.print(createResponseObject);
			sendResponse.flush();

			

		} catch (Exception e) {
			// will help in debugging
			System.out.println("ERRRO IN LOGINSERVLET");
			e.printStackTrace();
		}

	}

}
