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

public class Test {

	public static void main(String[] args) {
		// Set your secret key: remember to change this to your live secret key
		// in production
		// See your keys here: https://dashboard.stripe.com/account/apikeys
		Stripe.apiKey = "sk_test_inVa46aMRvW6HdPwKWbOOd3N";

		// RequestOptions requestOptions = (new
		// RequestOptionsBuilder()).setApiKey("sk_test_inVa46aMRvW6HdPwKWbOOd3N").build();
		// Map<String, Object> chargeMap = new HashMap<String, Object>();
		// chargeMap.put("amount", 20000);
		// chargeMap.put("currency", "usd");
		// Map<String, Object> cardMap = new HashMap<String, Object>();
		// cardMap.put("number", "4242424242424242");
		// cardMap.put("exp_month", 12);
		// cardMap.put("exp_year", 2020);
		// chargeMap.put("card", cardMap);
		// try {
		// Charge charge = Charge.create(chargeMap, requestOptions);
		// System.out.println(charge);
		// } catch (StripeException e) {
		// e.printStackTrace();
		// }
		//

		// Token is created using Stripe.js or Checkout!
		// Get the payment token submitted by the form:
		String token = "tok_1A0w76Fc6f1lZMdz7uHmuB4J";

		// Charge the user's card:
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", 1234);
		params.put("currency", "cad");
		params.put("description", "Example charge");
		Map<String, String> initialMetadata = new HashMap<String, String>();
		// initialMetadata.put("order_id", 6735);
		params.put("metadata", initialMetadata);
		params.put("source", token);

		try {
			Charge charge = Charge.create(params);
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
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
