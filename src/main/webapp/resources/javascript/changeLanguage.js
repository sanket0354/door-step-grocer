/**
 * 
 */

function changeLanguage(obj) {
	var lang = document.getElementById("displayLangInBold").innerHTML;
	document.getElementById("displayLangInBold").innerHTML = obj.innerHTML;
	document.getElementById("languageOptions").innerHTML = "<li><a id='displayLanguageDropDown' onclick='changeLanguage(this)'>"
			+ lang + "</a></li>";

	var language = document.getElementById("displayLangInBold").innerHTML;

	console.log(language);
	var changeLanguageObject = {
		"language" : language
	};

	/*
	 * convert the temp object created above to JSON object the name would be
	 * tempObjectCreatedAbove_json
	 */
	var changeLanguageObject_json = JSON.stringify(changeLanguageObject);

	/**
	 * var $items = $('#emailL, #passwordL'); var obj = {};
	 * $items.each(function() { obj[this.id] = $(this).val();// creating json
	 * object.(The objcet // have all login form values) }) var json =
	 * JSON.stringify(obj);// converting javascript value to json // string
	 */
	$.ajax({
		type : 'post',
		url : 'InternationalizationServlet',
		data : changeLanguageObject_json,
		traditional : true,
		/*
		 * if LoginServlet connected then all values would be print in browser
		 * console.
		 */
		success : function(data) {
			var response = JSON.parse(data);
			console.log(response);
			populateNavBar(language);
			storeFrontItemsInfo = response;
			test({innerHTML:storeFrontItemsInfo[1].categoryName});

		}
	});

}