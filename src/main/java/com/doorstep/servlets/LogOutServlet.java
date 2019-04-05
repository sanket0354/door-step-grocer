package com.doorstep.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(asyncSupported = true, urlPatterns = { "/doorstep/LogOutServlet" })
public class LogOutServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public LogOutServlet(){
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		boolean status;
		/*
		 * invalidate the current user session if its not null
		 */
		if(session == null)
			status = false;
		else{
			session.invalidate();
			status = true;
		}
		
		/*
		 * now create a response object for web browser
		 */
		JSONObject createResponseObject = new JSONObject();
		/*
		 * for now if everything has been added fine in to the database then
		 * we just return true for the script to know
		 */
		try {
			createResponseObject.put("status", status);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter sendResponse = response.getWriter();
		sendResponse.print(createResponseObject);
		sendResponse.flush();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
}
