function UsdToEuro() {
	document.getElementById("usdCurrency").style.color = "blue";
	document.getElementById("EuroCurrency").style.color = "red";

	var changeCurrencyObj = {
		"currencySelected" : "EURO"
	};
	var changeCurrencyObj_json = JSON.stringify(changeCurrencyObj);

	$.ajax({
		type : 'post',
		url : 'CurrencyInternationalizationServlet',
		data : changeCurrencyObj_json,
		traditional : true,

		success : function(data) {
			displayCurrentCart();
		}
	});

}

function EuroToUsd() {
	document.getElementById("usdCurrency").style.color = "red";
	document.getElementById("EuroCurrency").style.color = "blue";
	var changeCurrencyObj = {
		"currencySelected" : "USD"
	};
	var changeCurrencyObj_json = JSON.stringify(changeCurrencyObj);

	$.ajax({
		type : 'post',
		url : 'CurrencyInternationalizationServlet',
		data : changeCurrencyObj_json,
		traditional : true,

		success : function(data) {
			displayCurrentCart();
		}
	});
}