/**
 * 
 */
var previousOrdersObject ;
function displayPreviousOrders() {
	
	document.getElementById("previousOrderLoaderDiv").style.visibility = 'visible';
	$("#previousOrderLoader").show(); 

	document.getElementById("previousOrderAccordion").innerHTML = "";
	$.ajax({
		type : 'get',
		url : 'PreviousOrdersServlet',
		traditional : true,

		success : function(data) {
			var response = JSON.parse(data);
			previousOrdersObject = response;
			console.log(response);

			renderPreviousOrders(response);

		}
	});

}

function renderPreviousOrders(response) {

	$("#previousOrderLoader").hide(); 
	document.getElementById("previousOrderLoaderDiv").style.visibility = 'hidden';

	
	/*
	 * check total orders in json object
	 */
	var totalOrders = Object.keys(response).length;
	
	for(i = 0; i < totalOrders; i++){
		
		$('#previousOrderAccordion').append(
				"<div class='panel panel-default' id='previousOrderPanel"+ (i+1) +"' data-value="+ (i+1) +"> \
					<div class='panel-heading clearfix'> \
						<h4 class='panel-title'> \
						<a data-toggle='collapse' data-parent='#previousOrderAccordion' \
							href='#collapse"+ (i+1) +"'>Order #"+ (i+1)  +"</a> \
						<button type='button' class='btn btn-default btn-xs pull-right' onclick='repeatOrder(this)' name='"+ (i+1) +"'>Order Again</button> \
						</h4> \
					</div> \
				</div>");
		
		$('#previousOrderPanel'+ (i+1)).append(
				"<div class='table-responsive'> \
                 <table class='table' style='margin-top:3px; margin-bottom:5px;'> \
                	<tr><td>Order Date:</td><td>"+ response[i+1].order_date +"</td></tr> \
					<tr><td>Delivery Date:</td><td>"+ response[i+1].delivery_date +"</td></tr> \
					<tr><td>Delivered to:</td><td>" + response[i+1].person_name  + "</td></tr> \
					<tr><td>Delivered at:</td><td>" + response[i+1].delivery_address  + "</td></tr><table> \
				</div>");
	
		
		
		$('#previousOrderPanel'+ (i+1)).append(
				"<div id='collapse"+ (i+1) +"' class='panel-collapse collapse'> \
								<div class='table-responsive' id='collapseTable"+ (i+1) +"'> \
								</div> \
				</div>");
		
		
		$('#collapseTable'+ (i+1)).append(
		"<table class='table'> \
				<thead> \
					<tr> \
					  <th>#</th> \
					  <th>Product Name</th> \
					  <th>Quantity</th> \
					  <th>Price</th> \
				    </tr> \
				</thead> \
				<tbody id='collapseTableBody"+  (i+1)+"'> \
				</tbody> \
				<tfoot> \
					<tr> \
					<th></th> \
					<th></th> \
					<th>Total Price:</th> \
					<th>$"+ response[i+1].total_price +"</th> \
					</tr> \
				</tfoot> \
		</table>"		
		);
		
		var totalItemsInOrder = Object.keys(response[i+1].items).length;
		
		for(j = 0; j < totalItemsInOrder; j++){
			$("#collapseTableBody"+ (i+1)).append("\
					<tr> \
					   <td>"+ (j+1) +"</td> \
					   <td>"+ response[i+1].items[j+1].name +"</td> \
					   <td>"+ response[i+1].items[j+1].quantity +"</td> \
					   <td>$"+ response[i+1].items[j+1].price +"</td> \
					</tr>");
		}
		
	}

}

