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

function register()
{
	var $form = $("#register-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
	
	$.ajax({
		url: "api/guestRegistration",
		type:"POST",
		data: data,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "index.html";
			}
			else{
				$("#form-error").text("Username already exists").css("color","red");
			}
		}
	});
}

function login() {
	var $form = $("#login");
	console.log($form);
	var data = getFormData($form);
	console.log(data);
	if(!validateForm(data)){
		$("#login-error").text("Please enter your email and password").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "api/login",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				location.href = "index.html";
			}
			else{
				console.log(s);
				$("#login-error").text("Invalid username or password").css("color","red");
			}
		}
	});
}

function changePass() {
	var $form = $("#changePass");
	console.log($form);
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
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "index.html";
			}
			else{
				console.log(data);
				$("#password-error").text("Invalid form").css("color","red");
			}
		}
	});
}

function changeData() {
	var $form = $("#changeData");
	console.log($form);
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
		complete: function(data) {
			if (data.responseJSON){
				console.log(data);
				location.href = "index.html";
			}
			else{
				console.log(data);
				$("#data-error").text("Invalid form").css("color","red");
			}
		}
	});
}

