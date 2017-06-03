	var meals = [];
	var drinks= [];
	
		
	var ord_meals=[];
	var ord_drinks=[];

	
	var number_drinks=0;
	var number_meals=0;
	var num_meal=0;
	var num_drink=0;
	
	var num_of_orders=0;
function setMeals(){

	var counter_meals=0;
	var temp_meals=0;
	$.ajax({
		url: "/getMeals1",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {

			$.each(data.responseJSON, function(i, item){
				counter_meals++;
				console.log(counter_meals);
			});
			temp_meals=counter_meals;
			$.each(data.responseJSON, function(i, item){
				$("#meal-body").append($("<tr>")
									.append($("<td>")
										.append($("<input type='checkbox'>").attr('name',item.id).attr('value',item.name))
										.append($("<label>").text("  "+item.name))
									)
								);
								meals[temp_meals-counter_meals]=item;
								counter_meals--;

			});
			$("#meal-body").append($("<br>"));
			
		}
	});
	var counter_drinks=0;
	var temp_drinks=0;
	$.ajax({
		url: "/getDrinks1",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
		
			$.each(data.responseJSON, function(i, item){
				counter_drinks++;
			});
			temp_drinks=counter_drinks;
			$.each(data.responseJSON, function(i, item){
				$("#drink-body").append($("<tr>")
									.append($("<td>")
										.append($("<input type='checkbox'>").attr('name',item.id).attr('value',item.name))
										.append($("<label>").text("  "+item.name))
									)
								);
								drinks[temp_drinks-counter_drinks]=item;
								counter_drinks--;
								
								
			});
			$("#drink-body").append($("<br>"));
		}
	});
	
	$("#meal-form").append($("<tr>")
								.append($("<h4>")
									.append($("<input id='bbb' type='button' class='button btn' value='Submit' onclick='confirmMeals()'>"))
								)
							);
	
}

function confirmMeals(){
	var data=$('#meal-form').serializeArray();
	console.log(data);
	if(!validateForm(data)){
		$("#addManager-error").text("Fields must be filled in").css("color","red");
		return;
	}
	ord_meals=[];
	ord_drinks=[];
	var counter=0;
	var temp_counter=0;
	for(var i in data){
		counter++;
		console.log("counter");
		console.log(data[i]);
	}
	
	temp_counter=counter;
	var temp=temp_counter;
	var temp1=counter;
	for(var i in drinks){
		for(var j in data){
			if((drinks[i].name)==data[j].value){
				ord_drinks[temp_counter-counter]=drinks[i];
				bartender_drinks[num_drink]=drinks[i];
				num_drink++;
				counter--;
			}
		}
	}
	/*for(var i in ord_drinks){
		console.log("za porudzbinu pica");
		console.log(ord_drinks[i]);
	}*/

	for(var i in meals){
		for(var j in data){
			console.log("stavljam hranu");
			console.log(data[j]);
			if((meals[i].name)==data[j].value){
			console.log("stavljam hranu");
				ord_meals[temp_counter-temp1]=meals[i];
				chef_meals[num_meal]=meals[i];
				num_meal++;
				temp1--;
			}
		}
	}
	for(var i in ord_meals){
		console.log("stvarna za porudzbinu hrana");
		console.log(ord_meals[i]);
	}
	
	for(var i in meals){
		console.log("za porudzbinu hrana");
		console.log(meals[i]);
	}
	
	var order={"meals": ord_meals,
           		"drinks": ord_drinks};
	$.ajax({
		url: "/setMealss",
		type:"POST",
		data :JSON.stringify(order),
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
	saveMealChef();
	saveDrinkBartender();
}

function saveMealChef(){
	console.log(chef_meals);
	var order={"meals": chef_meals};
	$.ajax({
		url: "/saveChefMeals",
		type:"POST",
		data :JSON.stringify(order),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				location.href = "#";
				num_of_orders++;
			}
			else{
				console.log(data);
			}
		}
	});
}

function saveDrinkBartender(){
	var order={"drinks": bartender_drinks};
	$.ajax({
		url: "/saveBartenderDrinks",
		type:"POST",
		data :JSON.stringify(order),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				location.href = "#";
				num_of_orders++;
			}
			else{
				console.log(data);
			}
		}
	});
}

function getMealsChef(){
	var num=0;
	$.ajax({
		url: "/getChefMeals",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
		$("#app-div").html("");
				$("#app-div").append($("<section class='wrapper' id='chef_section'>")
								.append($("<div class='login-form'>")
									.append($("<div class='wrapper'>")
										.append($("<div class='row mt'>")
											.append($("<div class='col-md-12'>")
												.append($("<table class='table table-striped table-advance table-hover'>")
													.append($("<h2>")
														.append($("<i class='fa fa-cutlery'>").text("  Orders"))
													)
													//.append($("<hr>"))
													.append($("<tbody id='meals-body'>")
													)
												)
											)
										)
									)
								)
				
				);
				var meal=[];
			$.each(data.responseJSON, function(i, item){
					for(var j in item.meals){
						$("#meals-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+item.meals[j].name))
											)
										);
					}
					
										
			});
		}
	});
	
}
function getDrinksBartender(){
	var num=0;
	$.ajax({
		url: "/getBartenderDrinks",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
		$("#app-div").html("");
				$("#app-div").append($("<section class='wrapper' id='bartender_section'>")
								.append($("<div class='login-form'>")
									.append($("<div class='wrapper'>")
										.append($("<div class='row mt'>")
											.append($("<div class='col-md-12'>")
												.append($("<table class='table table-striped table-advance table-hover'>")
													.append($("<h2>")
														.append($("<i class='fa fa-beer'>").text("  Orders"))
													)
													//.append($("<hr>"))
													.append($("<tbody id='drinks-body'>")
													)
												)
											)
										)
									)
								)
							);

			$.each(data.responseJSON, function(i, item){
					for(var j in item.drinks){
						$("#drinks-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+item.drinks[j].name))
											)
										);
						
						//.append($("<h3>").text(item.drinks[j].name));	
					}
					
										
			});
		}
	});
	
}





