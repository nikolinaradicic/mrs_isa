function addEmployee(){
	var $form = $("#addEmployee");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#addEmployee-error").text("Fields must be filled in").css("color","red");
		return;
	}
	var s = JSON.stringify(data);
	console.log(data);
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
		complete: function(data) {
			if (data.responseJSON){
				location.href = "indexSysMan.html";
			}
			else{
				console.log(data);
				$("#addEmployee-error").text("Invalid form").css("color","red");
			}
		}
	});
}