function getUser(callback){
	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				$("#account-name").text(data.responseJSON["name"]);
				if (data.responseJSON.role == "ROLE_GUEST"){
					$("#restaurant-manage").hide();
					$("#employee-manage").hide();
					$("#manage").hide();
					$("#calendar").hide();
					$("#chart").hide();
					$("#order").hide();
					
					checkRequests(getRestaurants1);
				}
				else if(data.responseJSON.role == "ROLE_SYSTEM_MANAGER"){
					$("#employee-manage").hide();
					$("#friend-manage").hide();
					$("#poruke").remove();
					$("#friends-section1").hide();
					$("#calendar").hide();
					$("#chart").hide();
					$("#order").hide();
					getRestaurants();
				}
				else if(data.responseJSON.role == "ROLE_RESTAURANT_MANAGER"){
					$("#restaurant-manage").hide();
					$("#friend-manage").hide();
					$("#poruke").remove();
					$("#manage").hide();
					$("#friends-section1").hide();
					$("#calendar").hide();
					$("#chart").hide();
					$("#order").hide();
					displayRestaurant(data.responseJSON.restaurant);
				}
				else if(data.responseJSON.role =="ROLE_BIDDER"){
					$("#restaurant-manage").hide();
					$("#employee-manage").hide();
					$("#friend-manage").hide();
					$("#poruke").remove();
					$("#manage").hide();
					$("#calendar").hide();
					$("#chart").hide();
					$("#order").hide();
				}
				else if(data.responseJSON.role == "ROLE_BARTENDER"){
					$("#restaurant-manage").hide();
					$("#employee-manage").hide();
					$("#friend-manage").hide();
					$("#poruke").remove();
					$("#manage").hide();
					$("#chart").hide();
					$("#order").hide();
					getDrinksBartender();
					
				}
				else if(data.responseJSON.role == "ROLE_CHEF"){
					$("#restaurant-manage").hide();
					$("#employee-manage").hide();
					$("#friend-manage").hide();
					$("#poruke").remove();
					$("#manage").hide();
					$("#chart").hide();
					$("#order").hide();
					getMealsChef();
				}
				else if(data.responseJSON.role=="ROLE_WAITER"){
					$("#restaurant-manage").hide();
					$("#employee-manage").hide();
					$("#friend-manage").hide();
					$("#poruke").remove();
					$("#manage").hide();
				}
			}
			else{
				console.log(data);
			}
		}
	});
}

function displayData(){

	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				$("#persData")
								.append($("<div class='col-lg-4 col-md-4 col-sm-4 mb'>")
									.append($("<div class='content-panel pn'>")
										.append($("<div id='spotify'>")
											.append($("<div class='col-xs-4 col-xs-offset-8'>"))
													.append($("<div class='sp-title'>")	
													)
											).append($("<br>"))
											.append($("<br>"))
											.append($("<h4>").text("  E-mail: "+data.responseJSON["email"])
															.css('padding-left','20px')
														)
														.append($("<h4>").text("  First Name: "+data.responseJSON["name"])
															.css('padding-left','20px')
														)
														.append($("<h4>").text("  Last Name: "+data.responseJSON["lastname"])
															.css('padding-left','20px')
														)
													
									)
								);
							
			}
			else{
				console.log(data);
			}
		}
	});
}

function changeData() {
	var $form = $("#changeData");
	console.log($form);
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#data-error").text("Fill all the fields").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "api/changePersonalData",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "#";
			}
			else{
				console.log(data);
				$("#data-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function changePass() {
	var $form = $("#changePass");
	console.log($form);
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#password-error").text("Fill all the fields").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "api/changePass",
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
				console.log(data);
				$("#password-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function changePersData(){
	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {	
			if (data.responseJSON){
				$("#name-field").attr("value", data.responseJSON.name);
				$("#lastname-field").attr("value", data.responseJSON.lastname);
				$("#email-field").attr("value", data.responseJSON.email);
			}
		}
	});
}

function registerManager(){
	var $form = $("#add-manager-form");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#addManager-error").text("Fields must be filled in").css("color","red");
		return;
	}
	var url = $.param.fragment();
	var id = url.split("=")[1];
	data.restaurant = {id : id};
	var s = JSON.stringify(data);
	console.log(s);
	$.ajax({
		url: "/api/restManagerRegistration",
		type:"POST",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		data: s,
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "#";
			}
			else{
				console.log(data);
				$("#addManager-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function addSysMan() {
	var $form = $("#addSysMan");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#addSysMan-error").text("Fields must be filled in").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "/api/sysManagerRegistration",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function (data, textStatus, jqXHR) {
            console.log("uspjesno");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showResponse(jqXHR.status, errorThrown);
        },
		complete: function(data) {
			if (data.responseJSON){
				location.href = "#";
			}
			else{
				console.log(data);
				$("#addSysMan-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function addBidder() {
	var $form = $("#add-bidder-form");
	console.log($form);
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#addBidder-error").text("Fields must be filled in").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "/bidderRegistration",
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
				console.log(data);
				$("#addBidder-error").text("Invalid form").css("color","red");
			}
		}
	});
}


function addEmployee(){
	var $form = $("#add-employee-form");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#addEmployee-error").text("Fields must be filled in").css("color","red");
		return;
	}
	var s = JSON.stringify(data);
	console.log(data);
	var val = "";
	if (data.role == 0){
		val = "Waiter";
	}
	else if(data.role == 4){
		val = "Chef";
	}
	else{
		val = "Bartender";
	}
	$.ajax({
		url: "/register" + val,
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
				console.log(data);
				$("#addEmployee-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function register()
{
	var $form = $("#register-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "api/guestRegistration",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseJSON){
				if(data.responseJSON["firstTime"]=="notvisited"){
					location.href = "#ConfirmEmail";
				}
			}
			else{
				$("#form-error").text("Username already exists").css("color","red");
			}
		}
	});
}
