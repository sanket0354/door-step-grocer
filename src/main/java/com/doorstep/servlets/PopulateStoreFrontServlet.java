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
import com.doorstep.controllers.ProductDBManager;
import com.doorstep.controllers.SubCategoryDBManager;
import com.doorstep.dao.Category;
import com.doorstep.dao.Product;
import com.doorstep.dao.SubCategory;

@WebServlet(asyncSupported = true, urlPatterns = { "/doorstep/PopulateStoreFrontServlet" })
public class PopulateStoreFrontServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PopulateStoreFrontServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject responseObject = buildStoreFrontObject(Locale.ENGLISH);

		PrintWriter sendResponse = response.getWriter();
		sendResponse.print(responseObject);
		sendResponse.flush();
	}

	protected JSONObject buildStoreFrontObject(Locale languageSelected) {
		JSONObject responseObject = new JSONObject();

		try {

			ResourceBundle productBundle = ResourceBundle.getBundle("StoreFront", languageSelected);
			ResourceBundle navBundle = ResourceBundle.getBundle("SideNavBar", languageSelected);

			CategoryDBManager categoryDBManager = new CategoryDBManager();
			List<Category> allCategory = categoryDBManager.getAllCategory();

			for (int k = 0; k < allCategory.size(); k++) {

				JSONObject allInfoOnProducts = new JSONObject();

				SubCategoryDBManager subCategoryDBManager = new SubCategoryDBManager();
				List<SubCategory> allSubCategory = subCategoryDBManager.getAllSubCategory(allCategory.get(k).getId());

				if (allSubCategory.size() == 0) {
					ProductDBManager productDBManager = new ProductDBManager();
					List<Product> products = productDBManager.getProducts(allCategory.get(k).getId());

					JSONObject allProducts = new JSONObject();

					for (int i = 0; i < products.size(); i++) {
						JSONObject singleProduct = new JSONObject();
						singleProduct.put("name", productBundle.getString(products.get(i).getProduct_name()));
						singleProduct.put("price", products.get(i).getSelling_price());
						singleProduct.put("url", products.get(i).getImg_url());
						allProducts.put("" + (i + 1) + "", singleProduct);
					}

					allInfoOnProducts.put("categoryName", navBundle.getString(allCategory.get(k).getCategory_name()));
					allInfoOnProducts.put("numSubCategory", allSubCategory.size());
					allInfoOnProducts.put("numProducts", products.size());
					allInfoOnProducts.put("products", allProducts);

				} else {

					allInfoOnProducts.put("categoryName", navBundle.getString(allCategory.get(k).getCategory_name()));
					allInfoOnProducts.put("numSubCategory", allSubCategory.size());
					JSONObject SubCatObject = new JSONObject();
					for (int j = 0; j < allSubCategory.size(); j++) {

						ProductDBManager productDBManager = new ProductDBManager();
						List<Product> productsForSubCategory = productDBManager.getProductsBasedOnCatIdAndSubCatId(
								allCategory.get(k).getId(), allSubCategory.get(j).getId());

						JSONObject allProducts = new JSONObject();

						for (int i = 0; i < productsForSubCategory.size(); i++) {
							JSONObject singleProduct = new JSONObject();
							singleProduct.put("name",
									productBundle.getString(productsForSubCategory.get(i).getProduct_name()));
							singleProduct.put("price", productsForSubCategory.get(i).getSelling_price());
							singleProduct.put("url", productsForSubCategory.get(i).getImg_url());
							allProducts.put("" + (i + 1) + "", singleProduct);
						}

						allProducts.put("numProducts", productsForSubCategory.size());

						SubCatObject.put(navBundle.getString(allSubCategory.get(j).getSub_category_name()),
								allProducts);

					}

					allInfoOnProducts.put("products", SubCatObject);
				}

				responseObject.put("" + (k + 1) + "", allInfoOnProducts);
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}

		return responseObject;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
