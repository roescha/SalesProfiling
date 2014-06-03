<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="js/jquery.corner.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
      #panel {
        position: absolute;
        height: 35px;
        color: green;
        top: 5px;
        left: 50%;
        margin-left: -300px;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 3px solid black;
      }
      .popup {
		padding: 5px;
      	width: 200px;
      	height:80px;
      }
      
     .wrTitle {
     	font-size:22px;
		color: #79ac36;
		text-align: center;
		font-weight: bold;
	}
    </style>
<title>Availability heat map</title>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=visualization"></script>
<script>
var map, pointarray, heatmap;
var heatMapData=[];

var offSalesRaw = '${offSales}';
var fromDate = '${fromDate}';
var toDate = '${toDate}';
var displayRDC = ${displayRDC};
var offSales = JSON.parse(offSalesRaw); 
var markers = false;
var marker_array = [];
$.each($(offSales), function (key, value) {
	var point = new google.maps.LatLng(value.latitude, value.longitude);
	heatMapData.push({location:point, weight:Number(value.noOffsales)});
	
});

function toggleMarkers() {

	if (marker_array.length == 0) { //Create and display the markers
		$.each($(offSales), function (key, value) {
			var point = new google.maps.LatLng(value.latitude, value.longitude);
			var marker = new google.maps.Marker({position: point});
			marker_array.push(marker);
			bindInfoWindow(marker, map, new google.maps.InfoWindow, createHtml(value), value.id);
		});
	
		markers = true;
	} else { //Toggle visibility of the existing markers
		var mapVal = null;
		if (!markers) {
			mapVal = map;
			markers = true;
		} else {
			markers = false;
		}
			
		for (var i = 0; i < marker_array.length; i++) {
            marker_array[i].setMap(mapVal);
		}
	}
}

function createHtml(value) {
	var html = "<div id= 'pu_" + value.id + "' class='popup'><span class='wrTitle'>" + value.name + "</span> <br/> (" + value.noOffsales + " items off sale)<br />";
	
	if (!displayRDC) {
		html += "<a align='right' href='Javascript:void();' onclick='showDetail(" + value.id + ")'>View detail</a>";
	}
	
	html += "</div>";
	
	return html;
}

function showDetail(storeId) {
	window.location="tableView?storeId=" + storeId + "&fromDate=" + fromDate + "&toDate=" + toDate;
}

function initialize() {
  var mapOptions = {
    zoom: 6,
    center: new google.maps.LatLng(55.566856, -2.000022),
    mapTypeId: google.maps.MapTypeId.SATELLITE
  };

  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  var pointArray = new google.maps.MVCArray(heatMapData);

  heatmap = new google.maps.visualization.HeatmapLayer({
    data: pointArray  });
  
  heatmap.setMap(map);
  heatmap.set('radius', 25);
}
function bindInfoWindow(marker, map, infoWindow, html) {
	marker.setMap(map);
    google.maps.event.addListener(marker, 'click', function() {
      infoWindow.setContent(html);
      infoWindow.open(map, marker);
    });
  }

google.maps.event.addDomListener(window, 'load', initialize);

$('#panel').corner();
    </script>
</head>
<body>
    <div id="panel">
      <span class="wrTitle">Waitrose store off sales ${fromDate}to ${toDate}</span> (<a href="JavaScript:void(0);" onclick="toggleMarkers();">Click to show or disable markers</a>)
    </div>
    <div id="map-canvas"></div>
  </body>
</html>