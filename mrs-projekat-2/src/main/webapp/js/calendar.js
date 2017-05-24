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
					    				console.log(e);
					      				events.push({
					        			start: e.date,
					        			title: "  "+e.restaurant.name
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
	fillEmployeeBox();
}

function fillEmployeeBox(){
	$.ajax({
		type : "GET",
		url: '/getEmployees',
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			$.each(data.responseJSON, function (i, item) {
			    $('#employee-select').append($('<option>', { 
			        value: item.email,
			        text : item.email 
			    }));
			});
			displayCalendar();
		}
	});
}

function addEvent(){
	var $form = $("#add-event-form");
	var data = getFormData($form);
	
	$("#modalEvent").modal('toggle');
	eventData = {
			title: data['employee-name'],
			start: start_date,
			end: end_date
		};
	var check = moment(start_date, 'DD.MM.YYYY').format('YYYY-MM-DD');
	console.log(start_date);
	var send_data = {date : check, employee : {email: data['employee-name']}};
	$.ajax({
		type : "POST",
		url: '/addWorkingShift',
		contentType:"application/json",
		dataType:"json",
		data : JSON.stringify(send_data),
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			console.log(data.responseJSON);
			$('#calendar-div').fullCalendar('renderEvent', eventData, true);
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
		defaultDate: '2017-05-12',
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
	            	console.log(doc);
	                var events = [];
	                $.each(doc, function(i,item){
	                	
	                	events.push({
	                		title: item.employee.email,
	                		start: item.date
	                	});
	                });
	                callback(events);
	            }
	        });
	    },
		
		eventRender: function(event, element) {
			var check = moment(event.start, 'DD.MM.YYYY').format('YYYY-MM-DD');
		    var today = moment(new Date()).format('YYYY-MM-DD');
		    if(check >= today)
		    {
		    	element.append( "<span class='closeon'>X</span>" );
	            element.find(".closeon").click(function() {
	               $('#calendar-div').fullCalendar('removeEvents',event._id);
	            });
		    }
		}
	});
	
}