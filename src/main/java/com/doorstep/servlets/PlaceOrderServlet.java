package com.doorstep.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.doorstep.controllers.PersonManager;
import com.doorstep.controllers.ProductDBManager;
import com.doorstep.dao.CartItem;
import com.doorstep.dao.Order;
import com.doorstep.dao.Person;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@WebServlet(asyncSupported = true, urlPatterns = { "/doorstep/PlaceOrderServlet" })
public class PlaceOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public PlaceOrderServlet() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * This will be used for Order Review, so order wont be placed just the
		 * information will be fetched from database for user to get reviewed
		 */

		/*
		 * this will return a session if there is a session otherwise reutrn
		 * null, it wont create a new session if true is not passed as parameter
		 * in getSession
		 */
		HttpSession session = request.getSession();

		try {
			/*
			 * first convert the request object to json object
			 */

			JSONObject reviewOrderDetails_response = new JSONObject();

			/*
			 * Now fill up the order details
			 */

			/*
			 * Fill up the person's delivery details, like address
			 */
			PersonManager personManager = (PersonManager) session.getAttribute("personManager");
			Person person = personManager.getPersonDetails();

			reviewOrderDetails_response.put("delivery_details", createDeliveryDetailsObject(person));
			/*
			 * Return the response to client
			 */
			PrintWriter sendResponse = response.getWriter();
			sendResponse.print(reviewOrderDetails_response);
			sendResponse.flush();
		} catch (JSONException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader reader = new BufferedReader(request.getReader());
		try {
			/*
			 * This will actually place the order
			 */

			// Create a session object if it is already not created.
			/*
			 * this will return a session if there is a session otherwise reutrn
			 * null, it wont create a new session if true is not passed as
			 * parameter in getSession
			 */
			HttpSession session = request.getSession();

			/*
			 * first convert the request object to json object
			 */
			JSONObject orderDetails_request = new JSONObject(reader.readLine());

			JSONObject orderDetails_response = new JSONObject();

			/*
			 * Now fill up the order details
			 */

			/*
			 * Fill up the person's delivery details, like address
			 */
			PersonManager personManager = (PersonManager) session.getAttribute("personManager");
			/*
			 * get the current order details
			 */
			Order order = personManager.storeOrderDetails(orderDetails_request.getString("address"),
					orderDetails_request.getString("city"), orderDetails_request.getString("country"),
					orderDetails_request.getString("postal code"));

			/*
			 * get the details of the person placing the order
			 */
			Person person = personManager.getPersonDetails();

			/*
			 * get the items of the person to be delivered
			 */
			List<CartItem> cartItems = personManager.getCartItemListFromCartId();

			/*
			 * Now create Order object followed by delivery details object and
			 * then items in the cart object that are to be delivered
			 */
			orderDetails_response.put("items", createItemsDetailsObject(cartItems));
			orderDetails_response.put("order_details", createOrderObject(order));
			orderDetails_response.put("delivery_details", createDeliveryDetailsObject(person));

			/*
			 * Once the objects are constructed checkout the current cart and
			 * assign a new cart to the person
			 */
			personManager.checkoutCart();

			sendEmail(person.getEmail(), order.getDelivery_date());

			/*
			 * Return the response to client
			 */
			PrintWriter sendResponse = response.getWriter();
			sendResponse.print(orderDetails_response);
			sendResponse.flush();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendEmail(String email, Date date) {
		/*
		 * Used to send Email once the order is confirmed
		 */
		Email from = new Email("doorstepgrocerconfirmation@gmail.com");
		String subject = "Order Placed";

		Email to = new Email(email);
		Content content = new Content("text/plain", "Your Order will be delivered on " + date);

		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid("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");
		Request request = new Request();
		try {

			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();

			Response response = sg.api(request);
			System.out.println(response.statusCode);
			System.out.println(response.body);
			System.out.println(response.headers);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *
	 * @param cartItems
	 * @return
	 */
	private JSONObject createItemsDetailsObject(List<CartItem> cartItems) {
		ProductDBManager productDBManager = new ProductDBManager();

		// JSONObject itemsDetailsObject = new JSONObject();
		JSONObject items = new JSONObject();
		try {
			for (int i = 0; i < cartItems.size(); i++) {
				JSONObject singleObject = new JSONObject();
				singleObject.put("name",
						productDBManager.serachProductOnId(cartItems.get(i).getProduct_id().getId()).getProduct_name());
				singleObject.put("quantity", cartItems.get(i).getQuantity());
				singleObject.put("price", cartItems.get(i).getPrice());
				items.put("" + (i + 1) + "", singleObject);
			}
			// itemsDetailsObject.put("items", items);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return items;
	}

	private JSONObject createDeliveryDetailsObject(Person person) {
		// JSONObject deliveryDetailsObject = new JSONObject();

		JSONObject details = new JSONObject();

		try {
			details.put("address", person.getAddress());
			details.put("city", person.getCity());
			details.put("country", person.getCountry());
			details.put("postal_code", person.getPostalcode());
			details.put("delivered_to", person.getFirst_name() + " " + person.getLast_name());
			details.put("phone", person.getPhone());
			details.put("email", person.getEmail());
			// deliveryDetailsObject.put("delivery details", details);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return details;
	}

	private JSONObject createOrderObject(Order order) {
		// JSONObject orderObject = new JSONObject();

		JSONObject details = new JSONObject();

		try {
			details.put("created_on", order.getCreated_date());
			details.put("delivery_date", order.getDelivery_date());
			details.put("total_price", order.getTotal_price());
			details.put("total_tax", order.getTax());
			details.put("delivery_charge", order.getDelivery_charge());
			details.put("order_number", order.getOrder_number());

			// orderObject.put("order details", details);
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return details;
	}

}
