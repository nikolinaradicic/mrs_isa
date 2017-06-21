function rastegni(){
document.getElementById('toggleProfile').addEventListener('click', function () {
  [].map.call(document.querySelectorAll('.profile'), function(el) {
    el.classList.toggle('profile--open');
  });
});
}
	

function getUser(){
	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
				if(data.role =="ROLE_BIDDER" || data.role =="ROLE_BARTENDER" || data.role == "ROLE_WAITER" || data.role == "ROLE_CHEF")
				{
					if(!data.enabled){
						$("body").load("changePass1.html #changePassword");
						return;
					}
				}
			
				$("body").load("indexSysMan.html #container", function(){
					$.getScript("js/common-scripts.js", function(){
						startApp();
						$("#account-name").text(data["name"]);
						if (data.role == "ROLE_GUEST"){
							$("#restaurant-manage").hide();
							$("#employee-manage").hide();
							$("#bids-menu").hide();
							$("#manage").hide();
							$("#calendar").hide();
							$("#chart").hide();
							$("#order").hide();
							if(window.location.hash==''){
								checkRequests();
								getAllVisits(); // home page, show the default view
							}else{
								$(window).trigger( "hashchange" ); // user refreshed the browser, fire the appropriate function
							}
						}
						
	
						else if(data.role == "ROLE_SYSTEM_MANAGER"){
							$("#employee-manage").hide();
							$("#friend-manage").hide();
							$("#poruke").remove();
							$("#friends-section1").hide();
							$("#calendar").hide();
							$("#chart").hide();
							$("#order").hide();
							$("#bids-menu").hide();
							$("#guestsRestaurants").hide();
							if(window.location.hash==''){
								getRestaurants(); // home page, show the default view
							}else{
								$(window).trigger( "hashchange" ); // user refreshed the browser, fire the appropriate function
							}	
						}
						
						else if(data.role == "ROLE_RESTAURANT_MANAGER"){
							$("#restaurant-manage").hide();
							$("#friend-manage").hide();
							$("#poruke").remove();
							$("#manage").hide();
							$("#friends-section1").hide();
							$("#calendar").hide();
							$("#chart").hide();
							$("#order").hide();
							$("#bids-menu").hide();
							$("#guestsRestaurants").hide();
							if(window.location.hash==''){
								displayRestaurant(data.restaurant); // home page, show the default view
							}else{
								$(window).trigger( "hashchange" ); // user refreshed the browser, fire the appropriate function
							}
							
						}
						else if(data.role =="ROLE_BIDDER"){
							$("#restaurant-manage").hide();
							$("#employee-manage").hide();
							$("#friend-manage").hide();
							//$("#poruke").remove();
							$("#manage").hide();
							$("#calendar").hide();
							$("#chart").hide();
							$("#order").hide();
							$("#guestsRestaurants").hide();
							if(window.location.hash==''){
								 // home page, show the default view
								displayBids();
							}else{
								$(window).trigger( "hashchange" ); // user refreshed the browser, fire the appropriate function
							}
							
						}
						else if(data.role == "ROLE_BARTENDER"){
							$("#restaurant-manage").hide();
							$("#employee-manage").hide();
							$("#friend-manage").hide();
							$("#poruke").remove();
							$("#manage").hide();
							$("#chart").hide();
							$("#order").hide();
							$("#bids-menu").hide();
							$("#guestsRestaurants").hide();
							if(window.location.hash==''){
								// home page, show the default view
								getDrinksBartender();
							}else{
								$(window).trigger( "hashchange" ); // user refreshed the browser, fire the appropriate function
							}
						}
						else if(data.role == "ROLE_CHEF"){
							$("#restaurant-manage").hide();
							$("#employee-manage").hide();
							$("#friend-manage").hide();
							$("#poruke").remove();
							$("#manage").hide();
							$("#chart").hide();
							$("#order").hide();
							$("#bids-menu").hide();
							$("#guestsRestaurants").hide();
							if(window.location.hash==''){
								// home page, show the default view
								getMealsChef();
							}else{
								$(window).trigger( "hashchange" ); // user refreshed the browser, fire the appropriate function
							}
						}
						else if(data.role=="ROLE_WAITER"){
							$("#restaurant-manage").hide();
							$("#employee-manage").hide();
							$("#friend-manage").hide();
							$("#poruke").remove();
							$("#manage").hide();
							$("#bids-menu").hide();
							$("#guestsRestaurants").hide();
							if(window.location.hash==''){
								// home page, show the default view
							}else{
								$(window).trigger( "hashchange" ); // user refreshed the browser, fire the appropriate function
							}
						}
						if(typeof stompClient === 'undefined' || !stompClient){
							setupWebSockets(data);
						}
						getNotifications();
					});
				});
			},
		error: function (jqXHR, textStatus, errorThrown) {

	    		$("body").load("login.html #login-div");
	        },
	});
}


var num_notifications = 0;


function addNotification(notification){
	num_notifications++;
	$("#dodatiZahteve").append($("<li>").attr("id", "notif" + notification.id)
						 .append($("<a href='#menu'>")
							.append($("<span class='photo'>")
								.append($("<img alt='avatar'>").attr('src','img/fr-11.jpg'))
								)
							.append($("<span class='subject'>")
									.append($("<span class='from'>").text(notification.text))
								)
							.append($("<input class='button' type='button' value='Ok'>")
									.hover(function(e) {
										$(this).css("background-color",e.type === "mouseenter"?"#b3b3e6":"#6666cc");
									}).css('border','1px')
									.css('background-color','#6666cc')
									.css('width','80')
									.css('height','30')
									.css('color','#fff')
									.css('border-radius','3px')
									.click( function() {
											notifSeen(notification.id);
											})
									)
								)
							);
}

function notifSeen(id){
	$.ajax({
		url: "api/updateNotification/" + id,
		type:"POST",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
			$("#notif"+id).remove();
			num_notifications--;
			$("#brojZahteva").text(num_notifications);
		},
		error: function (jqXHR, textStatus, errorThrown) {

	        }
	});
}

function getNotifications(){
	$.ajax({
		url: "api/getNotifications",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
			$.each(data, function(i, item){
				addNotification(item);
			});

			$("#brojZahteva").text(num_notifications);
			if(num_notifications > 0){
				$("#brojZahtevaPoruka").text("You have new notifications.");
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {

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
			}
		}
	});
}

function changeData() {
	var $form = $("#changeData");
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
				location.href = "#";
			}
			else{
				$("#data-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function changePass() {
	var $form = $("#changePass");
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
				$("#password-error").text("Invalid form").css("color","red");
			}
		}
	});
}


function changePass1() {
	var $form = $("#changePass");
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
				$(window).trigger("hashchange");
			}
			else{
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
	$.ajax({
		url: "/api/restManagerRegistration",
		type:"POST",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		data: s,
		complete: function(data) {
			if (data.responseJSON){
				location.href = "#";
			}
			else{
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

			location.href = "#";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showResponse(jqXHR.status, errorThrown);
        }
	});
}

function addBidder() {
	var $form = $("#add-bidder-form");
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
