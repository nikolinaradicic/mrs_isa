var tables = [];
function setupChart(){
	$("#addSegmentButton").click(function(){
		$("#modalSegment").modal('toggle');
	});
	
	$("#add-table-button").click(function(){
		addTableToCanvas();
	});
	
	$('#new-table-button').click(function(){
		$("#add-table-modal").modal('toggle');
	});
	
	$('#save').click(function(){
		saveToCanvas();
		
	});
	
	$('#delete-table').click(function(){
		deleteTable();
		
	});

	$('#change-table').click(function(){
		changeTable();
		
	});
	
	var canvas = new fabric.CanvasEx("canvas");
	document.getElementById('canvas').fabric = canvas;
	canvas.setHeight(450);
	canvas.setWidth(800);
	
	canvas.on('mouse:dblclick', function(e) {
		  var activeObject = e.target;
		  if(activeObject != null){
			  if(activeObject.get('name')!=null){
				  var tableId = activeObject.get('name');
				  $('#table-name').val(tableId);
				  $('#change-table-modal').modal();
				  getTable(tableId);
				}
		  }
	});
	
	fillSegmentBox();
}

function setupChartWaiter(){
	var canvas = new fabric.CanvasEx("canvas");
	document.getElementById('canvas').fabric = canvas;
	canvas.setHeight(450);
	canvas.setWidth(800);
	canvas.selection = false;

	fillSegmentBox();
}


function displaySegment(){
	console.log("promjena selecta");
	var x = document.getElementById("segmentSelect").value;
	$.ajax({
		url: "/getSegment",
		type:"POST",
		data: x,
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){
				changeCanvas(data.responseJSON);
			}
		}
	});
    
}

function changeTable(){
	var $form = $("#change-table-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#change-table-error").text("Please enter chair number").css("color","red");
		return;
	}
	$("#change-table-modal").modal('toggle');
	
	var tableId =  data['table-name'];
	for (var i = 0; i < tables.length; i++) {
		if(tables[i].name===tableId){
			tables[i]['chairNum'] = data['chairNum'];
			return;
		}
	}
	
	var table = new Object();
	table['name'] = tableId;
	table['chairNum'] = data['chairNum'];
	table['operation'] = "update";
	tables.push(table);
	
}

function deleteTable(){
	var $form = $("#change-table-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#change-table-error").text("Please enter chair number").css("color","red");
		return;
	}
	$("#change-table-modal").modal('toggle');
	
	var canvas = document.getElementById('canvas').fabric;
	var tableId =  data['table-name'];
	for (var i = 0; i < tables.length; i++) {
		if(tables[i].name===tableId){
			tables.splice(i, 1);
			canvas.getActiveObject().remove();
			return;
		}
	}
	var table = new Object();
	table['name'] = tableId;
	table['operation'] = "delete";
	tables.push(table);
	canvas.getActiveObject().remove();
}

function addTableToCanvas(){
	console.log("usaooo");
	var $form = $("#add-table-form");
	var data = getFormData($form);
	if (!validateForm(data)){
		$("#add-table-error").text("Please enter chair number").css("color","red");
		return;
	}
	$("#add-table-modal").modal('toggle');
	
	var canvas = document.getElementById('canvas').fabric;
	
	var img = document.createElement('img');
	img.src = 'img/blue.png';
	
	var name = Date.now().toString();
	var imgInstance = new fabric.NamedImage(img, {
	  left: 100,
	  top: 100,
	  height: 50,
	  width: 100,
	  opacity: 0.85,
	  name: name
	});
	
	canvas.add(imgInstance);
	setTimeout(function(){
		canvas.renderAll(); 
	}, 100);
	
	var table = new Object();
	table['name'] = name;
	table['chairNum'] = data['chairNum'];
	table['operation'] = "new";
	tables.push(table);
}


function showCanvas(segment){
		var canvas = document.getElementById('canvas').fabric;
		if (segment.chart != ""  && segment.chart != null){
			var json = JSON.parse(segment.chart);
			canvas.loadFromJSON(json, canvas.renderAll.bind(canvas));
			setTimeout(function(){
				canvas.forEachObject(function(o) {
					console.log("usao");
  					o.selectable = false;
  					o.lockMovementX=true;
  					o.lockMovementY=true;
  					o.hasControls = false;
				});
			},1000);
		}
	
}

function getTable(tableId){
	for (var i = 0; i < tables.length; i++) {
		if(tables[i].name===tableId){
			$('#chairNum').val(tables[i].chairNum);
			return;
		}
	}

	var x = document.getElementById("segmentSelect").value;
	var data = {name : tableId};
	$.ajax({
		url: "/getTable/" + x,
		type:"POST",
		data: JSON.stringify(data),
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			console.log(data);
			if (data.responseJSON){
				console.log(data.responseJSON);
				$('#chairNum').val(data.responseJSON.chairNumber);
			}
		}
	});

}


function changeCanvas(segment){
	var canvas = document.getElementById('canvas').fabric;
	canvas.clear();
	if (segment.chart != "" && segment.chart != null){
		var json = JSON.parse(segment.chart);
		canvas.loadFromJSON(json, canvas.renderAll.bind(canvas));
		canvas.forEachObject(function(o) {
			console.log("usao");
  			o.selectable = false;
  			o.lockMovementX=true;
  			o.lockMovementY=true;
		});
	}

}


function saveTables(){
	var x = document.getElementById("segmentSelect").value;
	$.ajax({
		type : "POST",
		url: '/saveTables/'+x,
		dataType: 'json',
		contentType : "application/json",
		data : JSON.stringify(tables),
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			tables = [];
			window.alert("Your configuration has been succesfully saved");
		}
	});
}

function saveToCanvas() {
	var canvas = document.getElementById('canvas').fabric;
	var x = document.getElementById("segmentSelect").value;
	$.ajax({
		type : "POST",
		url: '/saveChart/'+x,
		dataType: 'text',
		contentType : "application/text",
		data : JSON.stringify(canvas),
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			saveTables();
		}
	});

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
		contentType: "application/json",
		headers: createAuthorizationTokenHeader(),
		success: function(data){
			$("#modalSegment").modal('toggle');
			$('#segmentSelect').prepend($('<option>', { 
		        value: data.name,
		        text : data.name 
		    }));
			$("#segmentSelect").val(data.name);
			$("#segmentSelect").change();
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR 5: " + errorThrown);
		}
	});
	
}

function fillSegmentBox(){
	$.ajax({
		url: "api/getUser",
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		headers: createAuthorizationTokenHeader(),
		complete: function(data) {
			if (data.responseJSON){	
				console.log(data.responseJSON);
				if (data.responseJSON.restaurant.segments.length != 0){
					showCanvas(data.responseJSON.restaurant.segments[0]);
					$.each(data.responseJSON.restaurant.segments, function (i, item) {
					    $('#segmentSelect').append($('<option>', { 
					        value: item.name,
					        text : item.name 
					    }));
					});
				}
			}
				
		}
	});
}

