var addressDetails_repeatOrder;
var addressDetails_repeatOrder_json;
var orderNumber_repeatOrder;
function repeatOrder(obj) {

	orderNumber_repeatOrder = previousOrdersObject[obj.name].order_number;

	console.log()

	orderSummary();
	// placeRepeatOrder(orderNumber);

}

function orderSummary() {

	$("#previousOrdersBody").fadeOut("normal", function() {
		$("#previous-order-address-modal-body").fadeIn("normal");
	});

}

function gatherOrderDetails_repeatOrder($this) {
	document.getElementById("addressStep_repeatOrder").style.visibility = 'hidden';
	$("#loading_repeatOrder").show(); // It

	var address = $("#address-repeatOrder").val();
	var city = $("#city-repeatOrder").val();
	var state = $("#state-repeatOrder").val();
	var postal_code = $("#zipcode-repeatOrder").val();

	addressDetails_repeatOrder = {
		"address" : address,
		"city" : city,
		"country" : state,
		"postal_code" : postal_code
	}
	addressDetails_repeatOrder_json = JSON
			.stringify(addressDetails_repeatOrder);

	console.log(addressDetails_repeatOrder_json);
	placeRepeatOrder();

}
function placeRepeatOrder() {
	var repeatOrder = {
		"orderNumber" : orderNumber_repeatOrder,
		"address" : addressDetails_repeatOrder.address,
		"city" : addressDetails_repeatOrder.city,
		"country" : addressDetails_repeatOrder.country,
		"postal code" : addressDetails_repeatOrder.postal_code
	};

	var repeatOrder_json = JSON.stringify(repeatOrder);
	$
			.ajax({
				type : 'post',
				url : 'OrderAgainServlet',
				data : repeatOrder_json,
				traditional : true,
				success : function(data) {
					var response = JSON.parse(data);
					console.log(response);

					document.getElementById('cancel_repearOrder').click();
					document.getElementById("addSuccessPlaceOrder").innerHTML = "";
					$("#addSuccessPlaceOrder")
							.append(
									"<div class='alert alert-success alert-dismissable fade in'> \
	<a href=''#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> \
	<strong>Success!</strong> Your Order was Placed. Check Email for Confirmation. \
</div>");

					document.getElementById("addressStep_repeatOrder").style.visibility = 'visible';
					$("#loading_repeatOrder").hide();

				}
			});
}

$("#cancel_repearOrder").click(function() {
	$("#previous-order-address-modal-body").fadeOut("normal", function() {
		$("#previousOrdersBody").fadeIn("normal");
	});

});
