package com.doorstep.process;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

public class ProcessPayment {

	// public static void main(String[] args) {
	// ProcessPayment.processPayment("tok_1A1LSUFc6f1lZMdzgP8o1a2P", 100, "cad",
	// "test", "123");
	// }

	/*
	 * 4242 4242 4242 4242 - Valid Card
	 * 
	 * 
	 */
	public static boolean processPayment(String token, int amount, String currency, String description,
			String orderId) {

		Stripe.apiKey = "sk_test_inVa46aMRvW6HdPwKWbOOd3N";

		Map<String, String> initialMetadata = new HashMap<String, String>();
		initialMetadata.put("order_id", orderId);

		// Charge the user's card:
		Map<String, Object> chargeParameters = new HashMap<String, Object>();
		chargeParameters.put("amount", amount);
		chargeParameters.put("currency", currency);
		chargeParameters.put("description", description);
		chargeParameters.put("metadata", initialMetadata);
		chargeParameters.put("source", token);

		try {
			/*
			 * Makes an stripe API call to charge the customer with the above
			 * parameters
			 */
			Charge charge = Charge.create(chargeParameters);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
