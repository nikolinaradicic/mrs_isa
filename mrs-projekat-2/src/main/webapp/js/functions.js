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
