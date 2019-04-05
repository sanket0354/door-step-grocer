package com.doorstep.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.doorstep.controllers.EntityManagerFactoryManager;

public class InitializationServlet extends HttpServlet {

	/**
	 * Initialize the web application
	 */
	public void init() throws ServletException {
		EntityManagerFactoryManager.populateTables();
	}

	public void destroy() {
		System.out.println("Destroying the Entity Manager Factory...");
		EntityManagerFactoryManager.getEntityManagerFactory().close();
	}

}
