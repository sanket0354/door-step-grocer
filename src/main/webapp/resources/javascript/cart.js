/**
 * 
 */
var item = 0;
var displayCartInfo;
$(document)
		.ready(
				function() {

					var totalItemsInCartObj = {
						"operation" : "totalItems"
					};

					var totalItemsInCartObj_json = JSON
							.stringify(totalItemsInCartObj);

					$
							.ajax({
								type : 'post',
								url : 'CartServlet',
								data : totalItemsInCartObj_json,
								traditional : true,
								/*
								 * if LoginServlet connected then all values
								 * would be print in browser console.
								 */
								success : function(data) {
									var response = JSON.parse(data);

									if (response.status == true) {
										item = item + response.totalItems;
										document.getElementById("quantity").innerHTML = " <span id='quantity' class='badge animated pulse' style='font-size:18px; background-color: red;'> <span class='animated fadeInDown'>"
												+ item + "</span></span>";
										
										
										
										
										
									}

								}
							});

				});

function addToCart(obj) {

	var nameOfItem = obj.value;

	var addToCartObj = {
		"operation" : "add",
		"item" : {
			"quantity" : "1",
			"name" : nameOfItem
		}
	};

	var addToCartObj_json = JSON.stringify(addToCartObj);

	$
			.ajax({
				type : 'post',
				url : 'CartServlet',
				data : addToCartObj_json,
				traditional : true,
				/*
				 * if LoginServlet connected then all values would be print in
				 * browser console.
				 */
				success : function(data) {
					var response = JSON.parse(data);

					if (response.status == true) {
						item = item + 1;
						document.getElementById("quantity").innerHTML = " <span id='quantity' class='badge animated pulse' style='font-size:18px; background-color: red;'> <span class='animated fadeInDown'>"
								+ item + "</span></span>";
						
						displayCurrentCart();
					}

				}
			});

}

function removeFromCart(obj) {

	var nameOfItem = obj.value;

	var removeFromCartObj = {
		"operation" : "remove",
		"item" : {
			"quantity" : "1",
			"name" : nameOfItem
		}
	};

	var removeFromCartObj_json = JSON.stringify(removeFromCartObj);

	$
			.ajax({
				type : 'post',
				url : 'CartServlet',
				data : removeFromCartObj_json,
				traditional : true,
				/*
				 * if LoginServlet connected then all values would be print in
				 * browser console.
				 */
				success : function(data) {
					var response = JSON.parse(data);

					if (response.status == true) {
						if (item > 0) {
							item = item - 1;
							document.getElementById("quantity").innerHTML = " <span id='quantity' class='badge animated pulse' style='font-size:18px; background-color: red;'> <span class='animated fadeInDown'>"
									+ item + "</span></span>";
							+item + "</span>";
							displayCurrentCart();
						}
						if (item == 0) {
							document.getElementById("quantity").innerHTML = "";
						}
					}

				}
			});

}

function displayCurrentCart() {

	document.getElementById("addItemsToCartDisplay").innerHTML = "";
	document.getElementById("NoItemsInCart").innerHTML = "";
	document.getElementById("checkOut").disabled = false;

	var displayCartObj = {
		"operation" : "display",
	};

	var displayCartObj_json = JSON.stringify(displayCartObj);

	$
			.ajax({
				type : 'post',
				url : 'CartServlet',
				data : displayCartObj_json,
				traditional : true,
				/*
				 * if LoginServlet connected then all values would be print in
				 * browser console.
				 */
				success : function(data) {
					var response = JSON.parse(data);
					displayCartInfo = response;
					var totalItems = Object.keys(response).length;
					console.log(response);

					if (item == 0) {
						document.getElementById("cartTable").style.visibility = 'hidden';
						$('#NoItemsInCart').append("<b>No Items in Cart<b>");
						document.getElementById("checkOut").disabled = true;

					} else {

						for (i = 0; i < totalItems; i++) {
							document.getElementById("cartTable").style.visibility = 'visible';

							$('#addItemsToCartDisplay')
									.append(
											"<tr> \
							<td>"
													+ (i + 1)
													+ "</td> \
							<td>"
													+ response[i + 1].name
													+ "</td> \
							<td>"
													+ response[i + 1].quantity
													+ "</td> \
							<td>"
													+ response[i + 1].price
													+ "</td> \
							<td> <button type='button' value='"
													+ response[i + 1].name
													+ "' class='btn btn-primary btn-sm' \
						onclick='removeFromCart(this)'> \
						<span class='button_text_font'><i class='fa fa-minus' aria-hidden='true'></i> </span> \
					</button> \
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \
							<button type='button' value='"
													+ response[i + 1].name
													+ "' class='btn btn-primary btn-sm' onclick='addToCart(this)'> \
                                             <span class='button_text_font'><i class='fa fa-plus' aria-hidden='true'></i></span> "
													+ "</button> </td> </tr>");
						}
					}

				}
			});
}

$("#checkOut").click(function() {
	$("#cartbody").fadeOut("normal", function() {
		$("#address-modal-body").fadeIn("normal");
	});
});

$("#addressStep").click(function() {
	$("#address-modal-body").fadeOut("normal", function() {
		$("#payment-body").fadeIn("normal");
	});
});

$("#processPaymentStep")
		.click(
				function() {
					$("#payment-body")
							.fadeOut(
									"normal",
									function() {
										$("#loadingPayment").hide();
										document
												.getElementById("paymentSuccessLabel").style.visibility = 'hidden';
										$("#ProcessPaymentButton").show();

										$("#order-review-body")
												.fadeIn("normal");

										document
												.getElementById("processPaymentStep").disabled = true;

									});
				});

$("#cancel").click(function() {
	$("#address-modal-body").fadeOut("normal", function() {
		$("#cartbody").fadeIn("normal");
	});

	$("#order-review-body").fadeOut("normal", function() {
		$("#cartbody").fadeIn("normal");

	});

	$("#payment-body").fadeOut("normal", function() {
		$("#cartbody").fadeIn("normal");
		$("#loadingPayment").hide();
		document.getElementById("paymentFailureLabel").style.display = 'none';

		document.getElementById("paymentSuccessLabel").style.display = 'none';
		$("#ProcessPaymentButton").show();
	});

});
