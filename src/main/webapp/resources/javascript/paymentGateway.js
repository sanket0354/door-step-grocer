/**
 * 
 */
// API key from stripe account
var stripe = Stripe('pk_test_FMSJXgpgt1GR8gGOfFk3YDiN');
var elements = stripe.elements();

// Custom styling can be passed to options when creating an Element.
var style = {
	base : {
		// Add your base input styles here. For example:
		fontSize : '16px',
		lineHeight : '24px'
	}
};

// Create an instance of the card Element
var card = elements.create('card', {
	style : style
});

// Add an instance of the card Element into the `card-element` <div>
card.mount('#card-element');

card.addEventListener('change', function(event) {
	var displayError = document.getElementById('card-errors');
	if (event.error) {
		displayError.textContent = event.error.message;
	} else {
		displayError.textContent = '';
	}
});

// Create a token or display an error the form is submitted.
var form = document.getElementById('payment-form');
form.addEventListener('submit', function(event) {
	$("#loadingPayment").show();
	$("#ProcessPaymentButton").hide();
	event.preventDefault();

	stripe.createToken(card).then(function(result) {
		if (result.error) {
			// Inform the user if there was an error
			var errorElement = document.getElementById('card-errors');
			errorElement.textContent = result.error.message;
		} else {
			console.log(result.token);

			// Send the token to your server
			stripeTokenHandler(result.token);
		}
	});
});

function stripeTokenHandler(token) {
	// Insert the token ID into the form so it gets submitted to the server
	var form = document.getElementById('payment-form');
	var hiddenInput = document.createElement('input');
	hiddenInput.setAttribute('type', 'hidden');
	hiddenInput.setAttribute('name', 'stripeToken');
	hiddenInput.setAttribute('value', token.id);

	var tokenObject = {
		"token" : token.id
	};

	var tokenObject_json = JSON.stringify(tokenObject);
	console.log(tokenObject_json);
	$
			.ajax({
				type : 'post',
				url : 'PaymentServlet',
				data : tokenObject_json,
				traditional : true,
				/*
				 * if LoginServlet connected then all values would be print in
				 * browser console.
				 */
				success : function(data) {
					var response = JSON.parse(data);

					if (response.status == true) {
						$("#loadingPayment").hide();
						document.getElementById("paymentSuccessLabel").style.display = 'block';
						document.getElementById("processPaymentStep").disabled = false;
						document.getElementById("paymentFailureLabel").style.display = 'none';

					}

					if (response.status == false) {
						$("#loadingPayment").hide();
						$("#ProcessPaymentButton").show();
						document.getElementById("paymentFailureLabel").style.display = 'block';

					}
				}
			});

}