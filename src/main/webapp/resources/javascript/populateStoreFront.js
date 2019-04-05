function populateItemsFromDatabase(categoryName, url) {

    $('#populateItemsFromDatabase')
            .append(
                    "<div class='col-lg-3 col-sm-6' style='width: 235px;'> \
				<div \
					class='well w3-card-8 w3-leftbar w3-rightbar  w3-hover-border-red' \
					style='width: 220px; background-color: white;'> \
					<h4 class='heading_font w3-center'>"
                    + categoryName
                    + "</h4> \
					<p> \
						<img src='"
                    + url
                    + "' class='w3-round' alt='' \
							style='width: 170px; height: 120px;'> \
					</p> \
							<button type='button' value='"
                    + categoryName
                    + "' class='btn btn-primary btn-sm' \
						onclick='removeFromCart(this)'> \
						<span class='button_text_font'><i class='fa fa-minus' aria-hidden='true'></i> </span> \
					</button> \
					&nbsp; \
					<button type='button' class='btn btn-default' id='" + categoryName + "Button'>Quantity</button>\
					 &nbsp; \ <button type='button' value='"
                    + categoryName
                    + "' class='btn btn-primary btn-sm' \
						onclick='addToCart(this)'> \
						<span class='button_text_font'><i class='fa fa-plus' aria-hidden='true'></i></span> \
					</button> \ </div> \
			</div>");

}

function test(obj) {
    var name = obj.innerHTML;

    document.getElementById("populateItemsFromDatabase").innerHTML = "";
    // console.log(storeFrontItemsInfo);

    var totalCategory = Object.keys(storeFrontItemsInfo).length;
    // console.log(totalCategory);
    for (i = 1; i <= totalCategory; i++) {
        var categoryName = storeFrontItemsInfo[i].categoryName;
        var numSubCategory = storeFrontItemsInfo[i].numSubCategory;
        var numProducts = storeFrontItemsInfo[i].numProducts;
        // console.log(categoryName);
        // console.log(numSubCategory);
        // console.log(numProducts);
        if (categoryName == name && numSubCategory == 0) {
            // console.log("inside");
            for (j = 1; j <= numProducts; j++) {
                populateItemsFromDatabase(
                        storeFrontItemsInfo[i].products[j].name,
                        storeFrontItemsInfo[i].products[j].url);
            }

        } else {
            var catNameFromNavMenu = $("li#headOfCategory").children("a")
                    .text();
            // console.log(catNameFromNavMenu);
            if (catNameFromNavMenu == categoryName) {
                var SubCatNameArray = Object
                        .keys(storeFrontItemsInfo[i].products);
                // console.log(storeFrontItemsInfo);
                // console.log(storeFrontItemsInfo[i].products[name].numProducts);
                var numSubcategoryProducts = storeFrontItemsInfo[i].products[name].numProducts;
                for (j = 1; j <= numSubcategoryProducts; j++) {
                    // console.log(test);
                    populateItemsFromDatabase(
                            storeFrontItemsInfo[i].products[name][j].name,
                            storeFrontItemsInfo[i].products[name][j].url);

                }
            }
            catNameFromNavMenu = "";
        }

    }

}