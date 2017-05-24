function displayFriends()
{

	$.ajax({
		url: "api/getUserRepresentation",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				$("#top-menu").hide();
				$("#changePassword").hide();
				$("#add-restaurant-form").hide();
				$("#restaurants1").hide();
				$("#changePers").hide();
				$("#dataUser1").hide();
				$("#add-friend-form").hide();
				$("#bidder-form").hide();
				$("#sysMan-form").hide();
				$("#friends-section1").show();
				console.log("usao");
				$.each(data.responseJSON.friends, function(i, item){
					$("#friend-section").append($("<tr>")
											.append($("<td>")
												.append($("<a href='#'>").text(item))
											)
											.append($("<td>")
												.append($("<input type='button' class='button' value='Unfriend'>")
													.click(function(){
													unfriend(item);
													})
													.append($("<i class='fa fa-user-times'>"))
												)
											)
										)
				});
				
			}
		}
	});
}

function unfriend(id){
	var user = {email : id};	
	var s = JSON.stringify(user);
	$.ajax({
		url: "/api/unFriend",
		type:"POST",
		contentType:"application/json",
		data: s,
		dataType:"json",
		complete: function(data) {
			console.log(data.responseJSON);
			if (data.responseJSON){
				location.href = "indexSysMan.html";		
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
			$("#brojZahteva").text(data.responseJSON.requests.length);
			$("#brojZahtevaPoruka").text("You have "+data.responseJSON.requests.length+" requests.");


			
			$.each(data.responseJSON.requests,function(i,item){
				
				$("#dodatiZahteve").append($("<li>").append($("<a href='indexSysMan.html#'>")
														.append($("<span class='photo'>")
																.append($("<img alt='avatar'>").attr('src','img/fr-11.jpg'))
														)
														.append($("<span class='subject'>")
																.append($("<span class='from'>").text("Request from:"+item))
														)
														.append($("<input class='button' type='button' value='Confirm'>").hover(function(e) {
															  $(this).css("background-color",e.type === "mouseenter"?"#b3b3e6":"#6666cc");
														}).css('border','1px').css('background-color','#6666cc').css('width','80').css('height','30').css('color','#fff').css('border-radius','3px').click(function(){
														    acceptFriend(item);
														}))
													)
										);
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

