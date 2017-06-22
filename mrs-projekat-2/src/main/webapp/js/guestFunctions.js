function displayFriends()
{

	$.ajax({
		url: "api/getFriends",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				console.log(data.responseJSON);
				console.log($("#friendTableBody"));
				$.each(data.responseJSON, function(i, item){
					console.log(item);
					$("#friendTableBody").append($("<tr>")
											.append($("<td>")
												.text(item.name)
											)
											.append($("<td>")
												.text(item.lastname)
											)
											.append($("<td>")
												.text(item.email)
											)
											.append($("<td>")
												.append($("<input type='button' class='button' value='Unfriend'>")
													.click(function(){
													unfriend(item.email);
													})
												)
											)
										);
				});
				
			}
		}
	});
}
function getGuests(value){
	$.ajax({
		url: "getGuests",
		type:"POST",
		contentType:"application/json",
		dataType:"json",
		data: value,
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				console.log(data.responseJSON);
				$("#friend-body").empty();
				$.each(data.responseJSON, function(i, item){
					$("#friend-body").append($("<tr>")
											.append($("<td>")
												.text(item.email)
											)
											.append($("<td>")
												.text(item.name)
											)
											.append($("<td>")
												.text(item.lastname)
											)
											.append($("<td>")
												.append($("<button class='button'>Add</button>")
													.click(function(){
													addFriend(item, i);
													})
												)
											)
										);
				});
			}
		}
	});
	
}

function addFriend(item, i) {

	var s = JSON.stringify(item);
	$.ajax({
		url: "/api/addFriend",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				//console.log($("#friends-table").rows[i].cells[3]);
				//document.getElementById('friends-table').rows[i+1].cells[3].remove();
				document.getElementById('friends-table').rows[i+1].cells[3].innerHTML = "sent";
				//console.log(row);
			}
			else{
				console.log(data);
				$("#addFriend-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function acceptFriend(id,p){
	var user = {email : id};
	var s = JSON.stringify(user);
	$.ajax({
		url: "/api/acceptFriend",
		type:"POST",
		contentType:"application/json",
		data : s,
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				location.href="#";
				p.remove();
			}
		}
	});
}

function checkRequests(){
	$.ajax({
		url: "/api/getUserRepresentation",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			console.log(data.responseJSON);
			$("#brojZahteva").text(data.responseJSON.requests.length);
			$("#brojZahtevaPoruka").text("You have "+data.responseJSON.requests.length+" requests.");


			
			$.each(data.responseJSON.requests,function(i,item){
				
				$("#dodatiZahteve").append($("<li>").append($("<a href='#menu'>")
														.append($("<span class='photo'>")
																.append($("<img alt='avatar'>").attr('src','img/fr-11.jpg'))
														)
														.append($("<span class='subject'>")
																.append($("<span class='from'>").text("Request from:"+item))
														)
														.append($("<input class='button' type='button' value='Confirm'>").hover(function(e) {
															  $(this).css("background-color",e.type === "mouseenter"?"#b3b3e6":"#6666cc");
														}).css('border','1px').css('background-color','#6666cc').css('width','80').css('height','30').css('color','#fff').css('border-radius','3px').click(function(){
														    acceptFriend(item,$(this));
														}))
													)
										);
			});
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
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			console.log(data.responseJSON);
			if (data.responseJSON){
				location.href = "#";		
			}
		}
	});
}

function sortFriendsName(){
	$.ajax({
		url: "/sortFriendsName",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
				displayFriendsName(data.responseJSON);	
		}
	});
}

function displayFriendsName(friends){
	$("#friendTableBody").empty();
	$.each(friends, function(i, item){
		$("#friendTableBody").append($("<tr>")
								.append($("<td>")
									.text(item.name)
								)
								.append($("<td>")
									.text(item.lastname)
								)
								.append($("<td>")
									.text(item.email)
								)
								.append($("<td>")
									.append($("<input type='button' class='button' value='Unfriend'>")
											.click(function(){
											 unfriend(item.email);
											})
											.append($("<i class='fa fa-user-times'>"))
									
									)
								));
	});
	
	
}

function sortFriendsLastName(){
	$.ajax({
		url: "/sortFriendsLastName",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
				displayFriendsLastName(data.responseJSON);	
		}
	});
}

function displayFriendsLastName(friends){
	$("#friendTableBody").empty();
	$.each(friends, function(i, item){
		$("#friendTableBody").append($("<tr>")
								.append($("<td>")
									.text(item.name)
								)
								.append($("<td>")
									.text(item.lastname)
								)
								.append($("<td>")
									.text(item.email)
								)
								.append($("<td>")
									.append($("<input type='button' class='button' value='Unfriend'>")
											.click(function(){
											 unfriend(item.email);
											})
											.append($("<i class='fa fa-user-times'>"))
									
									)
								));
	});
}

function getAllVisits(){

				console.log("cao");
	$.ajax({
			url: "/getMyVisits",
			type:"GET",
			contentType:"application/json",
			dataType:"json",
			headers: createAuthorizationTokenHeader(),
			complete: function(data) {
				if (data.responseJSON){
				console.log("cao");
		$("#modals-div").load("ModalRank.html #modals-div",function(){
		$("#app-div").append($("<section class='wrapper' id='visit-section'>")
								.append($("<div class='login-form'>")
									.append($("<div class='wrapper'>")
										.append($("<div class='row mt'>")
											.append($("<div class='col-md-12'>")
												.append($("<form class='content-panel'>")
													.append($("<table class='table table-striped table-advance table-hover'>")
														.append($("<h4>")
															.append($("<i class='fa fa-tripadvisor'>").text("  Visits"))
														)
														.append($("<tbody id='visits-body'>")
															.append($("<tr>")
																.append($("<th>").text("Restaurant Name"))
																.append($("<th>").text("Visit Date"))
																.append($("<th>").text("Rank"))
															)
														
														)
													)
												)
											)
										)
									)
								)
								
							);
					$.each(data.responseJSON,function(i,visit){
					if(visit.marked){
						return;
					}else{
						$("#visits-body").append($("<tr>")
											.append($("<th>").text(visit.reservation.restaurant.name))
											.append($("<th>").text(moment(visit.date).format('DD/MM/YYYY')))
											.append($("<th>")
												.append(function(){
													if(!visit.marked){
													return $("<input type='button' value='Rank' style='margin-right: 5px' class='btn btn-success btn-xs'>").click(function(){
														rank(visit);
													});
													}else{
													return $("<label>").text("Ranked");
													}
												}
											)
										));
					}
					});
		}		
		); 
				}
			}
		});

}

function rank(visit){
	$("#modalRank").modal('toggle');
	$("#restaurant-mark").attr('value',visit.id);
}

function addMark(){
	$("#modalRank").modal('toggle');
	var data=$("#restaurant-mark").val();
	var mealm=$("#mealsm");
	var ocenaJela=mealm[0].valueAsNumber;
	var r=$("#restaurantm");
	var ocenaRestorana=r[0].valueAsNumber;
	var s=$("#servicem");
	var ocenaUsluge=s[0].valueAsNumber;
	console.log(ocenaJela);
	console.log(ocenaRestorana);
	console.log(ocenaUsluge);

	var item = {meal_rank:ocenaJela,
				restaurant_mark:ocenaRestorana,
				service_mark:ocenaUsluge,
				visit_id:data
	};
	console.log(item);
	$.ajax({
		url: "/rank",
		type:"POST",
		contentType:"application/json",
		data: JSON.stringify(item),
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			console.log(data.responseJSON);
			if (data.responseJSON){
				window.alert(data.responseJSON.id);		
			}
		}
	});
	
	
}