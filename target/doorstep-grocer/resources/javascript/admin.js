$(document).ready(function() {

	$.ajax({
		type : 'get',
		url : 'AdminServlet',
		traditional : true,

		success : function(data) {
			var response = JSON.parse(data);
			console.log(response);
			populateUserTable(response);
		}
	});

	var getOrders = {
		"operation" : "getOrders",
	};

	var getOrders_json = JSON.stringify(getOrders);

	$.ajax({
		type : 'post',
		url : 'AdminServlet',
		data : getOrders_json,
		traditional : true,
		/*
		 * if LoginServlet connected then all values would be print in browser
		 * console.
		 */
		success : function(data) {
			var response = JSON.parse(data);
			populateOrderTable(response);
		}

	});

});

function populateUserTable(response) {
	document.getElementById("adminPanelUserTableBody").innerHTML = "";
	var totalItems = Object.keys(response).length;

	for (i = 1; i <= totalItems; i++) {
		$('#adminPanelUserTableBody')
				.append(
						"\
			<tr id='"
								+ response[i].email
								+ "'>\
				<th>"
								+ response[i].firstName
								+ "</th>\
				<th>"
								+ response[i].lastName
								+ "</th>\
				<th>"
								+ response[i].email
								+ "</th>\
				<th>"
								+ response[i].address
								+ "</th>\
				<th>"
								+ response[i].city
								+ "</th>\
				<th>"
								+ response[i].country
								+ "</th>\
				<th>"
								+ response[i].postalCode
								+ "</th>\
				<th>"
								+ response[i].admin
								+ "</th>\
				<th>"
								+ response[i].driver
								+ "</th>\
				<th><button type='button' class='btn btn-primary btn-sm' onclick='changeAdminState(this.parentElement.parentElement)' style='' id='makeAdminButton"
								+ i
								+ "'>\
						<span class='button_text_font'><i class='fa fa-plus' aria-hidden='true' id='makeAdminIcon"
								+ i
								+ "'></i></span> \
					</button>\
				</th>\
				<th><button type='button' class='btn btn-primary btn-sm' onclick='changeDriverState(this.parentElement.parentElement)' style='' id='makeDriverButton"
								+ i
								+ "'>\
						<span class='button_text_font'><i class='fa fa-plus' aria-hidden='true' id='makeDriverIcon"
								+ i
								+ "'></i></span> \
					</button>\
				</th>\
			</tr>\
			");

		// for (i = 1; i <= totalItems; i++) {
		if (response[i].admin == false)
			document.getElementById("makeAdminIcon" + i).className = "fa fa-plus";

		if (response[i].admin == true)
			document.getElementById("makeAdminIcon" + i).className = "fa fa-minus";

		if (response[i].driver == false)
			document.getElementById("makeDriverIcon" + i).className = "fa fa-plus";

		if (response[i].driver == true)
			document.getElementById("makeDriverIcon" + i).className = "fa fa-minus";

		// }

	}
}

function populateOrderTable(response) {
	console.log(response);
	document.getElementById("adminPanelOrderTableBody").innerHTML = "";
	var totalItems = Object.keys(response).length;

	for (i = 1; i <= totalItems; i++) {
		$('#adminPanelOrderTableBody').append(
				"\
				 <tr>\
					<th><a data-toggle='collapse' onclick='displayProducts("
						+ response[i].cartId
						+ ");' data-parent='#accordion' href='#collapse' >"
						+ response[i].orderNumber + "</a></th>\
					<th>"
						+ response[i].orderDate + "</th>\
					<th>"
						+ response[i].deliverDate + "</th>\
					<th>"
						+ response[i].amount + "</th>\
					<th>"
						+ response[i].OrderedBy
						+ "</th>\
				</tr>\
				\
				\
				\
				");

	}

	$('#detailReviewOrder')
			.append(
					"\<div id='collapse' class='panel-collapse collapse'>\
<div class='table-responsive'>\
		<table class='table'>\
			<thead>\
				<tr>\
					<th>#</th>\
					<th>Product Name</th>\
					<th>Quantity</th>\
					<th>Price(CAD)</th>\
				</tr>\
			</thead>\
			<tbody id ='itemsForOrder'>\
			</tbody>\
		</table>\
	</div>\
</div>\
");

	// console.log("end");
}

function displayProducts(obj) {
	var orderItems = {
		"operation" : "orderItems",
		"cartId" : obj
	};

	var orderItems_json = JSON.stringify(orderItems);

	$.ajax({
		type : 'post',
		url : 'AdminServlet',
		data : orderItems_json,
		traditional : true,
		/*
		 * if LoginServlet connected then all values would be print in browser
		 * console.
		 */
		success : function(data) {
			var response = JSON.parse(data);
			var totalItems = Object.keys(response).length;
			 $("#collapse").collapse('hide');


			document.getElementById("itemsForOrder").innerHTML = "";

			for (i = 1; i <= totalItems; i++) {

				$('#itemsForOrder').append(
						"<tr>\
					<td>" + i + "</td>\
					<td>"
								+ response[i].name + "</td>\
					<td>"
								+ response[i].quantity + "</td>\
					<td>"
								+ response[i].price + "</td>\
				</tr>");

			}
			
			$("#collapse").collapse('show');


		}

	});
}

function changeAdminState(obj) {
	var email = obj.id;

	var changeAdminState = {
		"operation" : "changeAdminState",
		"email" : email
	};

	var changeAdminState_json = JSON.stringify(changeAdminState);

	$.ajax({
		type : 'post',
		url : 'AdminServlet',
		data : changeAdminState_json,
		traditional : true,
		/*
		 * if LoginServlet connected then all values would be print in browser
		 * console.
		 */
		success : function(data) {
			var response = JSON.parse(data);
			populateUserTable(response);
		}

	});

}
function changeDriverState(obj) {
	var email = obj.id;

	var changeDriverState = {
		"operation" : "changeDriverState",
		"email" : email
	};

	var changeDriverState_json = JSON.stringify(changeDriverState);

	$.ajax({
		type : 'post',
		url : 'AdminServlet',
		data : changeDriverState_json,
		traditional : true,
		/*
		 * if LoginServlet connected then all values would be print in browser
		 * console.
		 */
		success : function(data) {
			var response = JSON.parse(data);
			populateUserTable(response);
		}

	});
}

function findUser() {
	var input, inputData, table, tr, th, i;
	input = document.getElementById("inputSearch");
	inputData = input.value.toUpperCase();
	table = document.getElementById("userTable");
	tr = table.getElementsByTagName("tr");
	for (i = 1; i < tr.length; i++) {
		th = tr[i].getElementsByTagName("th")[0];
		if (th) {
			if (th.innerHTML.toUpperCase().indexOf(inputData) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function findUserOrder() {
	var input, inputData, table, tr, th, i;
	input = document.getElementById("inputSearchOrder");
	inputData = input.value.toUpperCase();
	table = document.getElementById("orderTable");
	tr = table.getElementsByTagName("tr");
	for (i = 1; i < tr.length; i++) {
		th = tr[i].getElementsByTagName("th")[4];
		if (th) {
			if (th.innerHTML.toUpperCase().indexOf(inputData) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}