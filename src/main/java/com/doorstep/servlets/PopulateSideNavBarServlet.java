package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.doorstep.controllers.CategoryDBManager;
import com.doorstep.controllers.PersonManager;
import com.doorstep.controllers.SubCategoryDBManager;
import com.doorstep.dao.Category;
import com.doorstep.dao.SubCategory;

@WebServlet(asyncSupported = true, urlPatterns = { "/doorstep/PopulateSideNavBarServlet" })
public class PopulateSideNavBarServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PopulateSideNavBarServlet() {

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

		BufferedReader reader = request.getReader();

		try {
			JSONObject requestObject = new JSONObject(reader.readLine());
			JSONObject responseObject = new JSONObject();

			/*
			 * To build the side nav bar depending on the language user chooses
			 */
			if ("English".equals(requestObject.getString("language"))) {
				responseObject = buildNavBarObject(Locale.ENGLISH);
			} else if ("French".equals(requestObject.getString("language"))) {
				responseObject = buildNavBarObject(Locale.FRENCH);

			}

			HttpSession session = request.getSession();
			PersonManager personManager = (PersonManager) session.getAttribute("personManager");

			
			
			try {
				responseObject.put("isAdmin", personManager.checkAdminstate());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			PrintWriter sendResponse = response.getWriter();
			sendResponse.print(responseObject);
			sendResponse.flush();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * build the side nav bar object to populate it
	 * 
	 * @param languageSelected
	 *            the labguage that user wants
	 * @return the JSON object created
	 */
	protected JSONObject buildNavBarObject(Locale languageSelected) {

		ResourceBundle navBundle = ResourceBundle.getBundle("SideNavBar", languageSelected);
		JSONObject navBarObject = new JSONObject();

		try {
			CategoryDBManager categoryDBManager = new CategoryDBManager();
			List<Category> allCategory = categoryDBManager.getAllCategory();

			SubCategoryDBManager subCategoryDBManager = new SubCategoryDBManager();

			/*
			 * loop through category and subcategory to create the object so it
			 * can populate everything on side nav bar
			 */
			for (int i = 0; i < allCategory.size(); i++) {
				JSONObject category = new JSONObject();
				category.put("name", navBundle.getString(allCategory.get(i).getCategory_name()));

				List<SubCategory> allSubCategory = subCategoryDBManager.getAllSubCategory(allCategory.get(i).getId());
				JSONObject subCategory = new JSONObject();
				for (int j = 0; j < allSubCategory.size(); j++)
					subCategory.put("" + j + "", navBundle.getString(allSubCategory.get(j).getSub_category_name()));

				category.put("numSubCategory", allSubCategory.size());
				category.put("subCategory", subCategory);

				navBarObject.put("" + i + "", category);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return navBarObject;
	}
}
