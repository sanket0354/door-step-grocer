/*
 * When DOM(Document Object Model) is loaded then start populating the 
 * Nav Bar table
 */
var navBarJSONObject;
$(document).ready(function () {
    var language = document.getElementById("displayLangInBold").innerHTML;

    populateNavBar(language);
//	console.log(document.getElementById("DyanamicNavigationBar"));
//	var x = document.getElementById("DyanamicNavigationBar").firstChild;// Click on the checkbox
//	console.log(x);


});

function populateNavBar(language) {


    var changeLanguageObject = {
        "language": language
    };

    /*
     * convert the temp object created above to JSON object the name would be
     * tempObjectCreatedAbove_json
     */
    var changeLanguageObject_json = JSON.stringify(changeLanguageObject);

    $
            .ajax({
                type: 'post',
                url: 'PopulateSideNavBarServlet',
                data: changeLanguageObject_json,
                traditional: true,

                /*
                 * if LoginServlet connected then all values would be print in
                 * browser console.
                 */
                success: function (data) {

                    document.getElementById("DyanamicNavigationBar").innerHTML = "";

                    var response = JSON.parse(data);
                    if (response.isAdmin == false)
                        document
                                .getElementById("AdminPanelNavBar").style.visibility = 'hidden';



                    navBarJSONObject = response;
                    var totalCategory = Object.keys(response).length;

                    for (i = 0; i < totalCategory; i++) {
                        var categoryName = response[i].name;
                        var numSubCategory = response[i].numSubCategory;
                        var subCategoryObj = response[i].subCategory;

                        if (response[i].numSubCategory != 0) {
                            $("#DyanamicNavigationBar")
                                    .append(
                                            "<li class='dropdown' id='headOfCategory'> <a data-toggle='dropdown' href='#"
                                            + categoryName
                                            + "' class='dropdown-toggle'>"
                                            + categoryName
                                            + "<span class='caret'></span></a> <ul class='dropdown-menu' role='menu' id='listOfSubCategory'>");

                            for (j = 0; j < numSubCategory; j++) {
                                $("#listOfSubCategory").append(
                                        "<li id='" + subCategoryObj[j]
                                        + "Selection'><a href='#"
                                        + subCategoryObj[j]
                                        + "' onclick='test(this)'>"
                                        + subCategoryObj[j]
                                        + "</a></li>");
                            }

                            $("#listOfSubCategory").append("</ul></li>");

                        } else {
                            console.log(categoryName);
                            $("#DyanamicNavigationBar").append(
                                    "<li><a id='" + categoryName
                                    + "Selection' href='#"
                                    + categoryName
                                    + "' onclick='test(this)'>"
                                    + categoryName + "</a></li>");
                        }

                    }

                }
            });
}