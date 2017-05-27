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
                    $("#addShiftButton").click(function(){
                		$("#modalShift").modal('toggle');
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
		complete: function(data) {
			if (data.responseJSON){
				$("#modalShift").modal('toggle');
				displayShift(data.responseJSON);
			}
			else{
				$("#add-error").text("Invalid form").css("color","red");
			}
		}
	});
	
}

function displayShift(item){
	console.log(item);
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