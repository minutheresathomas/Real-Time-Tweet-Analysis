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
 	<!-- <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=visualization"></script> -->
<script>

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
background-color: #eeeeee;
height: 500px;
width: 150px;
float: left;
padding: 5px;
}
#section {
width: 1000px;
float: left;
padding: 10px;
}
#footer {
background-color: black;
color: white;
clear: both;
text-align: center;
padding: 5px;
}
#body {
background-color: white;
color: white;
text-align: center;
padding: 5px;
}

</style>
</head>
<body background="../resources/images/bg6.jpg">
<div id="header">
		<h1>Twitter Map</h1>
		
                <a href="WorldMapTags">Home</a> &nbsp | &nbsp
            
                <a href="About.jsp">About</a> &nbsp | &nbsp
            
                <a href="WordCloudCsv.jsp">LDA</a> &nbsp | &nbsp
                
                <a href="TopLocations">Top Locations</a> 
           
	</div>
<div id="section">
<p align="center"><b><h3 align="center">We Put you on top of what is happening!!</h3></b><p><br><br>

<h4 align="center">Twitter Trends Map displays you latest trends from Twitter around the world.<br>
It provides you the popular hash tags in a location and visualize trends across any location on map.<br>
You can also visualize top trends in a region using our stunning word Cloud feature.</h4>
<br><br><br>
<h4 align="center">Features that we provide are:<br><br>
			<li>
              Be alert about emerging trends and news before it breaks
            </li>
            <li>
             Broadcasters and publishers can use our tool for Breaking news, Sports coverage, <br>
    Local news, Major event coverage, Industry commentators, Internal analysis
            </li>
            <li>
             Word Cloud displaying top hashtags in a region.
            </li>
	</h4>

</div>	
<div id = "footer">	
      <ul class="list-inline center-block">
        <li><a href="http://facebook.com"><img src="../resources/images/soc_fb.png"></a></li>
        <li><a href="https://twitter.com/TrendsMap239"><img src="../resources/images/soc_tw.png"></a></li>       
      </ul> 	
	</div>
</body>
</html>