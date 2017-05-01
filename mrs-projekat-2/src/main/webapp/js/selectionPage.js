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
				$("#changePassword").remove();
				$("#add-restaurant-form").hide();
				$("#restaurants > div").remove();
				$("#changePers").hide();
				$("#dataUser > div").remove();
				$("#add-friend-form").hide();
				$("#bidder-form").hide();
				$.each(data.responseJSON.friends, function(i, item){
					$("#friends-section").append($("<div>").append($("<br>"))
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
										)
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
				checkRequests(getRestaurants1);
			}
			else if(data.responseJSON.role == "SYSTEM_MANAGER"){
				$("#employee-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();	
				$("restaurant-info").remove();
				getRestaurants();
			}
			else if(data.responseJSON.role == "RESTAURANT_MANAGER"){
				$("#restaurant-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();
				displayRestaurant(data.responseJSON.restaurant);
			}
			else if(data.responseJSON.role=="BIDDER"){
				$("#restaurant-manage").hide();
				$("#employee-manage").hide();
				$("#friend-manage").hide();
				$("#poruke").remove();
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
