


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
				$("restaurant-info").remove();
				$("#manage").hide();
				$("#calendar").hide();
				checkRequests(getRestaurants1);
				$("#chart").hide();
			}
			else if(data.responseJSON.role == "SYSTEM_MANAGER"){
				$("#employee-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();	
				$("restaurant-info").remove();
				getRestaurants();
				$("#chart").hide();
			}
			else if(data.responseJSON.role == "RESTAURANT_MANAGER"){
				$("#restaurant-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();
				$("#manage").hide();
				$("#calendar").hide();
				$("#chart").hide();
				displayRestaurant(data.responseJSON.restaurant);
			}
			else if(data.responseJSON.role=="BIDDER"){
				$("#restaurant-manage").hide();
				$("#employee-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();
				$("#manage").hide();
				$("#chart").hide();
			}
			else if(data.responseJSON.role=="BARTENDER"){
				$("#restaurant-manage").hide();
				$("#employee-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();
				$("#manage").hide();
				$("#chart").hide();
			}
			else if(data.responseJSON.role=="CHEF"){
				$("#restaurant-manage").hide();
				$("#employee-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();
				$("#manage").hide();
				$("#chart").hide();
			}else if(data.responseJSON.role=="WAITER"){
				$("#restaurant-manage").hide();
				$("#employee-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();
				$("#manage").hide();
			}
			$("#account-name").text(data.responseJSON["name"]);
			callback(data.responseJSON.restaurant);
		}
	
	});
}

