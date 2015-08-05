<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ page import="cmpe239.twitter.servlet.*" %>
	 <%@ page import="java.util.ArrayList, java.util.HashMap"  %>
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
#nav {
	line-height: 30px;
	background-color: #eeeeee;
	height: 500px;
	width: 150px;
	float: left;
	padding: 5px;
}
#section {
	width: 350px;
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
</style>
    
</head>

<body background="../resources/images/bg6.jpg">
<%String loc = (String)request.getAttribute("location"); %>

<div id="header">
		<h1>Twitter Trends based on LDA for cluster 0</h1>
		
                <a href="WorldMapTags">Home</a> &nbsp | &nbsp
                <a href="About.jsp">About</a> &nbsp | &nbsp
                <a href="WordCloudCsv.jsp">LDA</a> &nbsp | &nbsp
                <a href="TopLocations">Top Locations</a>
        </div>

<script>

//wordScale=d3.scale.linear().domain([1,100,1000,10000]).range([10,20,40,80]).clamp(true);
wordScale=d3.scale.linear().domain([1,5,20,40]).range([20,40,80,160]).clamp(true);
//wordColor=d3.scale.linear().domain([10,20,40,80]).range(["blue","green","orange","red"]);
wordColor=d3.scale.linear().domain([20,40,80,160]).range(["blue","green","orange","red"]);

for (x = 0; x < 3; x++) {
	viz = d3.select("body").append("svg")
    .attr("width", 400)
    .attr("height", 440)
    .attr("id", "svg" + x);
}
for (x = 0; x < 3; x++) {
//d3.json("topic.json", function(topic) {
d3.csv("../resources/csv/topic_words"+x+".csv", function(topic) {
	
  d3.layout.cloud().size([400, 400])
//      .words([{"text":"test","size":wordScale(.01)},{"text":"bad","size":wordScale(1)}])
      .words(topic)
      .rotate(function() { return ~~(Math.random() * 2) * 5; })
      .fontSize(function(d) { return wordScale(d.size); })
      .on("end", draw)
      .start();

  function draw(words) {
	  
	viz = d3.select("#svg" + topic[x].topic);
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
        .data([topic[x]])
        .style("font-size", 20)
        .style("font-weight", 900)
        .attr("x", 100)
        .attr("y", 20)
        .text(function(d) { return "TOPIC " + d.topic; })

//  d3.select("#svg"+x).append("svg:text").text("Topic " + x);	
 //   viz.enter().append("svg:text").text("Topic " + x);

  }

})
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