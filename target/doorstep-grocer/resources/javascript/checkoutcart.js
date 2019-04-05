var deliveryDetails;
var addressDetails;
var addressDetails_json;
function gatherOrderDetails($this) {
    var address = $("#address1").val();
    var city = $("#city").val();
    var state = $("#state").val();
    var postal_code = $("#zipcode").val();

    addressDetails = {
        "address": address,
        "city": city,
        "country": state,
        "postal code": postal_code
    }
    addressDetails_json = JSON.stringify(addressDetails);
    console.log(addressDetails);
    $.ajax({
        type: 'get',
        url: 'PlaceOrderServlet',
        traditional: true,
        /*
         * if LoginServlet connected then all values would be print in browser
         * console.
         */
        success: function (data) {
            var response = JSON.parse(data);
            deliveryDetails = response;
            console.log(deliveryDetails);

            reviewOrder();

        }

    });

    // placeOrder();
}

function placeOrder() {
    // var address = $("#address1").val();
    // var city = $("#city").val();
    // var state = $("#state").val();
    // var postal_code = $("#zipcode").val();
    //
    // var addressDetails = {
    // "address" : address,
    // "city" : city,
    // "country" : state,
    // "postal code" : postal_code
    // }

    // console.log(addressDetails_json);

    document.getElementById("placeOrderStep").style.visibility = 'hidden';
    $(".loader").show(); // It

    $
            .ajax({
                type: 'post',
                url: 'PlaceOrderServlet',
                data: addressDetails_json,
                traditional: true,
                /*
                 * if LoginServlet connected then all values would be print in
                 * browser console.
                 */
                success: function (data) {
                    var response = JSON.parse(data);
                    deliveryDetails = response;
                    console.log(deliveryDetails);

                    /*
                     * Now as the cart is checkout, display zero as the item in
                     * cart as user will add new items to new cart
                     */

                    item = 0;
                    document.getElementById("quantity").innerHTML = " <span id='quantity' class='badge animated pulse' style='font-size:18px; background-color: red;'> <span class='animated fadeInDown'>"
                            + item + "</span></span>";
                    +item + "</span>";

                    document.getElementById('cancel').click();
                    document.getElementById("addSuccessPlaceOrder").innerHTML = "";
                    $("#addSuccessPlaceOrder")
                            .append(
                                    "<div class='alert alert-success alert-dismissable fade in'> \
			<a href=''#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> \
			<strong>Success!</strong> Your Order was Placed. Check Email for Confirmation. \
		</div>");

                    document.getElementById("placeOrderStep").style.visibility = 'visible';
                    $(".loader").hide();
                }

            });

}

function reviewOrder() {
    document.getElementById("orderReviewTable").innerHTML = "";

    $("#orderReviewTable").append(
            " \
	<tr><td>Person Name:</td><td>"
            + deliveryDetails.delivery_details.delivered_to
            + "</td></tr> \
	<tr><td>Address:</td><td>"
            + addressDetails.address + ", " + addressDetails.country
            + ", " + addressDetails.city + "</td></tr> \
");

    var totalItems = Object.keys(displayCartInfo).length - 1;

    document.getElementById("addOrderReviewItems").innerHTML = "";

    for (i = 0; i < totalItems; i++) {
        $('#addOrderReviewItems').append(
                "<tr> \
	<td>" + (i + 1) + "</td> \
	<td>"
                + displayCartInfo[(i + 1)].name + "</td> \
	<td>"
                + displayCartInfo[(i + 1)].quantity + "</td> \
	<td>"
                + displayCartInfo[(i + 1)].price + "</td> \
	</tr>");
    }
    document.getElementById("reviewOrderTotalPrice").innerHTML = "";
    $("#reviewOrderTotalPrice").append(
            "<tr><td></td><td></td><td>Total Price</td><td>$"
            + displayCartInfo.total_price + "</td></tr>");
}
