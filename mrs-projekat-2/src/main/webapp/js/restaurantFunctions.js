function setupChartWaiter(){
	$.ajax({
		url: "/getWorkingShiftWaiter",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
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
			if(item.deleted){
				return;
			}else{
				$("#drinks-table-body").append($("<tr>").attr("id", "drink"+item.id)
						.append($("<td>")
								.text(item.name)
							)
							.append($("<td>")
								.text(item.description)
							)
							.append($("<td>")
								.text(item.price)
							)
							.append($("<td>")
								.append($("<input type='button' class='btn btn-success btn-xs' value='Edit'>")
									.click(function(){
									openUpdateDrink(item);
									})
								)
							)
							.append($("<td>")
								.append($("<input type='button' class='btn btn-danger btn-xs' value='Delete'>")
									.click(function(){
									deleteDrink(item);
									})
								)
							)
						);
				}
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
			if(item.deleted){
				return;
			}else{
				$("#meals-table-body").append($("<tr>").attr("id", "meal"+item.id)
						.append($("<td>")
								.text(item.name)
							)
							.append($("<td>")
								.text(item.description)
							)
							.append($("<td>")
								.text(item.price)
							)
							.append($("<td>")
								.append($("<input type='button' class='btn btn-success btn-xs' value='Edit'>")
									.click(function(){
									openUpdateMeal(item);
									})
								)
							)
							.append($("<td>")
								.append($("<input type='button' class='btn btn-danger btn-xs' value='Delete'>")
									.click(function(){
									deleteMeal(item);
									})
								)
							)
						);
				}
		
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
				$("#meals-btn").click(function(){
					$("#drinks-btn").removeClass("active");
					$("#meals-btn").addClass("active");
					$("#drinks-div").addClass("hide-me");
					$("#meals-div").removeClass("hide-me");
				});
				$("#drinks-btn").click(function(){
					$("#meals-btn").removeClass("active");
					$("#drinks-btn").addClass("active");
					$("#drinks-div").removeClass("hide-me");
					$("#meals-div").addClass("hide-me");
				});
				$("#editRest").click(function(){
									$("#modalInformation").modal('toggle');
									});
				$("#addMeal").click(function(){
									$("#modalMeal").modal('toggle');
									});
				$("#addDrink").click(function(){
									$("#modalDrink").modal('toggle');
									});
				$("#location-btn").click(function(){
					$('#us3').locationpicker({
	                     location: {
	                         latitude: restaurant.latitude,
	                         longitude: restaurant.longitude
	                     },
	                     radius: 0,
	                     inputBinding: {
	                         latitudeInput: $('#us3-lat'),
	                         longitudeInput: $('#us3-lon'),
	                         locationNameInput: $('#us3-address')
	                     },
	                     enableAutocomplete: true,
	                     markerIcon: 'img/map-marker-2-xl.png'
	                 });
	                 $('#us6-dialog').on('shown.bs.modal', function () {
	                     $('#us3').locationpicker('autosize');
	                 });
					$("#us6-dialog").modal('toggle');
				});
				$("#save-location-btn").click(function(){
					setLocation($("#us3-lat").val(),$("#us3-lon").val());
				});
				
				getDrinks(restaurant);
				getMeals(restaurant);
		});

		$("#r-name").attr("value", restaurant.name);
		$("#r-description").text(restaurant.description);

	});
		
	
}

function openUpdateDrink(drink)
{

	var name = $('#drinks-table-body #drink'+drink.id).children('td:eq(0)').text();
	var description = $('#drinks-table-body #drink'+drink.id).children('td:eq(1)').text();
	var price = $('#drinks-table-body #drink'+drink.id).children('td:eq(2)').text();
	$("#update-drink-form input[name=id]").val(drink.id);
	$("#update-drink-form input[name=name]").val(name);
	$("#update-drink-form textarea[name=description]").val(description);
	$("#update-drink-form input[name=price]").val(price);
	$("#updateDrinkModal").modal("toggle");
}

function openUpdateMeal(meal)
{

	var name = $('#meals-table-body #meal'+meal.id).children('td:eq(0)').text();
	var description = $('#meals-table-body #meal'+meal.id).children('td:eq(1)').text();
	var price = $('#meals-table-body #meal'+meal.id).children('td:eq(2)').text();
	$("#update-meal-form input[name=id]").val(meal.id);
	$("#update-meal-form input[name=name]").val(name);
	$("#update-meal-form").find('textarea').val(description);
	$("#update-meal-form input[name=price]").val(price);
	$("#updateMealModal").modal("toggle");

}


function setLocation(lat, lon){
	var s = {latitude:lat, longitude:lon};
	$.ajax({
		url: "/updateLocation",
		type:"POST",
		data: JSON.stringify(s),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
				$("#us6-dialog").modal('toggle');
		}
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
	$.ajax({
		url: "/addDrink",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
				$("#modalDrink").modal('toggle');
				$("#drinks-table-body").prepend($("<tr>").attr("id", "drink"+data.id)
						.append($("<td>")
								.text(data.name)
							)
							.append($("<td>")
								.text(data.description)
							)
							.append($("<td>")
								.text(data.price)
							)
							.append($("<td>")
								.append($("<input type='button' class='btn btn-success btn-xs' value='Edit'>")
									.click(function(){
									openUpdateDrink(data);
									})
								)
							)
							.append($("<td>")
								.append($("<input type='button' class='btn btn-danger btn-xs' value='Delete'>")
									.click(function(){
									deleteDrink(data);
									})
								)
							)
						);

			},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}


function updateDrink(){
	var $form = $("#update-drink-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
	var s = JSON.stringify(data);
	$.ajax({
		url: "/updateDrink",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
				$("#updateDrinkModal").modal('toggle');
				$('#drinks-table-body #drink'+data.id).children('td:eq(0)').text(data.name);
				$('#drinks-table-body #drink'+data.id).children('td:eq(1)').text(data.description);
				$('#drinks-table-body #drink'+data.id).children('td:eq(2)').text(data.price);
				},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
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
		success: function(data) {
				$("#modalMeal").modal('toggle');
				$("#meals-table-body").prepend($("<tr>").attr("id", "meal"+data.id)
							.append($("<td>")
									.text(data.name)
							)
							.append($("<td>")
								.text(data.description)
							)
							.append($("<td>")
								.text(data.price)
							)
							.append($("<td>")
								.append($("<input type='button' class='btn btn-success btn-xs' value='Edit'>")
									.click(function(){
									updateMeal(data);
									})
								)
							)
							.append($("<td>")
								.append($("<input type='button' class='btn btn-danger btn-xs' value='Delete'>")
									.click(function(){
									deleteMeal(data);
									})
							)
						)
					);

			},
		error: function (jqXHR, textStatus, errorThrown) {
	        if (jqXHR.status === 401) {
	        	
	        } else {
	            window.alert("an unexpected error occured: " + errorThrown);
	        }
	    }
	});
}


function updateMeal(){
	var $form = $("#update-meal-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
	var s = JSON.stringify(data);
	$.ajax({
		url: "/updateMeal",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
				$("#updateMealModal").modal('toggle');
				$('#meals-table-body #meal'+data.id).children('td:eq(0)').text(data.name);
				$('#meals-table-body #meal'+data.id).children('td:eq(1)').text(data.description);
				$('#meals-table-body #meal'+data.id).children('td:eq(2)').text(data.price);
				
			},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}


function deleteMeal(meal){
	var s = JSON.stringify(meal);
	$.ajax({
		url: "/deleteMeal",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
			$(window).trigger('hashchange');
			//$('#meals-table-body tr#meal'+meal.id).remove();
			},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}


function deleteDrink(drink){
	var s = JSON.stringify(drink);
	$.ajax({
		url: "/deleteDrink",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
			$(window).trigger('hashchange');
			//$('#drinks-table-body tr#drink'+drink.id).remove();
			},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
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
	$("#visit-section").hide();
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