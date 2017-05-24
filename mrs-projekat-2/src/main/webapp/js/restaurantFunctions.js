function addRestaurant() {
	var $form = $("#addRestaurant");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#add-error").text("Fill all the fields").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	console.log(s);
	$.ajax({
		url: "/addrestaurant",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				location.href = "#";
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function getRestaurants(){
	$.ajax({
		url: "/restaurants",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				$("#app-div").html("");
				$.each(data.responseJSON, function(i, item) {
					$("#app-div").append($("<section class='wrapper'>").append($("<div class='col-lg-4 col-md-4 col-sm-4 mb'>")
									.append($("<div class='content-panel pn'>")
										.append($("<div id='profile-01'>")
											.append($("<h3>").text(item.name))
											.append($("<h6>").text(item.description))
										)
										.append($("<div class='profile-01 centered'>")
											.css('color','#802000')
											.append($("<a class='button'>").attr("href", "addManager?id=" +item.id)
												.css('padding-top','1px')
												.css('padding-bottom','31px')
												.append($("<p>").text("Add Manager").css('padding-botton','31px'))
											)
										)
										.append($("<div class='centered'>")
											.append($("<h6>")
												.append($("<i class='fa fa-envelope'>"))
												.append($("<br/>"))
											)
										)
									)
								)
								);
				});
					
			}
		}
	});
}


function displayRestaurant(restaurant){
	$("#add-drink-form input[name=restaurant]").val(restaurant.id);
	$("#add-meal-form input[name=restaurant]").val(restaurant.id);
	$("#app-div").html("");
	$("#app-div").append($("<h5>").text("Name: " + restaurant.name))
						.append($("<h5>").text("Description: " + restaurant.description))
						.append($("<button>").text("Edit").click(function(){
								$("#modalInformation").modal('toggle');
								})
							);
	
	$("#r-name").attr("value", restaurant.name);
	$("#r-description").text(restaurant.description);
	$("#app-div").append($("<h5>").text("Drink List"));
	$("#app-div").append($("<button>")
			.text("Add drink")
			.attr("data-target","#modalDrink")
			.click(function(){
				$("#modalDrink").modal('toggle');
			}));
	/*$.each(restaurant.drinkList, function(i, item) {
		$("#app-div").append($("<div class='row mt'>")
								.append($("<div class='col-md-4'>")
									.append($("<div class='white-panel pn'>").css("width","1050")
										.append($("<div class='white-header'>")
											.append($("<h4>").text(item.name)))
										.append($("<img src='img/city1.jpg' class='img' width='100'>").css("width","150"))
										.append($("<h4>").text(item.description + " " + item.price))
									)
								)
							);
		
	});*/
	
	$("#app-div").append($("<h5>").text("Menu"));
	$("#app-div").append($("<button>")
			.text("Add meal")
			.attr("data-target","#modalMeal")
			.click(function(){
				$("#modalMeal").modal('toggle');
			}));
	
	/*$.each(restaurant.menu, function(i, item) {
		$("#app-div").append($("<div class='row mt'>")
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
	*/
}

function addDrink(){
	var $form = $("#add-drink-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
	var id = data.restaurant;
	data.restaurant = {id: id};
	var s = JSON.stringify(data);
	console.log(s);
	$.ajax({
		url: "/addDrink",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				$("#modalDrink").modal('toggle');
				location.href = "#";
			}
			else{
				$("#form-error").text("Username already exists").css("color","red");
			}
		}
	});
}

function addMeal(){
	var $form = $("#add-meal-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
	var id = data.restaurant;
	data.restaurant = {id: id};
		
	var s = JSON.stringify(data);
		
	$.ajax({
		url: "/addMeal",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				$("#modalMeal").modal('toggle');
				location.href = "#";
			}
			else{
				$("#form-error").text("An error has ocured").css("color","red");
			}
		}
	});
}

function changeInformation(){
	var $form = $("#change-information-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
		
	var s = JSON.stringify(data);
	console.log(s);
	$.ajax({
		url: "/changeInformation",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
				location.href = "#";	
		}
	});
}	

function getRestaurants1(){
		$.ajax({
			url: "/restaurants",
			type:"GET",
			contentType:"application/json",
			dataType:"json",
			headers: createAuthorizationTokenHeader(),
			complete: function(data) {
				if (data.responseJSON){
					$("#app-div").append($("<section class='wrapper'>" ).append($("<div class='col-md-4'>").append($("<button class='button' onclick='sortRestaurants()'>Sort</button>"))));
					$("#app-div").append($("<section class='wrapper' id='restWrapper'>"));
					displayRestaurants(data.responseJSON);
				}
			}
		});
	}	
function sortRestaurants(){
	$.ajax({
		url: "/sortRestaurants",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
				displayRestaurants(data.responseJSON);	
		}
	});
	
	
}
function displayRestaurants(restaurants){
	$("#restWrapper").html("");
	$.each(restaurants, function(i, item) {
		$("#restWrapper").append($("<div class='col-lg-4 col-md-4 col-sm-4 mb'>")
								.append($("<div class='content-panel pn'>")
									.append($("<div id='profile-01'>")
										.append($("<h3>").text(item.name))
										.append($("<h6>").text(item.description))
									)
									.append($("<div class='profile-01 centered'>")
										.css('color','#802000')
										.append($("<a class='button'>").attr("href", "addManager.html?id=" +item.id)
											.css('padding-top','1px')
											.css('padding-bottom','31px')
											.append($("<p>").text("Add Manager").css('padding-botton','31px'))
											
										)
									)
									.append($("<div class='centered'>")
										.append($("<h6>")
											.append($("<i class='fa fa-envelope'>"))
											.append($("<br/>"))
											.text('ja')
										)
									)
								)
							);
		
	});
	
}