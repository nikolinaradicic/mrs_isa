function getFormData($form){
		    var unindexed_array = $form.serializeArray();
		    var indexed_array = {};
	
		    $.map(unindexed_array, function(n, i){
		    	indexed_array[n['name']] = n['value'];
		    });
	
		    return indexed_array;
}

function validateForm(form_data){
	for (var key in form_data){
		if (form_data[key] == "" || form_data[key]==null){
			return false;
		}
	}
	return true;
}

function getRestaurants(){
	$.ajax({
		url: "/restaurants",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				$("#dataUser1").hide();
				$("#friends-section").hide();
				$("#friend-request").hide();
				$("#restaurant-info").hide();
				
				$("#restaurants1").show();
				$.each(data.responseJSON, function(i, item) {
					$("#restaurants").append($("<div class='col-lg-4 col-md-4 col-sm-4 mb'>")
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
		}
	});
}

function getRestaurants1(){
	$.ajax({
		url: "/restaurants",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				$("#friends-section").hide();
				$("#friend-request").hide();
				$("#restaurant-info").hide();
				$("#dataUser1").hide();			
				$("#restaurants1").show();
				$.each(data.responseJSON, function(i, item) {
					$("#restaurants").append($("<div class='col-lg-4 col-md-4 col-sm-4 mb'>")
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
					location.href="changePass1.html"				
				}else{
					location.href = "indexSysMan.html";
				}
			}
			else{
				$("#form-error").text("Username already exists").css("color","red");
			}
		}
	});
}
function login() {
	var $form = $("#login");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#login-error").text("Please enter your email and password").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "api/login",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				if(data.responseJSON["firstTime"]=="notvisited"){
					location.href="changePass1.html";
				}else{
					location.href = "indexSysMan.html";
				}
			}
			else{
				$("#login-error").text("Invalid username or password").css("color","red");
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
		complete: function(data) {
			if (data.responseJSON){
				location.href = "indexSysMan.html";
			}
			else{
				console.log(data);
				$("#password-error").text("Invalid form").css("color","red");
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
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "indexSysMan.html";
			}
			else{
				console.log(data);
				$("#data-error").text("Invalid form").css("color","red");
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
	console.log(s);
	$.ajax({
		url: "/addrestaurant",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				location.href = "indexSysMan.html";
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function addFriend() {
	var $form = $("#addFriend");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#addFriend-error").text("E-mail field must be filled in").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	$.ajax({
		url: "/api/addFriend",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "indexSysMan.html";
			}
			else{
				console.log(data);
				$("#addFriend-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function getUser(callback){
	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				$("#account-name").text(data.responseJSON["name"]);
				if(data.responseJSON.role=="SYSTEM_MANAGER" || data.responseJSON.role=="GUEST"){
					callback(data.responseJSON);
				}
			}
			else{
				console.log(data);
				$("#addFriend-error").text("Invalid form").css("color","red");
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
		complete: function(data) {
			if (data.responseJSON){
				if(data.responseJSON.role!="GUEST"){
					$("#poruke").remove();
				}
				$("#restaurant-info > div").remove();
				$("#add-friend-form").hide();
				$("#changePassword").hide();
				
				$("#add-restaurant-form").hide();
				$("#friends-section").hide();
				$("#friend-request").hide();
				$("#changePers").hide();
				$("#restaurants1").hide();
				$("#bidder-form").hide();
				$("#sysMan-form").hide();
				$("#dataUser1").show();
				$("#calendar").hide();
				$("#friends-section1").hide();
				$("#dataUser").append($("<div class='col-lg-4 col-md-4 col-sm-4 mb'>")
								.append($("<div class='content-panel pn'>")
										.append($("<div id='spotify'>")
											.append($("<div class='col-xs-4 col-xs-offset-8'>"))
													.append($("<div class='sp-title'>")	
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
										)
												
								)
							);
			}
			else{
				console.log(data);
				$("#addFriend-error").text("Invalid form").css("color","red");
			}
		}
	});
}


function registerManager(){
	var $form = $("#addManager");
	console.log($form);
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#addManager-error").text("Fields must be filled in").css("color","red");
		return;
	}
	var id = window.location.search.split("=")[1];
	data.restaurant = {id : id};
	var s = JSON.stringify(data);
	$.ajax({
		url: "/api/restManagerRegistration",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				location.href = "indexSysMan.html";
			}
			else{
				console.log(data);
				$("#addManager-error").text("Invalid form").css("color","red");
			}
		}
	});
}



function addBidder() {
	var $form = $("#addBidder");
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
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "indexSysMan.html";
			}
			else{
				console.log(data);
				$("#addBidder-error").text("Invalid form").css("color","red");
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
		complete: function(data) {
			if (data.responseJSON){
				location.href = "indexSysMan.html";
			}
			else{
				console.log(data);
				$("#addSysMan-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function getFriends(){

	$.ajax({
		url: "/getFriends",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				$("#dataUser1 > h4").remove();			
				$("#restaurants1 > div").remove();
				$("#friends > h4").remove();
				$.each(data.responseJSON, function(i, item) {
					console.log(item);
					$("#friends").append($("<br>"));
					$("#friends").append($("<img src='img/fr-11.jpg' width='150'>"));
					$("#friends").append($("<h4>").text("E-mail: "+data.responseJSON["email"]));
					$("#friends").append($("<h4>").text("First Name: "+data.responseJSON["name"]));
					$("#friends").append($("<h4>").text("Last Name: "+data.responseJSON["lastname"]));
					
				});
				
			}
		}
	});
}
