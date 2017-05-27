function showShifts(){
	$.ajax({
        url: "/getShifts",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            $("#app-div").load("shifts.html #shifts-section");
            $("#modals-div").load("shifts.html #modals", function(){
            	$('#timepicker1').timepicker({
            		minuteStep: 1,
                    showSeconds: false,
                    showMeridian: false
            	});
                $('#timepicker2').timepicker({
            		minuteStep: 1,
                    showSeconds: false,
                    showMeridian: false}
                );
                $("#addShiftButton").click(function(){
            		$("#modalShift").modal('toggle');
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
	
}