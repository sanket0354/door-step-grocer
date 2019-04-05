/**
 * 
 */

function sendLogOutRequest(){
	
	$.ajax({
		type : 'get',
		url : 'LogOutServlet',
		traditional : true,
		/*
		 * if LoginServlet connected then all values would be print in browser
		 * console.
		 */
		success : function(data) {
			var response = JSON.parse(data);
			if(response.status == true)
				window.location = "http://localhost:8084/doorstep-grocer/index.html";

		}
	});
	
}