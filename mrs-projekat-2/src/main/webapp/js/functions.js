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
				
				$("#dataUser > h4").remove();			
				$("#restaurants > div").remove();
				$.each(data.responseJSON, function(i, item) {
					$("#restaurants").append($("<div class='row mt'>")
											.append($("<div class='col-md-4'>")
												.append($("<div class='white-panel pn'>")
													.append($("<div class='white-header'>")
														.append($("<h4>").text(item.name)))
													.append($("<img src='img/city1.jpg' class='img-circle' width='100'>"))
													.append($("<h4>").text(item.description))
													.append($("<a class='button'>").attr("href", "addManager.html?id=" +item.id)
														.text("Add Manager")
														.css('background-color','#c6c6ec')
														.css('border','0px solid')
														.css('border-radius','7px')
														.css('padding','10px 20px'))
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
			if (data.responseJSON){
				location.href = "index.html";
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
				console.log(data.responseJSON);
				if(data.responseJSON.role == "SYSTEM_MANAGER"){
					location.href = "indexSysMan.html";
				}
				if(data.responseJSON.role ==  "RESTAURANT_MANAGER"){
					location.href="indexRestMan.html";
				}
				if(data.responseJSON.role == "GUEST"){
					location.href="indexGuest.html";
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
				console.log(data);
				location.href = "index.html";
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
				location.href = "index.html";
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
		url: "api/addFriend",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "index.html";
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
				if (data.responseJSON.role == "SYSTEM_MANAGER"){
					callback();
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
				$("#restaurants").hide();
				$("#dataUser > h4").remove();
				$("#dataUser").append($("<br>"));
				$("#dataUser").append($("<img src='img/fr-11.jpg' width='150'>"));
				$("#dataUser").append($("<h4>").text("E-mail: "+data.responseJSON["email"]));
				$("#dataUser").append($("<h4>").text("First Name: "+data.responseJSON["name"]));
				$("#dataUser").append($("<h4>").text("Last Name: "+data.responseJSON["lastname"]));
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
				location.href = "index.html";
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
		url: "/api/restManagerRegistration",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "index.html";
			}
			else{
				console.log(data);
				$("#addBidder-error").text("Invalid form").css("color","red");
			}
		}
	});
}
function getFriends(){

	$.ajax({
		url: "/restaurants",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				$("#dataUser > h4").remove();			
				$("#restaurants > div").remove();
				$("#friends > h4").hide;
				
				$.each(data.responseJSON, function(i, item) {
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
