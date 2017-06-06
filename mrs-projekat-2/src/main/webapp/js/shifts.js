function showShifts(){
	$.ajax({
        url: "/getShifts",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            $("#app-div").load("shifts.html #shifts-section", function(){
            	$("#shifts-table-body").empty();
            	$.each(data, function(i, item){
            		displayShift(item);
            	});
            	$("#addShiftButton").click(function(){
            		$("#shift-error").html("");
            		$("#shift-name").val("");
            		$("#modalShift").modal('toggle');
            	});
            	$("#modals-div").load("shifts.html #modals", function(){
                	console.log("usaooo");
                	$('#timepicker1').timepicker({
                		minuteStep: 1,
                        showSeconds: false,
                        showMeridian: false
                	});
                    $('#timepicker2').timepicker({
                		minuteStep: 1,
                        showSeconds: false,
                        showMeridian: false
                        });
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

function addShift(){
	var $form = $("#add-shift-form");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#add-error").text("Fill all the fields").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
	$.ajax({
		url: "/addShift",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
				$("#modalShift").modal('toggle');
				displayShift(data);

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

function displayShift(item){
	$("#shifts-table-body").prepend($("<tr>")
			.append($("<td>")
				.text(item.name)
			)
			.append($("<td>")
				.text(item.startTime)
			)
			.append($("<td>")
				.text(item.endTime)
			)
		);

}