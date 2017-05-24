$(document).ready(function() {

	// convert all a/href to a#href
	$("body").delegate("a", "click", function(){
		var href = $(this).attr("href"); // modify the selector here to change the scope of intercpetion
		 // Push this URL "state" onto the history hash.
		if(href != "menu")
			$.bbq.pushState(href,2);

		// Prevent the default click behavior.
		return false;
	});
	
	$(window).bind( "hashchange", function(e) {
		var url = $.param.fragment();
		// url action mapping
		if(url == ""){
			showMainView();
		}
		else if(url == "addRestaurant"){
			//showUserList();
			showAddRestaurant();
		}
		else if(url == "changePersData"){
			showChangePersData();
		}
		else if(url == "changePass"){
			showChangePass();
		}
		else if(url == "addSysMan"){
			showAddSysMan();
		}
		else if(url == "addBidder"){
			showAddBidder();
		}
		else if(url == "addEmployee"){
			showAddEmployee();
		}
		else if(url == "seatingChart"){
			showSeatingChart();
		}
		else if(url == "calendarView"){
			showCalendarView();
		}
		else if(/^addManager\?id\=[0-9]{1,}$/.test(url)){
			showAddManager();
		}
		else if(url == "seatingChartWaiter"){
			showSeatingChartWaiter();
		}
		else if(url == "calendar"){
			showCalendar();
		}
		else if(url == "personalData"){
			console.log("usao");
			showPersonalData();
		}
		else if(url == "restaurantSelect"){
			showRestaurants();
		}
		// add more routes
	});
	
	
	if(window.location.hash==''){
		showMainView(); // home page, show the default view
	}else{
		showMainView();
		$(window).trigger( "hashchange" ); // user refreshed the browser, fire the appropriate function
	}
});

function showRestaurants(){
	console.log("ja");
	$("#app-div").html("");
	$("#app-div").load("restaurant.html #choosenRestaurant", function(){
		console.log("usap");
		getRestaurantsSelect();
	});
}

function showPersonalData(){
	$("#app-div").html("");
	$("#app-div").load("personalData.html #data", function(){
		displayData();
	});
}

function showSeatingChart(){
	$("#app-div").html("");
	$("#modals-div").load("seatingChart.html #modals");
	$("#app-div").load("seatingChart.html #chart", function(){
		setupChart();
	});
	
}
function showSeatingChartWaiter(){
	$("#app-div").html("");
	$("#app-div").load("seatingChartWaiter.html #chart", function(){
		setupChartWaiter();
	});
	
}

function showMainView(){
	$("#app-div").html("");
	getUser();
}

function showCalendarView(){
	$("#app-div").html("");
	$("#modals-div").load("calendarView.html #modals");
	$('#app-div').load('calendarView.html #calendar-div', function (){
		setupCalendar();
	});
}

function showCalendar(){
	$("#app-div").html("");
	$('#app-div').load('calendar.html #calendar1', function (){
		setupCalendarView();
	});
}

function showAddSysMan(){
	$("#app-div").html("");
	$('#app-div').load('addSysMan.html #sysMan-form');
}

function showAddBidder(){
	$("#app-div").html("");
	$('#app-div').load('addBidder.html #addBidder');
}

function showAddEmployee(){
	$("#app-div").html("");
	$('#app-div').load('addEmployee.html #addEmployee');
}

function showAddManager(){
	$("#app-div").html("");
	$('#app-div').load('addManager.html #addManager');
}

function showChangePass(){
	$("#app-div").html("");
	$('#app-div').load('changePass.html #changePassword');
}

function showChangePersData(){
	$("#app-div").html("");
	$('#app-div').load('changePersData.html #changePers', function(){
		changePersData();
	});
}

var showAddRestaurant = function(){
	$("#app-div").html("");
	$('#app-div').load('addRestaurant.html #add-restaurant-form');
};


// VARIABLES =============================================================
var TOKEN_KEY = "jwtToken";

// FUNCTIONS =============================================================
function getJwtToken() {
    return localStorage.getItem(TOKEN_KEY);
}

function setJwtToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
}

function removeJwtToken() {
    localStorage.removeItem(TOKEN_KEY);
}

function doLogin() {
	var $form = $("#login");
	var data = getFormData($form);
	if(!validateForm(data)){
		$("#login-error").text("Please enter your email and password").css("color","red");
		return;
	}
	
	var s = JSON.stringify(data);
    $.ajax({
        url: "/auth",
        type: "POST",
        data: s,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            setJwtToken(data.token);
            location.href = "indexSysMan.html";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
            	$("#login-error").text("Invalid username or password").css("color","red");
            } else {
                window.alert("an unexpected error occured: " + errorThrown);
            }
        }
    });
}

    function doLogout() {
        removeJwtToken();
        location.href="login.html";
    }

    function createAuthorizationTokenHeader() {
        var token = getJwtToken();
        if (token) {
            return {"Authorization": token};
        } else {
            return {};
        }
    }

    
    $("#logoutButton").click(doLogout);

    // INITIAL CALLS =============================================================
    if (getJwtToken()) {
        
    }