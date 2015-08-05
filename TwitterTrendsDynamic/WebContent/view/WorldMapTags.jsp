<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ page import="cmpe239.twitter.servlet.*" %>
	 <%@ page import="java.util.ArrayList, java.util.HashMap"  %>
	 <%@ page import="cmpe239.twitter.domain.*, org.apache.commons.lang3.StringEscapeUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Twitter Map</title>

	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=3&amp;sensor=false&libraries=visualization"></script> 
 	<script type="text/javascript" src="../resources/js/markerwithlabel.js"></script>
 	
 	<link href="style/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resources/style/coin-slider.css" />
<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<script type="text/javascript" src="../resources/js/cufon-yui.js"></script>
<script type="text/javascript" src="../resources/js/cufon-aller.js"></script>
<script type="text/javascript" src="../resources/js/script.js"></script>
<script type="text/javascript" src="../resources/js/coin-slider.min.js"></script>
<script>
	
	
	<% ArrayList<Tags> list = (ArrayList<Tags>)session.getAttribute("WordlMapTags");
		int datanum = list.size(); %>
	var mapdata = [
	<% for (int i=0; i<datanum-1;i++) {
		Tags t = list.get(i);
		int cluster = t.getCluster();
		String loc = t.getLocation();
		ArrayList<TagFrequency> tf = t.getTagsWithFreq();
		
		//out.println("['"+cluster+"', '"+t.getLatCenter()+", "+t.getLongCenter()+"'],");
		for(TagFrequency freq : tf) {
			String tag = StringEscapeUtils.escapeEcmaScript(freq.getTag());
			int frequency = freq.getFrequency();
			out.println("['"+cluster+"', '"+t.getLatCenter()+"', '"+t.getLongCenter()+"', '"+tag+"', '"+frequency+"', '"+loc+"'],");
		}
		}
		Tags t = list.get(datanum-1);
		int cluster = t.getCluster();
		ArrayList<TagFrequency> tf = t.getTagsWithFreq();
		
		//out.println("['"+cluster+"', '"+t.getLatCenter()+", "+t.getLongCenter()+"'],");
		for(TagFrequency freq : tf) {
			String tag = StringEscapeUtils.escapeEcmaScript(freq.getTag());
			int frequency = freq.getFrequency();
			out.println("['"+cluster+"', '"+t.getLatCenter()+"', '"+t.getLongCenter()+"', '"+tag+"', '"+frequency+"'],");
	}
	%>
	
	];
    var map, heatmap;
    var markers = [];
    var heatlocs = [];
    function initialize() {
      var mapOptions = {
        zoom: 2,
        center: new google.maps.LatLng(40.7127, -74.0059)
      };
      map = new google.maps.Map(document.getElementById('map-canvas'),
          mapOptions);
      var infowindow = new google.maps.InfoWindow();
 
      var i, marker, j;
      for (i = 0, j=1;i<mapdata.length;i++) {
      	marker = new MarkerWithLabel({
      		position: new google.maps.LatLng(mapdata[i][1], mapdata[i][2]),
      		map: map,
      		title: 'Cluster: '+mapdata[i][0]+'\n'+'Tag : '+mapdata[i][3]+'\n'+'Freq : '+mapdata[i][4]+'\n'+'Location : '+mapdata[i][5],
      		labelContent: mapdata[i][3],
            labelAnchor: new google.maps.Point(30, j*20),
            labelClass: "labels", // the CSS class for the label
            labelStyle: {opacity: 0.63}
      	});
      	if(j<=3) {
      	j = j+1;
      	}
      	else
      		{
      		j=1;
      		}
      	markers[i] = marker;
      	heatlocs[i] = new google.maps.LatLng(mapdata[i][1], mapdata[i][2]);
      	google.maps.event.addListener(marker, 'click', (function(marker, i) {
      		return function() {
      			var contentstr = '<div>'
      			+'<p>"Cluster" : "'+(mapdata[i][0])+'"</p>'
      			+'<p>"Tag Details" : "'+unescape(mapdata[i][3])+'"</p>'
      			+'</div>';
      			infowindow.setContent(contentstr);
      			infowindow.open(map,marker);
      		}
      	})(marker, i));
      }
      var pointArray = new google.maps.MVCArray(heatlocs);
      heatmap = new google.maps.visualization.HeatmapLayer({
    	    data: pointArray
    	  });
      heatmap.set('radius', 18);
    }
    
    function setAllMap(map) {
    	for (var i = 0; i < markers.length; i++) {
    		markers[i].setMap(map);
    	}
    }
    
    function changeMap(sel) {
    	if(sel.value=="0") {
    		heatmap.setMap(null);
    		setAllMap(map);
    	}
    	else{
    		setAllMap(null);
    		heatmap.setMap(map);
    	}
    }
    
    function changeKeyword(sel) {
    	setAllMap(null);
    	if(sel.value=="0")
    		setAllMap(map);
    	else{
        	for (var i = 0; i < markers.length; i++) {
        		if (sel.value == mapdata[i][4])
        			markers[i].setMap(map);
        	}
    	}
    }
    google.maps.event.addDomListener(window, 'load', initialize);
    
    </script>
<style>
/* .labels {color: blue;font-size: 10px;font-weight: normal;text-align: right;white-space: nowrap;} */
.labels {font-family:"Helvetica Neue",Helvetica,Arial,"sans-serif";position:absolute;z-index:997;padding:1px 4px;line-height:1.2em;margin:4px 7px;-webkit-transform:translate3d(0px,0px,0px);}
.labels {font-size:12px;opacity:.3;z-index:990;}
.labels {color:#8F8;}.word_category.word_media{font-weight:normal;font-style:italic;}
.labels {font-weight:normal;font-style:italic;}
.labels {background-color:#000;color:#fff;-webkit-border-radius:3px;-moz-border-radius:3px;border-radius:3px;border:1px solid #000;}.{color:#fff;background-color:#131A2B;background:-webkit-gradient(linear,left top,left bottom,from(#131A2B),to(#4e5763));-webkit-box-shadow:0 .2em .3em rgba(0,0,0,0.75);-moz-box-shadow:0 .2em .3em rgba(0,0,0,0.75);border:1px solid #000;text-shadow:#000 0 1px 2px;}.
{z-index:999!important;cursor:pointer;opacity:1.0!important;color:#fff!important;background-color:#9ba6b7;background:-webkit-gradient(linear,left top,left bottom,from(#9aa6b8),to(#6b7a91),color-stop(0.5,#818fa4),color-stop(0.5,#78869c));text-shadow:#1d2a40 0 1px 0;border:1px solid #3f4a5c;font-weight:bold;-webkit-box-shadow:0 .2em .3em rgba(0,0,0,0.75);-moz-box-shadow:0 .2em .3em rgba(0,0,0,0.75);}
/*.label{padding:0 3px 0 19px;white-space:nowrap;display:inline-block;vertical-align:top;font-size:11px;line-height:18px;zoom:1;}*/

#header {
	background-color: black;
	color: white;
	text-align: center;
	padding: 5px;
}
#nav {
	line-height: 30px;
	background-color: #FFFFFF;
	height: 560px;
	width: 190px;
	float: left;
	padding: 5px;
}
#section {
	width: 350px;
	float: left;
	padding: 0px;
}
#footer {
	background-color: black;
	color: white;
	clear: both;
	text-align: center;
	padding: 5px;
}

#nav li{
    float:left;
    margin-right:10px;
    position:relative;
}

#nav a{
    display:block;
    padding:5px;
    color:#fff;
    background:#333;
    text-decoration:none;
}

#nav a:hover{
    color:#fff;
    background:#6b0c36;
    text-decoration:underline;
}

// test
/*--- DROPDOWN ---*/

//test

</style>
</head>
<body background="../resources/images/bg.jpg">
	<div id="header">
		<h1>Twitter Trends Map</h1>
                <a href="WorldMapTags">Home</a> &nbsp | &nbsp
            
                <a href="About.jsp">About</a> &nbsp | &nbsp
                
                <a href="WordCloudCsv.jsp">LDA</a> &nbsp | &nbsp
                
                <a href="TopLocations">Top Locations</a>
	</div>
	
	<div id="nav">
		<a>Top Trends</a><br>
		<br>
		<a>Location:</a><br>
		<div style="text-align:left">
		<select id="locationSelect"">
			<% ArrayList<String> locations = (ArrayList<String>)session.getAttribute("LocationForLatsLongs");
			int locSize = locations.size(); %>
			<% for(int j=0; j<locSize; j++) {%>
			<option value=j><%=locations.get(j)%></option>
			<%} %>
		</select>
		
		
		<button id="search" type="button" class="btn btn-primary" onclick="doNav()">Go</button>
		
		</div>
		<br>
		<a>Map Layer:</a><br>
		<select id="heatmapSelect" onchange="changeMap(this)">
			<option value="0">normal</option>
			<option value="1">heat map</option>
		</select>
		
	</div>
	<div id="section">
		<div id="map-canvas" style="width:1100px;height:560px;"></div>
	</div>
	<div id = "footer">	
      <ul class="list-inline center-block">
        <li><a href="http://facebook.com"><img src="../resources/images/soc_fb.png"></a></li>
        <li><a href="https://twitter.com/TrendsMap239"><img src="../resources/images/soc_tw.png"></a></li>       
      </ul> 	
	</div>
	<script type="text/javascript">
	function doNav()
	{
		var loc=document.getElementById("locationSelect");
		var location = loc.options[loc.selectedIndex].text;
		document.location.href="WordCloud?loc="+location;
		}
	</script>
	
</body>
</html>