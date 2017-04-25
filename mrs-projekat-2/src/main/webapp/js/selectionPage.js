function displayForPersData(){
	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			
			if (data.responseJSON.role=="SYSTEM_MANAGER"){
				$("#friend-manage").hide();
				
			}
		}
	});
}

function toPage() {

	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
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