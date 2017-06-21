function startApp() {
	// convert all a/href to a#href;
	$("body").delegate("a", "click", function(){
		var href = $(this).attr("href"); // modify the selector here to change the scope of intercpetion
		 // Push this URL "state" onto the history hash.
		if(typeof href === 'undefined')
			return;
		if(href.indexOf("collapse") !== -1){
			return true;
		}
		
		if(href != "menu"){
			$.bbq.pushState(href,2);
			// Prevent the default click behavior.
			return false;
		}
		return true;
	});
	
	$("#logoutButton").click(doLogout);

}

function showMyOrders(){
	$("#app-div").html("");	
	
	$("#app-div").load("showOrders.html #showOrder", function(){
		getMyOrder();
		$("#modals-div").load("showOrders.html #modals-div", function(){
			$("#changeInfo").click(function(){
				setInfo();
			});
			$("#changeInfoMeal").click(function(){
				setInfoMeal();
			});
		});
	});
	

}

function showDefineOrder(){
	$("#app-div").html("");
	$("#app-div").load("defineOrder.html #defineOrder", function(){
		setMeals();
		$("#modals-div").load("defineOrder.html #modals-div");
		
	});

}

function showRestaurants(){
	console.log("ja");
	$("#app-div").html("");
	$("#app-div").load("restaurant.html #choosenRestaurant", function(){
		getRestaurantsSelect();
	});
}

function showPersonalData(){
	$("#app-div").html("");
	$("#app-div").load("personalData.html #data", function(){
		displayData();	});
}

function showSeatingChart(){
	$("#app-div").html("");
	$("#modals-div").load("seatingChart.html #modals");
	$("#app-div").load("seatingChart.html #chart", function(){
		setupChart();
	});
	
}


function showCalendar(){
 	$("#app-div").html("");
 	$('#app-div').load('calendar.html #calendar', function (){
 		setupCalendarView();
 	});
 }
 

function showAddSysMan(){
	$("#app-div").html("");
	$('#app-div').load('addSysMan.html #sysMan-form');
}
function showConfirmEmail(){
	$('body').load('confirmRegByEmail.html #confirmByEmail');
	
}
function showFriends(){
	$('#app-div').load('FriendsView.html #friendsList',displayFriends());
	
}
function showaddFriend(){
	$('#app-div').load('addFriend.html #add-friend-form',function(){
		$('#myInput').keypress(function(e){
			if(e.which===13){
				e.preventDefault();
				getGuests(e.target.value);
			}
			console.log(e);
		});
	});
	
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
    		getUser();
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
	stompClient.disconnect();
    location.href="#";
    num_notifications = 0;
    $("body").load("login.html #login-div");
}

function createAuthorizationTokenHeader() {
    var token = getJwtToken();
    if (token) {
        return {"Authorization": token};
    } else {
        return {};
    }
}

function showSeatingChartWaiter(){
		$("#app-div").html("");
 		$("#app-div").load("seatingChartWaiter.html #chart", function(){
 			setupChartWaiter();
 		});
	
}



$(document).ready(function(){

$(window).bind( "hashchange", function(e) {
		var url = $.param.fragment();
		// url action mapping
		if(url == ""){
			if (getJwtToken()) {
				getUser();
		    }else{
		    	$("body").load("login.html #login-div");
		    }
			
		}
		else if(url == "supplies"){
			showSupplies();
		}
		else if(url == "bidding"){
			displayDemands();
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
			setupCalendar();
		}
		else if(/^addManager\?id\=[0-9]{1,}$/.test(url)){
			showAddManager();
		}
		else if(/^showSupply\?supplyId\=[0-9]{1,}$/.test(url)){
			showSelectedSupply();
		}
		else if(url=="ConfirmEmail"){
			showConfirmEmail();
		}
		else if(url=="friends"){
			showFriends();			
		}
		else if(url =="addFriend"){
			showaddFriend();
		}
		else if(url == "seatingChartWaiter"){
 			showSeatingChartWaiter();
 		}
 		else if(url == "calendar"){
 			showCalendar();
 		}
 		else if(url == "personalData"){
 			showPersonalData();
 		}
 		else if(url == "restaurantSelect"){
 			showRestaurants();
 		}
 		else if(url == "shifts"){
 			showShifts();
 		}
 		else if(url=="getMyOrders"){
 			showMyOrders();
 		}
 		else if(url=="defineOrder"){
 			showDefineOrder();
 		}else if(url="seeAllRestaurants"){
 			getRestaurants1();
 		}
		// add more routes
	});
	getUser();
});

function setupWebSockets(user){
	var socketClient = new SockJS("/sendNotification");
	stompClient = Stomp.over(socketClient);
	stompClient.connect({}, function(frame){
		stompClient.subscribe("/notify/" + user.email + "/receive", function(retVal){
			if (retVal != null && retVal.body != null && retVal.body != ""){
				
				var notification = JSON.parse(retVal.body);
				addNotification(notification);
				$("#brojZahteva").text(num_notifications);
			}
		});
	});
}