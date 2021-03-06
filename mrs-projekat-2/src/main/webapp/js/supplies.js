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


function displayBids(){
	$.ajax({
		url: "/getBids",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function (data, textStatus, jqXHR) {	
				$("#app-div").load("bids.html #bids-section", function(){
					$.each(data, function(i, item) {
						$("#bids-table-body").append($("<tr>")
								.append($("<td>")
									.text(item.restaurant)
								)
								.append($("<td>")
									.text(item.price)
								)
								.append($("<td>")
									.text(item.message)
								)
								.append($("<td>")
									.text(item.status)
								)
							);
	
					});
	
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

function displayDemands(){
	$.ajax({
		url: "/getActiveLists",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function (data, textStatus, jqXHR) {
			$("#modals-div").load("bidding.html #modals", function(){
				$("#app-div").load("bidding.html #active-lists", function (){showActiveLists(data);});
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
			console.log(data);
			$("#app-div").load("GroceryList.html #list-section", function(){
				$("#description").text(data.text);
				$("#startDate").text(moment(data.startDate).format('YYYY-MM-DD'));
				$("#endDate").text(moment(data.endDate).format('YYYY-MM-DD'));
				if(data.offers){
					displayOffers(data);
				}
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

function showActiveLists(lists){
	$.each(lists, function(i,item){
		$("#accordion").append($("<div>").attr("class", "panel panel-default")
							.append($("<div>").attr("class", "panel-heading")
									.append($("<h4>").attr("class","panel-title")
											.append($("<a>").attr("data-toggle", "collapse")
											.attr("data-parent", "#accordion")
											.attr("href", "#collapse" + i)
											.append(item.restaurant.name)
											)
											
									)
								)
							.append($("<div>").attr("class",function(){
								if (i == 0)
									return "panel-collapse collapse in";
								return "panel-collapse collapse";
							})
								.attr("id", "collapse" + i)
								.append($("<div>").attr("class", "panel-body")
										.append($("<h5>").append("End date: " + moment(item.startDate).format('YYYY-MM-DD')))
										.append(item.text)
										.append($("<br>"))
										.append($("<p>").attr("id", "bid-p"+item.id)
												.html(function(){
													if (item.offer)
														return "Your bid: " + item.offer.price;
												})
											)
										.append($("<button>").click(function(){
													showBidDialog(item.id);
												}).html("Bid"))
										
								)
							)
				
			);
	});
}

function displayOffers(data){
	$.each(data.offers, function(i,item){
		$("#bids-div").prepend($('<div>')
					  .attr("class", "list-group-item")
					  .append($('<div>')
					    .attr("class","media")
						.append($('<div>')
						  .attr("class", "media-left media-top")
						  .append($('<img>')
								.attr("src", "img/person.jpg")
								.attr("class", "pull-left")
								.css("width","60px")
							)
						  
						)
					    .append($('<div>')
						  .attr("class", "media-body")
						  .append($('<h4>')
							.attr("class","media-heading")
							.text(item.bidder.name + " " + item.bidder.lastname)
						)
					)
				)
				.append($('<div>').append($('<p>')
							.text(item.message)
							.css("word-wrap","break-word")
							)
							.append($('<p>')
								.text("Asking price: " + item.price)
									.append(function (){
											if(data.acceptedOffer == null)
												return $('<button>')
												.attr("class","btn btn-link")
												.attr("id", "like-button")
												.text("Accept")
												.click(function(){
													acceptOffer(item, data.id);
												});
											if(item.accepted)
												return "Accepted";
											
											return "Rejected";
											
											}
									)
								)
						)
					);

	});
}

function acceptOffer(data, supId){
	var s = {id: data.id, price: data.price, groceryListId:supId};
	$.ajax({
		url: "/acceptOffer",
		type:"POST",
		contentType:"application/json",
		dataType:"json",
		data: JSON.stringify(s),
		headers: createAuthorizationTokenHeader(),
		success: function (data, textStatus, jqXHR) {
				if(data.acceptedOffer != null){
		            
				}
				else{

		            window.alert("This offer has been modified. Review and accepted again.");
				}
	            $(window).trigger( "hashchange" );
	        },
	    error: function (jqXHR, textStatus, errorThrown) {
	            if (jqXHR.status === 401) {
	            	
	            } else {
	                window.alert("an unexpected error occured: " + errorThrown);
	            }
	        }
	});
}

function showBidDialog(id){
	$.ajax({
		url: "/getBid/"+id,
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data){
			if (data.responseJSON){
				console.log(data.responseJSON);
				$("#message").html(data.responseJSON.message);
				$("#price").attr("value", data.responseJSON.price);
			}else{
				console.log("tuuuu");
				$("#message").html("");
				$("#price").attr("value", 0);
			}
			$("#groceryList").attr("value", id);
			$("#modalBid").modal("toggle");
		}
	});
}

function addBid(){
	var $form = $("#add-bid-form");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#add-error").text("Fill all the fields").css("color","red");
		return;
	}
	var s = JSON.stringify(data);
	console.log(s);
	$.ajax({
		url: "/addBid",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
				$("#modalBid").modal('toggle');
				$("#bid-p"+data.groceryListId).html("Your bid: " + data.price);

		},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 500) {
            	$("#shift-error").text("Two shifts cannot have the same name").css("color","red");
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
		$("#active-btn").click(function(){
			$("#past-btn").removeClass("active");
			$("#active-btn").addClass("active");
			$("#past-div").addClass("hide-me");
			$("#active-div").removeClass("hide-me");
		});
		$("#past-btn").click(function(){
			$("#active-btn").removeClass("active");
			$("#past-btn").addClass("active");
			$("#past-div").removeClass("hide-me");
			$("#active-div").addClass("hide-me");
		});
		getPastSupplies();
		$.each(supplies, function(i,item){
			displaySupply(item);
		});
	});
	
});

}

function showPastSupplies(data){
	$.each(data, function(i,item){
		$("#supply-table-body-past").prepend($("<tr>")
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
	});
}

function getPastSupplies(){
	$.ajax({
		url: "/getPastLists",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function (data, textStatus, jqXHR) {
	            showPastSupplies(data);
	        },
	    error: function (jqXHR, textStatus, errorThrown) {
	            if (jqXHR.status === 401) {
	            	
	            } else {
	                window.alert("an unexpected error occured: " + errorThrown);
	            }
	        }
	});
}

function displaySupply(item){
	$("#supply-table-body-active").prepend($("<tr>")
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
