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
										.append($("<input type='checkbox'>").attr('name',"jelo").attr('value',item.name))
										.append($("<label>").text("  "+item.name))
									)
									.append($("<td>")
										.append($("<input id='dynamic-number-meals' type='number' required autocomplete='off' name='quantity_meals'>"))
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
										.append($("<input type='checkbox'>").attr('name','pice').attr('value',item.name))
										.append($("<label>").text("  "+item.name))
									)
									.append($("<td>")
										.append($("<input id='dynamic-number-drinks' type='number' required autocomplete='off' name='quantity_drinks'>"))
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
	var quantity_meals=$('#dynamic-number-meals').serializeArray();
	var quantity_drinks=$('#dynamic-number-drinks').serializeArray();
	//console.log(quantity);
	//console.log(data);
	if(!validateForm(data)){
		$("#addManager-error").text("Fields must be filled in").css("color","red");
		return;
	}
	ord_meals=[];
	ord_drinks=[];
	var counter=0;
	var temp_counter=0;
	for(var i in quantity_drinks){

		console.log("counter");
		console.log(quantity_drinks);
	}
	var vrednost=0;
	var data_temp=[];
	var data_drinks=[];
	var data_number_dr=[];
	var data_number_ml=[];
	var broj_num=0;
	var broj=0;
	
	//jela 
	broj=0;
	var broj_pica=0;
	var broj_jela=0;
	for(var i=0;i<data.length;i++){
		if(data[i].name!="quantity_meals" && data[i].name!="quantity_meals" && data[i].name=="jelo"){
			data_temp[broj]=data[i];
			console.log("jelaaaa");
			console.log(data_temp[broj].value);
			broj++;
			console.log(broj);
		}
		if(data[i].name=="quantity_drinks"){
		
			data_number_dr[broj_pica]=data[i].value;
			broj_pica++;
		}
		
		if(data[i].name=="quantity_meals"){
			data_number_ml[broj_jela]=data[i].value;
			broj_jela++;
		}
	}
	
	//pica
	broj=0;
	for(var i=0;i<data.length;i++){
		if(data[i].name!="quantity_meals" && data[i].name!="quantity_meals" && data[i].name=="pice"){
			data_drinks[broj]=data[i];
			console.log("picaaaa");
			console.log(data_drinks[broj].value);
			broj++;
			console.log(broj);
		}
	}
	
	var pomocni_broj=0;
	temp_counter=counter;
	var temp=temp_counter;
	var temp1=counter;
	for(var i in drinks){
		for(var j=0;j<data_drinks.length;j++){
		console.log("kkk");
			if((drinks[i].name)==data_drinks[j].value){
				drinks[i].quantity=data_number_dr[pomocni_broj];
				console.log("objekat pica");
				console.log(drinks[i]);
				ord_drinks[temp_counter-counter]=drinks[i];
				bartender_drinks[num_drink]=drinks[i];
				num_drink++;
				counter--;
				pomocni_broj++;
			}
		}
	}
	var pomocni_broj=0;
	for(var i in meals){
		for(var j =0;j<data_temp.length;j++){
			//console.log(data[j].value);
			if((meals[i].name)==data_temp[j].value){
				console.log("stavljam hranu");
				meals[i].quantity=data_number_ml[pomocni_broj];
				console.log(meals[i]);
				ord_meals[temp_counter-temp1]=meals[i];
				chef_meals[num_meal]=meals[i];
				num_meal++;
				temp1--;
				pomocni_broj++;
			}
		}
	}
	for(var i in ord_meals){
		console.log("stvarna za porudzbinu hrana");
		console.log(ord_meals[i]);
	}
	
	for(var i in meals){
		//console.log("za porudzbinu hrana");
		//console.log(meals[i]);
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
	console.log("ovde");
	saveMealChef();
	console.log("ovdwe");
	saveDrinkBartender();
}

function saveMealChef(){
		for(var i in chef_meals){
		console.log("chefmeals");
		console.log(chef_meals[i]);
	}

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
				$("#meals-body").empty();
			$.each(data.responseJSON, function(i, item){
			console.log(item);
						$("#meals-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+item.name))
											)
											.append($("<td>")
												.append($("<label>").text("  "+item.quantity))
											)
										);								
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
			
			$("#drinks-body").empty();
			$.each(data.responseJSON, function(i, item){
						$("#drinks-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+item.name))
											)
											.append($("<td>")
												.append($("<label>").text("  "+item.quantity))
											)
										);							
			});
		}
	});
	
}

function getMyOrder(){
	var num=0;
	$.ajax({
		url: "/getMyOrders",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$("#orders-body").empty();
			$.each(data.responseJSON, function(i, item){
			
				$("#orders-body").append($("<tr>")
											.append($("<td id='forChange'>")
												.append($("<label class='fa fa-info-circle'>")
																 .text("  Order #"+item.id))
											)
										);
				for(var i in item.meals){
					$("#orders-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+item.meals[i].name))
											)
										);
				}
				for(var i in item.drinks){
					$("#orders-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+item.drinks[i].name))
											)
										);
				}				
				$("#orders-body").append($("<tr>")
								.append($("<h4>")
									.append($("<input id='bbb' type='button' class='button btn' value='Submit' onclick='confirmMeals()'>"))
								)
							);					
			});
		}
	});
}



