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
