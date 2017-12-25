<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>project SH</title>
    <link href="/SH/css/style.css" rel="stylesheet" type="text/css">
<script  type="text/javascript" src="/SH/js/jquery.min.js"></script>
<script  type="text/javascript" src="/SH/js/canvasjs.min.js"></script>
<script>
var refershOn=true;
var sortBy='lp1';
var sortType='ASC';
var myVar;
function refereshTable(){
	
	if(refershOn){
	 jQuery.ajax({
	    	type: "POST",
	    	url: 'tablecreator/viewlistlp1.html',
	    	dataType: "json",
	    	data: { sortBy: sortBy, sortType:sortType},  
	    	error: function(jqXHR, textStatus, errorThrown){  
		          alert('Error');
		      },
  	 success: function(json) {
		var numericCode='';
		var table='<table><tr><th>NCode</th><th>Open</th><th>current</th><th>total change</th><th>lp1</th><th>lp2</th><th>lp3</th><th>lp4</th><th>Buy</th><th>BE</th><th>G</th></tr>';
		for(var i in json)
		{
			table=table+'<tr>';
			table=table+'<td><a href="http://www.bseindia.com/stock-share-price/wef/dc/'+json[i].numericCode+'/" target="_blank">'+ json[i].numericCode+'</a></td>';
			table=table+'<td>'+ json[i].openValue+'</td>';
			table=table+'<td>'+ json[i].l5+'</td>';
			table=table+'<td>'+ json[i].totalChange+'</td>';
			table=table+'<td>'+ json[i].lp1+'</td>';
			table=table+'<td>'+ json[i].lp2+'</td>';
			table=table+'<td>'+ json[i].lp3+'</td>';
			table=table+'<td>'+ json[i].lp4+'</td>';
			table=table+'<td><a href="http://moneybhai.moneycontrol.com/neworderview/stocks" target="_blank"> Buy</a></td>';
			table=table+'<td><a href="/SH/buy/init.html?numericCode='+json[i].numericCode+'" target="_blank"> BE</a></td>';
			table=table+'<td onclick="showGraph(this,'+json[i].numericCode+',\'left\')"><a href="javascript:void(0);">G</a></td>';
		     table=table+'</tr>';	     
		}
		table=table+'</table>';
		jQuery("#tableSort").html(table);
  	 }
		      
	
});}
	 
	 if(refershOn){
			 myVar=setTimeout(refereshTable, 30000);
		}


}
function loadOnreadyFunction(){
	refereshTable();
	showBoughtTable();
}
jQuery(document).ready(loadOnreadyFunction);

function showBoughtTable(){
	jQuery.ajax({
    	type: "POST",
    	url: 'buy/showBuyTable.html',
    	dataType: "json",
    	data: { sortType: sortBy},  
    	error: function(jqXHR, textStatus, errorThrown){  
	          alert('Error');
	      },
	 success: function(json) {
	var numericCode='';
	var table='<table><tr><th>G</th><th>NCode</th><th>Stock</th><th>Buy Value</th><th>Quantity/Total</th><th>p1</th><th>p2</th><th>p3</th><th>p4</th><th>p5</th><th>SE</th><th>Sale</th></tr>';
	
	for(var i in json)
	{
		table=table+'<tr>';
		table=table+'<td onclick="showGraph(this,'+json[i].numericCode+',\'right\')"><a href="javascript:void(0);">G</a></td>';
		table=table+'<td><a href="http://www.bseindia.com/stock-share-price/wef/dc/'+json[i].numericCode+'/" target="_blank">'+ json[i].numericCode+'</a></td>';
		table=table+'<td>'+ json[i].stock+'</td>';
		table=table+'<td>'+ json[i].price+'</td>';
		table=table+'<td>'+ json[i].quantity+'/'+ json[i].totalAmount+'</td>';
		
		var priceArray=json[i].percentList;
		
		jQuery.each(priceArray, function() {
			  if(this<0.21){
				  //red color
				  table=table+'<td class="redStyle">'+this+'</td>';
			  }else if (this>=0.21 & this <0.31) {
				  table=table+'<td class="yellowStyle">'+this+'</td>';
				//yellow color
				}else{
					//green color
					table=table+'<td class="greenStyle">'+this+'</td>';
				}
			});
		table=table+'<td><a href="/SH//buy/initSale.html?numericCode='+json[i].numericCode+'" target="_blank">SE</a>';
		table=table+'<td><a href="http://moneybhai.moneycontrol.com/neworderview/stocks" target="_blank">Sale</a>';
		 table=table+'</tr>';	     
	}
	table=table+'</table>';
	
	jQuery("#boughtDiv").html(table);
	 }
	      
	      
});
	setTimeout(showBoughtTable, 30000);
}

function toggleReferesh(){
	
	if(refershOn){
		 clearTimeout(myVar);
		refershOn=false;
		jQuery("#refershButton").html("Start");
	}else{
		refershOn=true;
		refereshTable();
		jQuery("#refershButton").html("Stop");
	}
}
function changeSort(){
	
	sortBy=$("#sortBySel").val();
}
function changeSortType(){
	sortType=$("#sortByType").val();
}

function showGraph(obj, numericCode, pos){
	//alert(numericCode);
	//alert(obj.offsetLeft+','+ obj.offsetTop);
	$("#graphhDiv").show();
	if(pos=='right'){
		$("#graphhDiv").css('top',getOffset(obj).top);
		$("#graphhDiv").css('left',getOffset(obj).left-445);
		$("#graphArrow").css('float','right');
		$("#graphCross").css('float','left');
		
		
	}else{
		$("#graphhDiv").css('top',getOffset(obj).top);
		$("#graphhDiv").css('left',getOffset(obj).left+20);
		$("#graphArrow").css('float','left');
		$("#graphCross").css('float','right');
			
	}
	$("#chartContainer").html('');
	
	jQuery.ajax({
    	type: "POST",
    	url: 'tablecreator/getGraphData.html',
    	dataType: "json",
    	data: { numericCode: numericCode},  
    	error: function(jqXHR, textStatus, errorThrown){  
	          alert('Error');
	      },
	 success: function(json) {
		 var minimum=json[0].y;
		 var maximum=json[0].y;
		 for(var i in json)
			{
			 if(json[i].y<minimum){
				minimum= json[i].y;
			 }
			 if(json[i].y>maximum){
				 maximum=json[i].y;
			 }
			}
		 var chart = new CanvasJS.Chart("chartContainer",
				    {
				      title:{
				       text: numericCode   
				     },
				     theme: "theme3",
				     animationEnabled: true,
				     axisY:{
				    	 minimum:minimum*0.95,
				    	 maximum:maximum*1.05
				     },
				     data: [
				     {        
				      type: "line",
				      showInLegend: true,
				      xValueType: "dateTime",
				      legendText: "Share",
				      dataPoints: json
				    }
				    ]
				  });

				chart.render();
		 
	 }
	      
	      
});
	jQuery.ajax({
    	type: "POST",
    	url: 'tablecreator/getCandleChartData.html',
    	dataType: "json",
    	data: { numericCode: numericCode},  
    	error: function(jqXHR, textStatus, errorThrown){  
	          alert('Error');
	      },
	 success: function(json) {
		 //alert(json);
		 var graphJson=new Array(json.length);
		 
		 
		 for(var i in json)
			{
			 var dt=new Date(json[i].x);
			 graphJson[i]={x:dt, y:json[i].y};
			}
		 
		 var candleChart = new CanvasJS.Chart("candleChartContainer",
				    {
				      title:{
				       text: "Candle Stick chart"   
				     },
				     backgroundColor: "#F5DEB3",
				     zoomEnabled: true,
						axisY: {
							includeZero:false,
							title: "Prices",
							prefix: "Rs "
						},
						axisX: {
							interval:5,
							intervalType: "day",
							valueFormatString: "DD-MMM-YY",
							labelAngle: -45
							
						},
				     data: [
				     {        

							type: "candlestick",
							risingColor: 'green',
							color:'red',
							dataPoints: graphJson
						}
				    ]
				  });

		 candleChart.render();
		 
	 }
	      
	      
});
	
}
function getOffset( el ) {
    var _x = 0;
    var _y = 0;
    while( el && !isNaN( el.offsetLeft ) && !isNaN( el.offsetTop ) ) {
        _x += el.offsetLeft - el.scrollLeft;
        _y += el.offsetTop - el.scrollTop;
        el = el.offsetParent;
    }
    return { top: _y+document.body.scrollTop, left: _x };
}
function closeGraph(){
	$("#graphhDiv").hide();
	//document.getElementById('graphhDiv').style.display='none';
}

</script>
</head>
<body>
<div style="width: 100%; float:left;"><tiles:insert name="header"/></div>
<div style="width: 100%;float:left;"><tiles:insert name="cpanel"/></div>
<div style="width: 100%;float:left;"><tiles:insert name="footer"/></div>
<div id="graphhDiv" style="width: 745px; height: 800px; display: none; z-index: 1; position: absolute; top:0px; left:0px; background-color: white;">
	<img id="graphArrow" src="/SH/img/arrow.png" alt="" style="float:left" />
	<a id="graphCross" href="javascript:void(0);" style="margin: 0 5px;float: right; color: red;" onclick="closeGraph();">X</a>
	<div id="chartContainer" style="height: 300px; width: 400px; float:left;"></div>
	<div id="candleChartContainer" style="height: 500px; width: 700px; float:left;"></div>
</div>
</body>
</html>