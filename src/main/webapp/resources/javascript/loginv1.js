$(document)
		.ready(
				function() {

					$("form#signup")
							.submit(
									function() { // signup from is
										// submitted
										$(".loader").show(); // it will show
																// loading
										// animation until data stored
										// in database
										var firstname = $("#firstname").val(); // get
										// firstname
										var lastname = $("#lastname").val();// get
										// lastname
										var email = $("#email").val(); // get
										// email
										var password = $("#password").val();// get
										// password

										/*
										 * Lets create naming convention for all
										 * temperory objects that would be then
										 * converted to JSON it would be the
										 * javaClassNameThatMapsToDatabaseTable
										 * we can talk more on this, or create
										 * some thing else if everyone disaggres
										 * on this
										 */
										var UserInfo = {
											"first_name" : firstname,
											"last_name" : lastname,
											"email" : email,
											"password" : password,
											"phone" : "123456789"
										};

										/*
										 * convert the temp object created above
										 * to JSON object the name would be
										 * tempObjectCreatedAbove_json
										 */
										var UserInfo_json = JSON
												.stringify(UserInfo);

										/*
										 * The XMLHttpRequest object is used to
										 * exchange data with a server behind
										 * the scenes. Its already built in in
										 * modern browsers
										 */
										var xhttp = new XMLHttpRequest();

										/*
										 * specifies the type of request
										 * parameters: method: POST or GET url:
										 * the server file location async
										 * request: true or false, when set true
										 * we can basically execute other
										 * scripts while response in getting
										 * ready when response is ready this
										 * will recieve it
										 */
										xhttp.open("POST", "SignUpServlet",
												true);

										/*
										 * Sends the request to server with data
										 * (used for POST send(string)) for GET
										 * requests : send()
										 */
										xhttp.send(UserInfo_json);

										console.log(UserInfo_json);

										/*
										 * The onreadystatechange function is
										 * called every time the readyState
										 * changes.
										 */
										xhttp.onreadystatechange = function() {
											/*
											 * various options we can use:
											 * 
											 * readyState - Holds the status of
											 * the XMLHttpRequest. 0: request
											 * not initialized 1: server
											 * connection established 2: request
											 * received 3: processing request 4:
											 * request finished and response is
											 * ready
											 * 
											 * status - 200: "OK" 403:
											 * "Forbidden" 404: "Page not found"
											 * 
											 * These are some commonly used
											 * options, may be there would be
											 * more on internet
											 * 
											 * Reference : W3schools
											 * 
											 */
											if (this.readyState == 4
													&& this.status == 200) {

												/*
												 * We can do whatever we want
												 * with the response text here
												 * For now just printing the
												 * response, but here we can
												 * redirect to any page we would
												 * like
												 */
												var response = JSON
														.parse(this.responseText);

												if (response.status == true) {
													$(".loader").hide(); // after
																			// getting
													// true response
													// from database it
													// will stop
													window.location = "http://localhost:8084/doorstep-grocer/customer.html";

												}

												// console.log(response.status);
											}
										};

										/*
										 * return false so as to not reload the
										 * page
										 */
										return false;
									});

					/*
					 * When user click on Login button the below function will
					 * execute. It will store all Login form values in specific
					 * variables. It will create an object and established
					 * connection between webserver. If all value is passed then
					 * it will create an object of json and send to the server
					 * and store in database. if specific value is missing then
					 * it will represent message in console.
					 */
					$("form#login")
							.submit(
									function() { // loginForm
										// is
										// submitted

										$(".loader").show(); // It
										// will
										// show
										// loading
										// animation
										// until
										// database
										// found valid
										// email and
										// password

										var email = $("#emailL").val(); // get
										// email
										var password = $("#passwordL").val();// get
										// password

										var UserInfo_LoginDetails = {
											"email" : email,
											"password" : password
										};

										/*
										 * convert the temp object created above
										 * to JSON object the name would be
										 * tempObjectCreatedAbove_json
										 */
										var UserInfo_LoginDetails_json = JSON
												.stringify(UserInfo_LoginDetails);

										/**
										 * var $items = $('#emailL,
										 * #passwordL'); var obj = {};
										 * $items.each(function() { obj[this.id] =
										 * $(this).val();// creating json
										 * object.(The objcet // have all login
										 * form values) }) var json =
										 * JSON.stringify(obj);// converting
										 * javascript value to json // string
										 */
										$
												.ajax({
													type : 'post',
													url : 'LoginServlet',
													data : UserInfo_LoginDetails_json,
													traditional : true,
													/*
													 * if LoginServlet connected
													 * then all values would be
													 * print in browser console.
													 */
													success : function(data) {
														var response = JSON
																.parse(data);

														if (response.status == true)
															window.location = "http://localhost:8084/doorstep-grocer/customer.html";
														else {
															$(".loader").hide(); // after
															// getting
															// false
															// response
															// from
															// database
															// it
															// will
															// stop
															document
																	.getElementById("loginError").innerHTML = "<b>Worng Password!</b>";

														}
														// alert("Wrong
														// Password");

													}
												});

										return false;// tells
										// to
										// the
										// browser
										// that the event not
										// finished yet.

									});

					/*
					 * Used jQuery function If user clicked on login button on
					 * the navbar then, signup from will be collapsed and login
					 * form will be pop up with out reloading the page
					 */
					$("#loginS").click(function() {
						$("#signupBox").slideUp("normal", function() {
							$("#loginBox").slideDown("normal");
						});
					});
					/*
					 * Used jQuery function If user clicked on signup button on
					 * the navbar then, login from will be collapsed and login
					 * form will be pop up with out reloading the page
					 */
					$("#signinS").click(function() {
						$("#loginBox").slideUp("normal", function() {
							$("#signupBox").slideDown("normal");
						});
					});

				});
