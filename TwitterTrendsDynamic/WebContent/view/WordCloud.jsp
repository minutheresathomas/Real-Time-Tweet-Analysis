<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ page import="cmpe239.twitter.servlet.*" %>
	 <%@ page import="java.util.ArrayList, java.util.HashMap, java.util.Map" %>
	 <%@ page import="cmpe239.twitter.domain.*, org.apache.commons.lang3.StringEscapeUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <title>Topic Clouds</title> 
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script src="http://d3js.org/d3.v2.min.js?2.10.0"></script>
    <script type="text/javascript" src="../resources/js/d3.layout.cloud.js"></script>

<link href="style/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resources/style/coin-slider.css" />
<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<style>

#header {
	background-color: black;
	color: white;
	text-align: center;
	padding: 5px;
}
#footer {
	background-color: black;
	color: white;
	clear: both;
	text-align: center;
	padding: 5px;
}
#section {
	width: 350px;
	float: left;
	padding: 0px;
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
</style>
</head>

<body background="../resources/images/bg6.jpg">

<%String loc = (String)request.getAttribute("location"); %>

<div id="header">
		<h1>Twitter Trends in <%=loc%></h1>
		
                <a href="WorldMapTags">Home</a> &nbsp | &nbsp
            
                <a href="About.jsp">About</a> &nbsp | &nbsp
            
                <a href="WordCloudCsv.jsp">LDA</a> &nbsp | &nbsp
                
                <a href="TopLocations">Top Locations</a>
           
	</div>
	
	<div id="nav">
		
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
		
		
	</div>
	
<script>

//wordScale=d3.scale.linear().domain([1,100,1000,10000]).range([10,20,40,80]).clamp(true);
wordScale=d3.scale.linear().domain([1,5,20,40]).range([10,20,40,80]).clamp(true);
//wordColor=d3.scale.linear().domain([10,20,40,80]).range(["blue","green","orange","red"]);
wordColor=d3.scale.linear().domain([10,20,40,80]).range(["blue","green","orange","red"]);

<% HashMap<Integer, String> map = (HashMap<Integer, String>)session.getAttribute("WordCloudTagSupport");

int datanum = map.size();
%>

var len = <%=datanum%>
var count = 0;
for (x = 0; x < len; x++) {
	viz = d3.select("body").append("svg")
    .attr("width", 400)
    .attr("height", 440)
    .attr("id", "svg" + x);
}

<%for (Map.Entry<Integer, String> entry : map.entrySet()) {
    int key = entry.getKey();
    String value1 = entry.getValue();
    %>
    var mydata = <%=value1 %>
    var x= <%=key%>
    
  d3.layout.cloud().size([400, 400])
      .words(mydata)
      .rotate(function() { return ~~(Math.random() * 2) * 5; })
      .fontSize(function(d) { return wordScale(d.size); })
      .on("end", draw)
      .start();

  function draw(words) {
	  
	viz = d3.select("#svg" + count);
   // viz = d3.select("#svg" + topic[0].topic);
        
      viz.append("g")
        .attr("transform", "translate(200,220)")
      	.selectAll("text")
        .data(words)
      	.enter().append("text")
        .style("font-size", function(d) { return d.size + "px"; })
        .style("fill", function(d) { return wordColor(d.size); })
        .style("opacity", .75)
        .attr("text-anchor", "middle")
        .attr("transform", function(d) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.text; });
    
    //  .data([topic[0]])
		viz
        .append("text")
        .data([mydata[x]])
        .style("font-size", 20)
        .style("font-weight", 900)
        .attr("x", 100)
        .attr("y", 20)
        .text(function(d) { return "" })

  //d3.select("#svg"+x).append("svg:text").text("Cluster " + x);	
 //   viz.enter().append("svg:text").text("Topic " + x);
	
  }
	count++;
//})
<% } %>
</script>
<script type="text/javascript">
	function doNav()
	{
		var loc=document.getElementById("locationSelect");
		var location = loc.options[loc.selectedIndex].text;
		document.location.href="WordCloud?loc="+location;
		}
	</script>
<div id = "footer">	
      <ul class="list-inline center-block">
        <li><a href="http://facebook.com"><img src="../resources/images/soc_fb.png"></a></li>
        <li><a href="https://twitter.com/TrendsMap239"><img src="../resources/images/soc_tw.png"></a></li>       
      </ul> 	
	</div>
</body>
</html>