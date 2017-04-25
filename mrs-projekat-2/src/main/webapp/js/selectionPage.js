function displayFriends()
{

	$.ajax({
		url: "api/getUserRepresentation",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				$("#changePassword").hide();
				$("#add-restaurant-form").hide();
				$("#restaurants > div").remove();
				$("#changePers").hide();
				$("#dataUser > div").remove();
				$("#add-friend-form").hide();
				$("#bidder-form").hide();
				$.each(data.responseJSON.friends, function(i, item){
					$("#friends-section").append($("<div>")
							.append($("<img src='img/fr-11.jpg' width='150'>"))
							.append($("<h4>").text("E-mail: "+item))
							);
				});
				
			}
		}
	});
}

function acceptFriend(id){
	var user = {email : id};
	var s = JSON.stringify(user);
	$.ajax({
		url: "/api/acceptFriend",
		type:"POST",
		contentType:"application/json",
		data : s,
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				location.href = "indexSysMan.html";
			}
		}
	});
}

function checkRequests(callback){
	$.ajax({
		url: "/api/getUserRepresentation",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			console.log(data.responseJSON);
			$.each(data.responseJSON.requests, function(i, item) {
				$("#friend-request").append($("<b>").text("Friend request from: " + item)
											)
									.append($("<button>Confirm</button>").click(function(){
									    acceptFriend(item);
									}));
			});
			
			callback();
		}
	
	});

}

function displayForPersData(callback){
	$.ajax({
		url: "/api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON.role == "GUEST"){
				$("#restaurant-manage").hide();
				$("#employee-manage").hide();
				checkRequests(getRestaurants);
			}
			else if(data.responseJSON.role == "SYSTEM_MANAGER"){
				$("#employee-manage").hide();
				$("#friend-manage").hide();
				getRestaurants();
			}
			else if(data.responseJSON.role == "RESTAURANT_MANAGER"){
				$("#restaurant-manage").hide();
				$("#friend-manage").hide();
			}
			$("#account-name").text(data.responseJSON["name"]);
			callback();
		}
	
	});
}

function changePersData(){
	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			
			if (data.responseJSON){
				$("#name-field").attr("value", data.responseJSON.name);
				$("#lastname-field").attr("value", data.responseJSON.lastname);
				$("#email-field").attr("value", data.responseJSON.email);
			}
		}
	});
}
