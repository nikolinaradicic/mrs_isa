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
				$.each(data.responseJSON, function(i, item){
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
				$.each(data.responseJSON, function(i, item){
					$("#friend-body").append($("<tr>")
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

function acceptFriend(id){
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
				location.href = "#";
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
		headers: createAuthorizationTokenHeader(),
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