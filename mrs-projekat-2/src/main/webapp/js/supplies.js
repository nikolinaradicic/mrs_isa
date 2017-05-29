function showSupplies(){
	$.ajax({
		url: "/getLists",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function (data, textStatus, jqXHR) {
	            showActiveSupplies(data);
	        },
	    error: function (jqXHR, textStatus, errorThrown) {
	            if (jqXHR.status === 401) {
	            	
	            } else {
	                window.alert("an unexpected error occured: " + errorThrown);
	            }
	        }
	});
}

function showSelectedSupply(){
	var url = $.param.fragment();
	var id = url.split("=")[1];
	$.ajax({
		url: "/getSelectedList/"+id,
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function (data, textStatus, jqXHR) {
			$("#app-div").load("GroceryList.html #list-section", function(){
				$("#description").text(data.text);
				$("#startDate").text(moment(data.startDate).format('YYYY-MM-DD'));
				$("#endDate").text(moment(data.endDate).format('YYYY-MM-DD'));
				});
	        },
	    error: function (jqXHR, textStatus, errorThrown) {
	            if (jqXHR.status === 401) {
	            	
	            } else {
	                window.alert("an unexpected error occured: " + errorThrown);
	            }
	        }
	});
}

function showActiveSupplies(supplies){
	$("#modals-div").load("supplies.html #modals", function(){
		var date = new Date();
		date.setDate(date.getDate()-1);

		$('#datepicker1').datepicker({ 
		    startDate: date,
		    format: 'yyyy-mm-dd'
		});
		

		$('#datepicker2').datepicker({ 
		    startDate: date,
		    format: 'yyyy-mm-dd'
		});
	
	$("#app-div").load("supplies.html #supply-section", function(){
		$("#add-supply-button").click(function(){
			$("#supply-error").html("");
    		$("#supply-text").val("");
			$("#modalSupplies").modal("toggle");
		});
		$("#supply-table-body").empty();
		$.each(supplies, function(i,item){
			displaySupply(item);
		});
	});
	
});

}

function displaySupply(item){
	$("#supply-table-body").prepend($("<tr>")
			.append($("<td>")
				.append($("<a href =" + "showSupply?supplyId="+
		        			item.id+">"+item.text.substring(0,30)+"... </a>"))
			)
			.append($("<td>")
				.text(moment(item.startDate).format('YYYY-MM-DD'))
			)
			.append($("<td>")
				.text(moment(item.endDate).format('YYYY-MM-DD'))
			)
		);
}

function addGroceryList()
{
	var $form = $("#add-supply-form");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#add-error").text("Fill all the fields").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	console.log(s);
	$.ajax({
		url: "/addList",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				$("#modalSupplies").modal("toggle");
				displaySupply(data.responseJSON);
			}
			else{
				$("#supply-error").text("Invalid form").css("color","red");
			}
		}
	});

}