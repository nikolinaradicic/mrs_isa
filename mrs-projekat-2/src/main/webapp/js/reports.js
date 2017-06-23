function showReports(){
	$("#app-div").load("reports.html #reportSection", function(){
		$("#income-btn").click(function(){
			$("#income-btn").addClass("active");
			$("#waiters-btn").removeClass("active");
			$("#visit-btn").removeClass("active");
			$("#ratings-btn").removeClass("active");
			
			$("#income-div").removeClass("hide-me");
			$("#waiters-div").addClass("hide-me");
			$("#visit-div").addClass("hide-me");
			$("#ratings-div").addClass("hide-me");
		});
		
		
		$("#visit-btn").click(function(){
			$("#visit-btn").addClass("active");
			$("#waiters-btn").removeClass("active");
			$("#income-btn").removeClass("active");
			$("#ratings-btn").removeClass("active");
			
			$("#income-div").addClass("hide-me");
			$("#waiters-div").addClass("hide-me");
			$("#visit-div").removeClass("hide-me");
			$("#ratings-div").addClass("hide-me");
		});
		
		$("#waiters-btn").click(function(){
			$("#waiters-btn").addClass("active");
			$("#income-btn").removeClass("active");
			$("#visit-btn").removeClass("active");
			$("#ratings-btn").removeClass("active");
			
			$("#income-div").addClass("hide-me");
			$("#waiters-div").removeClass("hide-me");
			$("#visit-div").addClass("hide-me");
			$("#ratings-div").addClass("hide-me");
		});
		
		$("#ratings-btn").click(function(){
			$("#waiters-btn").removeClass("active");
			$("#income-btn").removeClass("active");
			$("#visit-btn").removeClass("active");
			$("#ratings-btn").addClass("active");
			
			$("#income-div").addClass("hide-me");
			$("#waiters-div").addClass("hide-me");
			$("#visit-div").addClass("hide-me");
			$("#ratings-div").removeClass("hide-me");
			
			getRating();
		});
		
		var dateToday = new Date();
		$('#start-date').datepicker({format: "dd-mm-yy", maxDate: dateToday});
		$('#end-date').datepicker({format: "dd-mm-yy", maxDate: dateToday});
		
		$('#start-date2').datepicker({format: "dd-mm-yy", maxDate: dateToday});
		$('#end-date2').datepicker({format: "dd-mm-yy", maxDate: dateToday});
		
		$('#start-date3').datepicker({format: "dd-mm-yy", maxDate: dateToday});
		$('#end-date3').datepicker({format: "dd-mm-yy", maxDate: dateToday});
	
		$("#show1").click(function(){
			getVisitChart();
		});
		
		$("#show2").click(function(){
			getIncomeChart();
		});
		
		$("#show3").click(function(){
			getWaitersChart();
		});
		
	});
}

function getRating(){
	$.ajax({
		url: '/getRating',
		type: 'GET',
		dataType: 'json',
		contentType: 'application/json',
		headers: createAuthorizationTokenHeader(),
		success: function(data){
			$("#meal-rating").empty();
			$("#waiter-rating").empty();
			$("#rating").text(data.rating);
			for (var key in data.waiters) {
				$("#waiter-rating").append($("<tr>")
										.append($("<td>").text(key))
										.append($("<td>").text(data.waiters[key]))
										);
			}
			
			for (var key in data.meals) {
				$("#meal-rating").append($("<tr>")
										.append($("<td>").text(key))
										.append($("<td>").text(data.meals[key]))
										);
			}
		}
	});

}

function getVisitChart(){
	var s = new Object();

	var start_date = $('#start-date').datepicker('getDate');
	var end_date = $('#end-date').datepicker('getDate');
	if (start_date.getTime() > end_date.getTime()){
		window.alert("Please choose a valid period");
		return;
	}
	s.start = start_date;
	s.end = end_date;
	$.ajax({
		url: '/visitChart',
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(s),
		headers: createAuthorizationTokenHeader(),
		success: function(data){
				showVisitChart(data);
		}
	});

}

function showVisitChart(data){
	var div = $('#canvas_div').empty();
	div.html('<canvas id="canvas" height="300" style="background:white;"></canvas>');
	var ctx = $('#canvas');
	console.log(data);
	var data = {
		labels: data.labels,
		datasets: [
			{
				label: "Visits",
				fill: false,
				lineTension: 0.1,
				backgroundColor: "rgba(75,192,192,0.4)",
				borderColor: "rgba(75,192,192,1)",
				borderCapStyle: 'butt',
				borderDash: [],
				borderDashOffset: 0.0,
				borderJoinStyle: 'miter',
				pointBorderColor: "rgba(75,192,192,1)",
				pointBackgroundColor: "#fff",
				pointBorderWidth: 1,
				pointHoverRadius: 5,
				pointHoverBackgroundColor: "rgba(75,192,192,1)",
				pointHoverBorderColor: "rgba(220,220,220,1)",
				pointHoverBorderWidth: 2,
				pointRadius: 1,
				pointHitRadius: 10,
				data: data.data,
			}
		]
	};
	chart_1 = new Chart(ctx, {
		type: 'line',
		data: data,
		options: {
			maintainAspectRatio: false,
			fullWidth: false,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true
					}
				}]
			}
		}
	});
}

function getIncomeChart(){
	var s = new Object();

	var start_date = $('#start-date2').datepicker('getDate');
	var end_date = $('#end-date2').datepicker('getDate');
	if (start_date.getTime() > end_date.getTime()){
		window.alert("Please choose a valid period");
		return;
	}
	s.start = start_date;
	s.end = end_date;
	$.ajax({
		url: '/incomeChart',
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(s),
		headers: createAuthorizationTokenHeader(),
		success: function(data){
				showIncomeChart(data);
		}
	});
}


function showIncomeChart(data){
	var div = $('#canvas2_div').empty();
	div.html('<canvas id="canvas2" height="300" style="background:white;"></canvas>');
	var ctx = $('#canvas2');
	var data1 = {
		labels: data.labels,
		datasets: [
			{
				label: "Restaurant incomes",
				fill: false,
				lineTension: 0.1,
				backgroundColor: "rgba(75,192,192,0.4)",
				borderColor: "rgba(75,192,192,1)",
				borderCapStyle: 'butt',
				borderDash: [],
				borderDashOffset: 0.0,
				borderJoinStyle: 'miter',
				pointBorderColor: "rgba(75,192,192,1)",
				pointBackgroundColor: "#fff",
				pointBorderWidth: 1,
				pointHoverRadius: 5,
				pointHoverBackgroundColor: "rgba(75,192,192,1)",
				pointHoverBorderColor: "rgba(220,220,220,1)",
				pointHoverBorderWidth: 2,
				pointRadius: 1,
				pointHitRadius: 10,
				data: data.data,
			}
		]
	};
	chart_2 = new Chart(ctx, {
		type: 'line',
		data: data1,
		options: {
			maintainAspectRatio: false,
			fullWidth: false,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true
					}
				}]
			}
		}
	});
	var total = 0;
	for (var i = 0; i < data.data.length; i++){
		total += data.data[i];
	}
	$('#total').val(total);
}


function getWaitersChart(){
	var s = new Object();

	var start_date = $('#start-date3').datepicker('getDate');
	var end_date = $('#end-date3').datepicker('getDate');
	if (start_date.getTime() > end_date.getTime()){
		window.alert("Please choose a valid period");
		return;
	}
	s.start = start_date;
	s.end = end_date;
	$.ajax({
		url: '/waitersChart',
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(s),
		headers: createAuthorizationTokenHeader(),
		success: function(data){
				showWaitersChart(data);
		}
	});
}

function showWaitersChart(data){
	var div = $('#canvas3_div').empty();
	div.html('<canvas id="canvas3" height="300" style="background:white;"></canvas>');
	var ctx3 = document.getElementById("canvas3");
	chart_3 = new Chart(ctx3, {
		type: 'bar',
		data: {
			labels: data.labels,
			datasets: [{
				data: data.data,
				backgroundColor: generateRandomColors(data.data.length),
				borderWidth: 1
			}]
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true
					}
				}]
			},
			maintainAspectRatio: false,
			title: {
				display: true,
				text: 'Incomes by waiters'
			},
			legend: {
				display: false
			}
		}
	});
}

function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function generateRandomColors(num){
	var colors = [];
	for (var i = 0; i < num; i++)
		colors.push(getRandomColor());
	return colors;
}
