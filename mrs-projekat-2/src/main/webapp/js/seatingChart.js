var stolovi = [];
$(document).ready(function() {
	//showCanvas();
	$("#addSegmentButton").click(function(){
		$("#modalSegment").modal('toggle');
	});
	
	displayForPersData(fillSegmentBox);
});


function addSegment(){
	var $form = $("#add-segment-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#form-error").text("All fields are required").css("color","red");
		return;
	}
		
	var s = JSON.stringify(data);
	
	$.ajax({
		type : "POST",
		url : "/addSegment",
		data: s,
		dataType: 'json',
		contentType : "application/json",
		success: function(data){
			console.log(data);
			$("#modalSegment").modal('toggle');
			 $('#segmentSelect').append($('<option>', { 
			        value: data.name,
			        text : data.name 
			    }));
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR 5: " + errorThrown);
		}
	});
	
}

function fillSegmentBox(restaurant){
	console.log(restaurant);
	$.each(restaurant.segments, function (i, item) {
	    $('#segmentSelect').append($('<option>', { 
	        value: item.name,
	        text : item.name 
	    }));
	});
}

