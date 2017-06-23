	var meals = [];
	var drinks= [];		
	var ord_meals=[];
	var ord_drinks=[];
	var number_drinks=0;
	var number_meals=0;
	var num_meal=0;
	var num_drink=0;
	var num_of_orders=0;
	var chef_meals=[];
	var bartender_drinks=[];
function setMeals(){
	var counter_meals=0;
	var temp_meals=0;
	//meals=[];
	//drinks=[];
	$.ajax({
		url: "/getMeals1",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$.each(data.responseJSON, function(i, item){
				if(!item.deleted){
					counter_meals++;
				}
			});
			console.log(counter_meals);
			temp_meals=counter_meals;
			$.each(data.responseJSON, function(i, item){
			if(item.deleted){
			console.log("hiiii");
				return;
			}else{
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
								
			}
			});
			console.log(meals);
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
				if(!item.deleted){
					counter_drinks++;
				}
			});
			console.log(counter_drinks);
			temp_drinks=counter_drinks;
			$.each(data.responseJSON, function(i, item){
			console.log(item);
			if(item.deleted){
				return;
			}else{
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
				}							
			});
			$("#drink-body").append($("<br>"));
		}
	});
	console.log(drinks);
	$("#meal-form").append($("<tr>")
								.append($("<h4>")
									.append($("<input id='bbb' type='button' class='btn btn-default' value='Create' onclick='showModal()'>"))
								)
							);
	
}

function confirmMeals(sto){
	chef_meals=[];
	bartender_drinks=[];
	var data=$('#meal-form').serializeArray();
	console.log(data);
	var found=false;

	var counter=0;
	var temp_counter=0;

	var vrednost=0;
	var data_temp=[];
	var data_drinks=[];
	var data_number_dr=[];
	var data_number_ml=[];
	var broj_num=0;
	var broj=0;
	
	//jela 
	broj_zaj=0;
	broj_zap=0;
	var broj_pica=0;
	var broj_jela=0;
	for(var i=0;i<data.length;i++){
		if(data[i].name=="jelo"){
		//jela
			data_temp[broj_zaj]=data[i];
			broj_zaj++;
		}else if(data[i].name=="quantity_drinks" && data[i].value!=""){
		//quantity
			data_number_dr[broj_pica]=data[i].value;
			broj_pica++;
		}else if(data[i].name=="quantity_meals" && data[i].value!=""){
		//quantity
			data_number_ml[broj_jela]=data[i].value;
			broj_jela++;
		}else if(data[i].name=="pice"){
		//pica
			data_drinks[broj_zap]=data[i];
			broj_zap++;
		}
	}
	console.log(data_temp);
	console.log(data_drinks);
	console.log(data_number_dr);
	console.log(data_number_ml);
	
	for(var i in data_number_ml){
		if(i<0){
			found=true;
		}
	}
	for(var i in data_number_dr){
		if(i<0){
			found=true;
		}
	}
	
	if(found==true){
		window.alert("Kolicine ne mogu biti manje od 1!");
	}
	
	var pomocni_broj=0;
	counter=0;
	temp_counter=counter;
	var temp=temp_counter;
	var temp1=counter;
	num_drink=0;
	for(var i in drinks){
		for(var j=0;j<data_drinks.length;j++){
			if((drinks[i].name)==data_drinks[j].value){
				drinks[i].quantity=data_number_dr[pomocni_broj];
				bartender_drinks[num_drink]=drinks[i];
				num_drink++;
				pomocni_broj++;
				if(drinks[i].quantity==null){
					window.alert("Morate uneti kolicinu pica "+drinks[i].name+"!");
					return;
				}
			}
		}
	}
	num_meal=0;
	var pomocni_broj=0;
	for(var i in meals){
		//console.log(meals[i]);
		for(var j =0;j<data_temp.length;j++){
			//console.log(data_temp[j].value);
			if((meals[i].name)==data_temp[j].value){
			console.log(meals[i].name);
				meals[i].quantity=data_number_ml[pomocni_broj];
				chef_meals[num_meal]=meals[i];
				num_meal++;
				pomocni_broj++;
				if(drinks[i].quantity==null){
					window.alert("Morate uneti kolicinu jela "+meals[i].name+"!");
					return;
				}
			}
		}
	}
	
	var order={meals: chef_meals,
           		drinks: bartender_drinks,
           		table: sto};
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
}

function getMealsChef(){
	var num=0;
	$.ajax({
		url: "/getBartenderDrinks",
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
				//$("#meals-body").empty();
			$.each(data.responseJSON, function(i, item){
			console.log(item.id);
							$("#meals-body").append($("<tr>")
											.append($("<td>")
												.append($("<label class='fa fa-info-circle'>")
																 .text("  Order #"+item.id))
											)
											.append($("<td>").append($("<label class='fa fa-info-circle'>")
																 .text("  Quantity")
																 ))
			
            								.append($("<td>").append($("<label class='fa fa-info-circle'>")
            													.text("  Action")
            								))
								)
				$.each(item.meals,function(i,meal){
						$("#meals-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+meal.meal.name))
											)
											.append($("<td>")
												.append($("<label>").text("  "+meal.quantity))
											)
											.append($("<td>")
												.append($("<div>")
													.append($("<input type='button' value='Accept' style='margin-right: 5px'  class='btn btn-warning btn-xs'>").click(function(){
														acceptMeal(meal);
													})
													)
													.append($("<input type='button' value='Prepared' style='margin-right: 5px' class='btn btn-success btn-xs'>").click(function(){
														preparedMeal(meal);
													})
												)
												)
												
											)
										);
				});								
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
				if(item.drinks!=0){
				$("#drinks-body").append($("<tr>")
											.append($("<td>")
												.append($("<label class='fa fa-info-circle'>")
																 .text("  Order #"+item.id))
											)
											.append($("<td>").append($("<label class='fa fa-info-circle'>")
																 .text("  Quantity")
																 ))
			
            								.append($("<td>").append($("<label class='fa fa-info-circle'>")
            													.text("  Action")
            								))
								)
				}
				$.each(item.drinks, function(i, drink){
						$("#drinks-body")
										.append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+drink.drink.name))
											)
											.append($("<td>")
												.append($("<label>").text("  "+drink.quantity))
											)
											.append($("<td>")
												.append($("<input type='button' id='acceptDrinkButton' value='Accept'  style='margin-right: 5px' class='btn btn-warning btn-xs'>").click(function(){
														acceptDrink(drink);
													})
												)
												.append($("<input type='button' value='Prepared' style='margin-right: 5px' class='btn btn-success btn-xs'>").click(function(){
														preparedDrink(drink);
													})
												)
											)
										);
						});							
			});
		}
	});
	
}

function getMyOrder(){

	$.ajax({
		url: "/getWorkingShiftWaiter",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
		if(data.responseJSON==undefined){
					window.alert("Vasa smena je zavrsena!");
					location.href="#";
					return;
				}
		}
	});


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
			console.log(item);
			if(item.drinks==0  && item.meals==0){
				return;
			}else{
				$("#orders-body").append($("<tr>")
											.append($("<td id='forChange'>")
												.append($("<label class='fa fa-info-circle'>")
																 .text("  Order #"+item.id))
											)
											.append($("<td>").append($("<label class='fa fa-info-circle'>")
																 .text("  Quantity")
																 ))
			
            								.append($("<td>").append($("<label class='fa fa-info-circle'>")
            													.text("  Action")
            								))
											.append($("<td>").append($("<label class='fa fa-info-circle'>")
																 	.text("  Status")
															)
											)
											
										);						
				$.each(item.meals, function(i, mm){
				console.log(mm);
					$("#orders-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+mm.meal.name))
											)
											.append($("<td>")
												.append($("<label>").text("  "+mm.quantity))
											)
											.append($("<td>")
												.append($("<input type='button' value='Edit'  style='margin-right: 5px' class='btn btn-success btn-xs'>").click(function(){
														editMeal(mm);
													})
												)
												.append($("<input type='button' value='Delete'  style='margin-right: 5px' class='btn btn-danger btn-xs'>").click(function(){
														console.log("brisem");
														deleteItemMeal(mm,item.id);
														
													})
												)
												
												
											)
											.append($("<td>")
												.append($("<span class='label label-success'>").text(mm.status))
											)
											
										);
				});
				$.each(item.drinks, function(i, dd){
					$("#orders-body").append($("<tr>")
											.append($("<td>")
												.append($("<label>").text("  "+dd.drink.name))
											)
											.append($("<td>")
												.append($("<label>").text("  "+dd.quantity))
											)
											.append($("<td>")
												.append($("<input type='button' value='Edit' style='margin-right: 5px'  class='btn btn-success btn-xs'>").click(function(){
														editDrink(dd);
													})
												)
												.append($("<input type='button' value='Delete' style='margin-right: 5px'  class='btn btn-danger btn-xs'>").click(function(){
														deleteItemDrink(dd,item.id);
													})
												)
											)
											.append($("<td>")
												.append($("<span class='label label-success'>").text(dd.status))
											)
										);
				});			
				$("#orders-body").append($("<tr>")
								.append($("<td>"))
								.append($("<td>"))
								.append($("<td>"))
								.append($("<td>")
									.append($("<input type='button' value='Create Check' style='margin-right: 5px'  class='btn btn-default btn-xs'>").click(function(){
														getCheck(item);
													})
									
								)

								
							));
							}					
			});
		}
	});
	
	
}

function editMeal(meal){
	if(meal.status=="Accepted" || meal.status=="Prepared"){
		window.alert("Nije moguce menjati porudzinu!");
		return;
	}
	$("#modalInformationMeal").modal('toggle');
	$("#r-name-meal").html(meal.meal.name);
	$("#r-quantity-meal").attr("value",meal.quantity);	
	$("#idMeal").attr("value",meal.id);
}

function editDrink(drink){
	if(drink.status=="Accepted" || drink.status=="Prepared"){
		window.alert("Nije moguce menjati porudzinu!");
		return;
	}
	console.log("udjem u metodu");
	$("#modalInformation").modal('toggle');
	$("#r-name").html(drink.drink.name);
	$("#r-quantity").attr("value",drink.quantity);	
	$("#id").attr("value",drink.id);
}

function setInfo(){
	
	var data= $("#id");
	console.log(data[0].value);
	var id=data[0].value;
	var quantity=$("#r-quantity");
	
	var vrednost=quantity[0].valueAsNumber;
	if(vrednost<1){
		window.alert("Vrednost pica ne moze biti nula ili manja od nule!");
		return;
	}
	$("#modalInformation").modal('toggle');
	var itemDrink={id:id, quantity:vrednost};
	$.ajax({
		url: "/updateItemDrink",
		type:"POST",
		data :JSON.stringify(itemDrink),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
			$("#app-div").load("showOrders.html #showOrder",function(){
				getMyOrder();
			});
		},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}

function setInfoMeal(){
	
	var data= $("#idMeal");
	console.log(data[0].value);
	var id=data[0].value;
	var quantity=$("#r-quantity-meal");
	var vrednost=quantity[0].valueAsNumber;
	if(vrednost<1){
		window.alert("Vrednost jela ne moze biti nula ili manja od nule!");
		return;
	}
	$("#modalInformationMeal").modal('toggle');
	var itemMeal={id:id, quantity:vrednost};
	$.ajax({
		url: "/updateItemMeal",
		type:"POST",
		data :JSON.stringify(itemMeal),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
			window.location.reload(true/false);
		},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}

function acceptMeal(meal){
	if(meal.status!="Not Accepted"){
		window.alert("Vec ste prihvatili jelo!");
		return;
	}else{
	console.log(meal);
	meal.status="Accepted";
	var itemMeal=meal;
	console.log(itemMeal);
		$.ajax({
		url: "/updateItemMealStatus",
		type:"POST",
		data :JSON.stringify(itemMeal),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				//window.location.reload(true/false);
				$(window).trigger(" hashchange ");
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
	}
}

function preparedMeal(meal){
	if(meal.status!="Accepted"){
		if(meal.status=="Prepared"){
			window.alert("Jelo je vec spremno!");
			return;
		}else if(meal.status=="Not Accepted"){
			window.alert("Jelo prvo morate prihvatiti!");
			return;
		}
		
	}else{
	console.log(meal);
	meal.status="Prepared";
	var itemMeal=meal;
	console.log(itemMeal);
		$.ajax({
		url: "/updateItemMealStatus",
		type:"POST",
		data :JSON.stringify(itemMeal),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				//window.location.reload(true/false);
				$(window).trigger(" hashchange ");
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
	}
}


function acceptDrink(drink){
	if(drink.status!="Not Accepted"){
		window.alert("Vec ste prihvatili pice!");
		return;
	}else{
	drink.status="Accepted";
	var itemDrink=drink;
		$.ajax({
		url: "/updateItemDrinkStatus",
		type:"POST",
		data :JSON.stringify(itemDrink),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				//window.location.reload(true/false);
				$(window).trigger(" hashchange ");
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
	}
}

function preparedDrink(drink){
	if(drink.status!="Accepted"){
		if(drink.status=="Prepared"){
			window.alert("Pice je vec spremno!");
			return;
		}else if(drink.status=="Not Accepted"){
			window.alert("Pice prvo morate prihvatiti!");
			return;
		}
		
	}else{
	drink.status="Prepared";
	var itemDrink=drink;
		$.ajax({
		url: "/updateItemDrinkStatus",
		type:"POST",
		data :JSON.stringify(itemDrink),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				//window.location.reload(true/false);
				$(window).trigger(" hashchange ");
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
	}
}

function deleteItemMeal(meal,id){
	console.log(id);
	console.log("usao u brisanje");
	var item={idItemMeal:meal.id,idWaiterOrd:id}
	$.ajax({
		url: "/deleteItemMeal",
		type:"POST",
		data :JSON.stringify(item),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),		
		success: function(data) {
			$(window).trigger("hashchange");
		},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}

function deleteItemDrink(drink,id){
	var item={idItemDrink:drink.id,idWaiterOrd:id};
	$.ajax({
		url: "/deleteItemDrink",
		type:"POST",
		data :JSON.stringify(item),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
			$(window).trigger("hashchange");
		},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}
var table;
var canvas;
var segment;
function showModal(){
	$.ajax({
		url: "/getWorkingShiftWaiter",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if(data.responseJSON==undefined){
				window.alert("Vasa smena je zavrsena!");
				location.href="#";
				return;
			}
			if (data.responseJSON){
				console.log(data.responseJSON);
				if(data.responseJSON.segment==null){
					window.alert("Vasa smena je zavrsena!");
					location.href="#";
					return;
				}
		segment=data.responseJSON.segment;
				canvas = new fabric.CanvasEx("canvas");
				
		if (segment.chart != ""  && segment.chart != null){
			var json = JSON.parse(segment.chart);
			canvas.loadFromJSON(json, canvas.renderAll.bind(canvas));
			setTimeout(function(){
				canvas.forEachObject(function(o) {
					console.log("kreiram Kanvas!!!");
  					o.selectable = true;
  					o.lockMovementX=true;
  					o.lockMovementY=true;
  					o.hasControls = true;
				});
			},1000);
		}	
				console.log(table);		
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
	
	$("#modal-tables").modal('toggle');	
}

function choose(){
table=canvas.getActiveObject();
	console.log(table);

	var x =segment.name;
	var data = {name : table.name};
	$.ajax({
		url: "/getTable/" + x,
		type:"POST",
		data: JSON.stringify(data),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			console.log(data);
			if (data.responseJSON){
				var sto=data.responseJSON;
				confirmMeals(sto);
			}
		}
	});
}

function getCheck(order){
	//pravim racun, trebam izracunati vrednost i ubaciti order
	var price=0;
	var nisu=false;
		$.each(order.meals,function(i,meall){
		console.log(meall);
			if(meall.status=="Prepared" && !meall.bill){
				price+=meall.quantity*meall.meal.price;
			}else{
				nisu=true;
				return;
			}
		});
		$.each(order.drinks,function(i,drinkk){
			if(drinkk.status=="Prepared" && !drinkk.bill){
				price+=drinkk.quantity*drinkk.drink.price;
			}else{
				nisu=true;
				return;
			}
		});
		if(nisu){
			window.alert("Nemoguce kreirati racun.");
			return;
		}
	
	var item={order:order,final_price:price};
	$.ajax({
		url: "/defineVisit",
		type:"POST",
		data :JSON.stringify(item),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				//window.location.reload(true/false);
				window.alert("Racun uspesno kreiran.");
				$(window).trigger(" hashchange ");
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
	
	
}
