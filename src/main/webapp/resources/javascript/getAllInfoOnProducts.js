var storeFrontItemsInfo;

$(document)
		.ready(
				function() {

					$
							.ajax({
								type : 'get',
								url : 'PopulateStoreFrontServlet',
								traditional : true,
								/*
								 * if LoginServlet connected then all values
								 * would be print in browser console.
								 */
								success : function(data) {
									var response = JSON.parse(data);
									storeFrontItemsInfo = response;
									// alert("Wrong
									// Password");
									// As vegetable tab will always be first
									// load its
									// products when the page loads
									test({
										innerHTML : "Vegetable"
									});
									
								
								}
							});

				});