function displayRestaurant(restaurant){
	$("#restaurant-info").append($("<button>")
			.text("Add drink")
			.attr("data-target","#modalDrink")
			.click(function(){
				$("#modalDrink").modal('toggle');
			}));
	$("#restaurant-info")
						.append($("<h5>").text("Name: " + restaurant.name))
						.append($("<b>").text("Description: " + restaurant.description));
	
	$.each(restaurant.drinkList, function(i, item) {
		$("#restaurant-info").append($("<div class='row mt'>")
								.append($("<div class='col-md-4'>")
									.append($("<div class='white-panel pn'>").css("width","1050")
										.append($("<div class='white-header'>")
											.append($("<h4>").text(item.name)))
										.append($("<img src='img/city1.jpg' class='img' width='100'>").css("width","150"))
										.append($("<h4>").text(item.description + " " + item.price))
									)
								)
							);
		
	});
	
}

function addDrink(){
	var $form = $("#add-drink-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
		
	var s = JSON.stringify(data);
		
	$.ajax({
		url: "/addDrink",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				location.href = "indexSysMan.html";
			}
			else{
				$("#form-error").text("Username already exists").css("color","red");
			}
		}
	});
}