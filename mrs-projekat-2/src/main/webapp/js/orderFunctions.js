function setMeals(restaurant){
	$.ajax({
		url: "/getMeals",
		type:"POST",
		data: JSON.stringify(restaurant),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$.each(data.responseJSON, function(i, item){
				$("#meal-body").append($("<tr>")
									.append($("<td>")
										.text(item.name)
									)
								);
			});	
		}
	});
}

function setDrinks(restaurant){
	$.ajax({
		url: "/getDrinks",
		type:"POST",
		data: JSON.stringify(restaurant),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$.each(data.responseJSON, function(i, item){
				$("#drink-body").append($("<tr>")
									.append($("<td>")
										.text(item.name)
									)
								);
			});	
		}
	});
}