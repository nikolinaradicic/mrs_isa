var stolovi = [];
$(document).ready(function() {
	$("#addSegmentButton").click(function(){
		$("#modalSegment").modal('toggle');
	});
	
	displayForPersData(fillSegmentBox);
});


$(document).on('click', '#new-table-button', function(){
	addSquareTableToCanvas();
	
});

function displaySegment(){
	var x = document.getElementById("segmentSelect").value;
	$.ajax({
		url: "/getSegment",
		type:"POST",
		data: x,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if (data.responseJSON){
				console.log("segment");
				console.log(data.responseJSON);
				changeCanvas(data.responseJSON);
			}
		}
	});
    
}


function addSquareTableToCanvas(){
	
	var canvas = document.getElementById('canvas').fabric;
	
	var img = document.createElement('img');
	img.src = 'img/blue.png';
	
	//var nameSquare = randomString(32, '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ');
	
	var imgInstance = new fabric.NamedImage(img, {
	  left: 100,
	  top: 100,
	  height: 50,
	  width: 100,
	  opacity: 0.85,
	  name: "bla"
	});
	canvas.add(imgInstance);
	setTimeout(function(){
		canvas.renderAll(); 
	}, 50);
}


function showCanvas(segment){
		var canvas = new fabric.CanvasEx("canvas");
		if (segment.canvas != ""){
			console.log("prikazujem");
			var json = JSON.parse(segment.chart);
			canvas.loadFromJSON(json);
			console.log(canvas);
		}
		
		document.getElementById('canvas').fabric = canvas;
		canvas.setHeight(450);
		canvas.setWidth(800);
		setTimeout(function(){
			canvas.renderAll(); 
		}, 50);
	
}


function changeCanvas(segment){
	var canvas = document.getElementById('canvas').fabric;
	canvas.clear();
	if (segment.canvas != ""){
		console.log("prikazujem");
		var json = JSON.parse(segment.chart);
		canvas.loadFromJSON(json);
		console.log(canvas);
	}
	canvas.setHeight(450);
	canvas.setWidth(800);
	setTimeout(function(){
		canvas.renderAll(); 
	}, 50);

}

$(document).on('click', '#save', function(){
	saveToCanvas();
	
});

function saveToCanvas() {
	var canvas = document.getElementById('canvas').fabric;
	var x = document.getElementById("segmentSelect").value;
	//var segment = {name: x, chart: canvas};
	//console.log(segment);
	$.ajax({
		type : "POST",
		url: '/saveChart/'+x,
		dataType: 'text',
		contentType : "application/text",
		data : JSON.stringify(canvas),
		complete: function(data) {
			console.log(data);
		}
	});

}



$(document).on('click', '#stoAdd', function(){
	saveTableConfiguration();
	
});




	
//FUNKCIJA KOJA GENERISE RANDOM ID STOLA
function randomString(length, chars) {
	var result = '';
	for (var i = length; i > 0; --i) result += chars[Math.round(Math.random() * (chars.length - 1))];
		return result;
}



fabric.NamedImage = fabric.util.createClass(fabric.Image, {

	  type: 'named-image',

	  initialize: function(element, options) {
		this.callSuper('initialize', element, options);
		options && this.set('name', options.name);
	  },

	  toObject: function() {
		return fabric.util.object.extend(this.callSuper('toObject'), { name: this.name });
	  }
});fabric.NamedImage.async = true;


fabric.NamedImage.fromObject = function(object, callback) {
	  fabric.util.loadImage(object.src, function(img) {
		callback && callback(new fabric.NamedImage(img, object));
	  });
};





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
	$.each(restaurant.segments, function (i, item) {
	    $('#segmentSelect').append($('<option>', { 
	        value: item.name,
	        text : item.name 
	    }));
	});
	if (restaurant.segments != []){
		showCanvas(restaurant.segments[0]);
	}
}

