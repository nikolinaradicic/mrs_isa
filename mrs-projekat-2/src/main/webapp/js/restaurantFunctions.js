function setupChartWaiter(){
	$.ajax({
		url: "/getWorkingShiftWaiter",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				console.log(data.responseJSON);
				var canvas = new fabric.CanvasEx("canvas");
				document.getElementById('canvas').fabric = canvas;
				canvas.setHeight(450);
				canvas.setWidth(800);
				canvas.selection = false;
				fillSegmentBox();
				window.alert("Vasa smena je "+data.responseJSON.shift.name);
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function addRestaurant() {
	var $form = $("#addRestaurant");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#add-error").text("Fill all the fields").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
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
				$("#app-div").append($("<section class='wrapper' id='sekcija'>"));
				$.each(data.responseJSON, function(i, item) {
					$("#sekcija").append($("<div class='col-lg-4 col-md-4 col-sm-4 mb'>")
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
													.append($("<br/>"))
												)
											)
										)
									);
				});
					
			}
		}
	});
}

function getDrinks(restaurant){
	$.ajax({
		url: "/getDrinks",
		type:"POST",
		data: JSON.stringify(restaurant),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$.each(data.responseJSON, function(i, item){
				$("#drinks_inf").append($("<div class='col-md-12'>")
									.append($("<div class='col-md-4'>").css('border','1px solid grey')
									.append($("<h4>").text(item.name))
									.append($("<h6>").text(item.description))
									.append($("<h6>").text(item.price))
									)
								);
			});	
		}
	});
}

function getMeals(restaurant){
	$.ajax({
		url: "/getMeals",
		type:"POST",
		data: JSON.stringify(restaurant),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$.each(data.responseJSON, function(i, item){
				$("#meal_inf").append($("<div class='col-md-12'>")
									.append($("<div class='col-md-4'>").css('border','1px solid grey')
									.append($("<h4>").text(item.name))
									.append($("<h6>").text(item.description))
									.append($("<h6>").text(item.price))
									)
								);
			});	
		}
	});
}

function displayRestaurant(restaurant){
	$("#modals-div").load("restaurant.html #modals-div", function(){
		$("#add-drink-form input[name=restaurant]").val(restaurant.id);
		$("#add-meal-form input[name=restaurant]").val(restaurant.id);
		$("#app-div").load("restaurant.html #choosenRestaurant", function(){
				$("#name_rest").text(restaurant.name);
				$("#desc_rest").text(restaurant.description);
				$("#editRest").click(function(){
									$("#modalInformation").modal('toggle');
									});
				$("#addMeal").click(function(){
									$("#modalMeal").modal('toggle');
									});
				$("#addDrink").click(function(){
									$("#modalDrink").modal('toggle');
									});
				getDrinks(restaurant);
				getMeals(restaurant);
		});

		$("#r-name").attr("value", restaurant.name);
		$("#r-description").text(restaurant.description);

	});
		
	
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
				$("#drinks_inf").prepend($("<div class='col-md-12'>")
									.append($("<div class='col-md-4'>").css('border','1px solid grey')
									.append($("<h4>").text(data.responseJSON.name))
									.append($("<h6>").text(data.responseJSON.description))
									.append($("<h6>").text(data.responseJSON.price))
									)
								);
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
	console.log(data);
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
								$("#meal_inf").prepend($("<div class='col-md-12'>")
									.append($("<div class='col-md-4'>").css('border','1px solid grey')
									.append($("<h4>").text(data.responseJSON.name))
									.append($("<h6>").text(data.responseJSON.description))
									.append($("<h6>").text(data.responseJSON.price))
									)
								);
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
				$("#modalInformation").modal('toggle');
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
									.append($("<div class='centered'>")
										.append($("<h6>")
											.append($("<br/>"))
										)
									)
								)
							);
		
	});
	
}