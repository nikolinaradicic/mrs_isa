var start_date;
var end_date;

function setupCalendarView(){
						$(document).ready(function() {
				  			$.ajax({
				      			url: "/getWorkingShiftsForEmployee",
				    			method: "GET",
				    			datatype: "json",
				    			headers: createAuthorizationTokenHeader()
				  				}).done(function(data) {
				  
					    			var events = [];
					   		 		$.each(data, function(idx, e) {
					      				events.push({
					        			start: e.date,
					        			title: "  "+e.employee.name
					      			});
				    			});
				    			$('#calendar1').show();
				    			$('#calendar1').fullCalendar({
				      				events: events
				    			});
				  			});
						});
}

function setupCalendar(){
	$("#modals-div").load("calendarView.html #modals", function(){
		fillShiftBox();
		$("#step1-btn").click(function () {
		    if($('#shift-select option').size() == 0){
				$("#error1").text("A shift must be selected").css("color", "red");
				return;
			}
		    $("#step1").hide();
		    $("#step2").show();
		    fillEmployeeBox();
		});
		$("#step2-btn").click(function () {
			 if($('#employee-select option').size() == 0){
					$("#error2").text("An employee must be selected").css("color", "red");
					return;
				}
			var co = $('#employee-select').find(":selected").attr("class");
			if(co == "ROLE_WAITER"){
				$("#step2").hide();
			    $("#step3").show();
			    fillSegments();
			}
			else{
				addEvent();
			}
		});
	});
	$('#app-div').load('calendarView.html #calendar', function (){
			displayCalendar();
		});
}

function fillEmployeeBox(){
	var $form = $("#add-event-form");
	var data = getFormData($form);
	var check = moment(start_date, 'DD.MM.YYYY').format('YYYY-MM-DD');
	var send_data = {date : check, shift : {name: data['shift-name']}};
	$.ajax({
		type : "POST",
		url: '/getAvailableEmployees',
		contentType:"application/json",
		dataType:"json",
		data: JSON.stringify(send_data),
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$("#employee-select").empty();
			$.each(data.responseJSON, function (i, item) {
			    $('#employee-select').append($('<option>', { 
			        value: item.email,
			        text : item.email
			    }).attr("class", item.role));
			});
		}
	});
}

function fillSegments(){
	var $form = $("#add-event-form");
	var data = getFormData($form);
	var check = moment(start_date, 'DD.MM.YYYY').format('YYYY-MM-DD');
	var send_data = {date : check, shift : {name: data['shift-name']}};
	$.ajax({
		type : "POST",
		url: '/getAvailableSegments',
		contentType:"application/json",
		dataType:"json",
		data: JSON.stringify(send_data),
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$("#segment-select").empty();
			$.each(data.responseJSON, function (i, item) {
			    $('#segment-select').append($('<option>', { 
			        value: item.name,
			        text : item.name
			    }));
			});
		}
	});
}


function fillShiftBox(){
	$.ajax({
		type : "GET",
		url: '/getShifts',
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$.each(data.responseJSON, function (i, item) {
			    $('#shift-select').append($('<option>', { 
			        value: item.name,
			        text : item.name 
			    }));
			});
		}
	});}

function addEvent(){
	var $form = $("#add-event-form");
	var data = getFormData($form);
	var check = moment(start_date, 'DD.MM.YYYY').format('YYYY-MM-DD');
	var send_data = {date : check, employee : {email: data['employee-name']}, shift : {name: data['shift-name']}};
	var co = $('#employee-select').find(":selected").attr("class");
	if(co == "ROLE_WAITER"){
		if($('#segment-select option').size() != 0) 
			send_data["segment"] = {name: data['segment-name']};
		else{
			$("#error").text("A segment must be assigned to a waiter").css("color", "red");
			return;
		}
	}

	$("#modalEvent").modal('toggle');
	$.ajax({
		type : "POST",
		url: '/addWorkingShift',
		contentType:"application/json",
		dataType:"json",
		data : JSON.stringify(send_data),
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
			eventData = {
					title: data.employee.name,
					start: data.date,
					end: data.date,
					id: data.id
				};
			$('#calendar-div').fullCalendar('renderEvent', eventData, true);
		},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}

function updateEvent(event){
	var check = moment(event.start._d, 'DD.MM.YYYY').format('YYYY-MM-DD');
	var send_data = {date : check, id: event.id};
	$.ajax({
		type : "POST",
		url: '/updateWorkingShift',
		contentType:"application/json",
		dataType:"json",
		data : JSON.stringify(send_data),
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
		},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});
}



function displayCalendar(){
	$('#calendar-div').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		defaultDate: moment(new Date()).format('YYYY-MM-DD'),
		navLinks: true, // can click day/week names to navigate views
		selectable: true,
		selectHelper: true,
		select: function(start, end) {
			var check = moment(start, 'DD.MM.YYYY').format('YYYY-MM-DD');
		    var today = moment(new Date()).format('YYYY-MM-DD');
		    if(check < today)
		    {
		    	window.alert("You cannot change the past days");
		    }
		    else{
		    	start_date = start;
		    	end_date = end;

		    	$("#error1").text("");
		    	$("#error2").text("");
		    	$("#error").text("");
		    	$("#step2").hide();
		    	$("#step3").hide();
		    	$("#step1").show();
		    	$("#modalEvent").modal('toggle');
		    }
			
			$('#calendar-div').fullCalendar('unselect');
		},
		editable: true,
		eventLimit: true, // allow "more" link when too many events
		events: function(start, end, timezone, callback) {
	        $.ajax({
	        	type: "POST",
	            url: '/getWorkingShifts',
	            dataType: 'json',
	            contentType: "application/json",
	    		headers: createAuthorizationTokenHeader(),
	            data: JSON.stringify({
	                start: start,
	                end: end
	            }),
	            success: function(doc) {
	                var events = [];
	                $.each(doc, function(i,item){
	                	
	                	events.push({
	                		title: item.employee.email,
	                		start: item.date,
	                		id: item.id
	                	});
	                });
	                callback(events);
	            }
	        });
	    },
	    eventConstraint: {
            start: moment().format('YYYY-MM-DD'),
            end: '2100-01-01'
        },
        eventClick: function(event){
        	
        },
        eventDrop: function(event, delta, revertFunc) {
        	updateEvent(event);
	 },
	
		eventRender: function(event, element) {
			var check = moment(event.start, 'DD.MM.YYYY').format('YYYY-MM-DD');
		    var today = moment(new Date()).format('YYYY-MM-DD');
		    if(check >= today)
		    {
		    	element.append( "<span class='closeon'>X</span>" );
	            element.find(".closeon").click(function() {
	            	deleteEvent(event);
	            });
		    }
		}
	});
	
}

function deleteEvent(event){
	var send_data = {id: event.id};
	$.ajax({
		type : "POST",
		url: '/deleteWorkingShift',
		contentType:"application/json",
		dataType:"json",
		data : JSON.stringify(send_data),
		headers: createAuthorizationTokenHeader(),
		success: function(data) {
            $('#calendar-div').fullCalendar('removeEvents',event._id);
		},
		error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
	});

}