<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
var chartData = [];
function showEquityGraph(symbol){
	
	jQuery.ajax({
    	type: "POST",
    	url: '../equity/getGraphData.html',
    	dataType: "json",
    	data: {  symbol:symbol},  
    	error: function(jqXHR, textStatus, errorThrown){  
	          alert('Error');
	      },
	 success: function(json) {
		 for(var i in json){
			 var newDate = new Date(json[i].valueDate);
			 newDate.setHours( 0, 0, 0, 0 );
			// newDate.setDate( newDate.getDate() + i );
			 
			 chartData[ i ] = ( {
			      "date": newDate,
			      "open": json[i].openVal,
			      "close": json[i].closeVal,
			      "high": json[i].highVal,
			      "low": json[i].lowVal,
			      "volume": json[i].volume,
				  "noTrade": json[i].tradeVal
			    } );
			    
			
		 }	
		 var chart = AmCharts.makeChart( "chartdiv", {
			  "type": "stock",
			  "theme": "light",
			  "dataSets": [ {
			    "fieldMappings": [ {
			      "fromField": "open",
			      "toField": "open"
			    }, {
			      "fromField": "close",
			      "toField": "close"
			    }, {
			      "fromField": "high",
			      "toField": "high"
			    }, {
			      "fromField": "low",
			      "toField": "low"
			    }, {
			      "fromField": "volume",
			      "toField": "volume"
			    }, {
			      "fromField": "noTrade",
			      "toField": "noTrade"
			    }, {
			      "fromField": "value",
			      "toField": "value"
			    } ],
			    "color": "#7f8da9",
			    "dataProvider": chartData,
			    "categoryField": "date"
			  } ],
			  "balloon": {
			    "horizontalPadding": 13
			  },
			  "panels": [ {
			    "title": "Value",
			    "stockGraphs": [ {
			      "id": "g1",
			      "type": "candlestick",
			      "openField": "open",
			      "closeField": "close",
			      "highField": "high",
			      "lowField": "low",
			      "valueField": "close",
			      "lineColor": "#7f8da9",
			      "fillColors": "#7f8da9",
			      "negativeLineColor": "#db4c3c",
			      "negativeFillColors": "#db4c3c",
			      "fillAlphas": 1,
			      "balloonText": "open:<b>[[open]]</b><br>close:<b>[[close]]</b><br>low:<b>[[low]]</b><br>high:<b>[[high]]</b>",
			      "useDataSetColors": false
			    } ]
			  } ],
			  "scrollBarSettings": {
			    "graphType": "line",
			    "usePeriod": "WW"
			  },
			  "panelsSettings": {
			    "panEventsEnabled": true
			  },
			  "cursorSettings": {
			    "valueBalloonsEnabled": true,
			    "valueLineBalloonEnabled": true,
			    "valueLineEnabled": true
			  }
			  /*,
			  "periodSelector": {
			    "position": "bottom",
			    "periods": [ {
			      "period": "DD",
			      "count": 10,
			      "label": "10 days"
			    }, {
			      "period": "MM",
			      "selected": true,
			      "count": 1,
			      "label": "1 month"
			    }, {
			      "period": "YYYY",
			      "count": 1,
			      "label": "1 year"
			    }, {
			      "period": "YTD",
			      "label": "YTD"
			    }, {
			      "period": "MAX",
			      "label": "MAX"
			    } ]
			  }*/
			} );

		 adjustTableHeight();	 
	 	}
	     
	 });
	 
	 document.getElementById('jumbotron').style.display='block';
	
}

function addPanel() {
	  var chart = AmCharts.charts[ 0 ];
	  if ( chart.panels.length == 2 ) {
	    var newPanel = new AmCharts.StockPanel();
	    newPanel.allowTurningOff = true;
	    newPanel.title = "Trade";
	    newPanel.showCategoryAxis = false;

	    var graph = new AmCharts.StockGraph();
	    graph.valueField = "noTrade";
	    graph.fillAlphas = 0.15;
	    newPanel.addStockGraph( graph );

	    var legend = new AmCharts.StockLegend();
	    legend.markerType = "none";
	    legend.markerSize = 0;
	    newPanel.stockLegend = legend;

	    chart.addPanelAt( newPanel, 1 );
		//chart.addPanelAt( newPanel, 2 );
	    chart.validateNow();
	  }
	  if ( chart.panels.length == 1 ) {
	    var newPanel = new AmCharts.StockPanel();
	    newPanel.allowTurningOff = true;
	    newPanel.title = "Volume";
	    newPanel.showCategoryAxis = false;

	    var graph = new AmCharts.StockGraph();
	    graph.valueField = "volume";
	    graph.fillAlphas = 0.15;
	    newPanel.addStockGraph( graph );

	    var legend = new AmCharts.StockLegend();
	    legend.markerType = "none";
	    legend.markerSize = 0;
	    newPanel.stockLegend = legend;

	    chart.addPanelAt( newPanel, 1 );
		//chart.addPanelAt( newPanel, 2 );
	    chart.validateNow();
	  }
	  adjustTableHeight();
	  
	}

	function removePanel() {
	  var chart = AmCharts.charts[ 0 ];
	  if ( chart.panels.length > 1 ) {
	    chart.removePanel( chart.panels[ 1 ] );
	    chart.validateNow();
	  }
	}
function showEquityData(){
	
	var sector=document.getElementById('sector').value;
	var symbol=document.getElementById('symbol').value;
	var low52=document.getElementById('low52').value;
	var high52=document.getElementById('high52').value;
	var limit=document.getElementById('limit').value;
	
	
	jQuery("#tableContainer").html('<div class="loader"></div>');
	 jQuery.ajax({
	    	type: "POST",
	    	url: '../equity/showTable.html',
	    	dataType: "json",
	    	data: { sector: sector, symbol:symbol, high52:high52, low52:low52, limit:limit},  
	    	error: function(jqXHR, textStatus, errorThrown){  
		          alert('Error');
		      },
  	 success: function(json) {
		var numericCode='';
		
		var table='<table class="table table-bordered table-fixed">';
		table=table+'<thead>';
		table=table+'<tr style="background-color:#81A2F3">';
		table=table+'<th rowspan="2">Symbol</th><th colspan="3">'+json[0].vd1+'</th><th colspan="3">'+json[0].vd2+'</th><th colspan="3">'+json[0].vd3+'</th><th colspan="3">'+json[0].vd4+'</th><th colspan="3">'+json[0].vd5+'</th>';
		table=table+'</tr>';
		table=table+'<tr style="background-color:#81A2F3">';
		table=table+'<th>Close price</th><th>Volume</th><th>Trade value</th><th>Close price</th><th>Volume</th><th>Trade value</th><th>Close price</th><th>Volume</th><th>Trade value</th><th>Close price</th><th>Volume</th><th>Trade value</th><th>Close price</th><th>Volume</th><th>Trade value</th>';
		table=table+'</tr>';
		
		table=table+'</thead>';
		table=table+'<tbody>';
		
		for(var i in json)
		{
			table=table+'<tr>';
			table=table+'<td rowspan="2" style="background-color:#F5F4F3"> <a href="javascript:void(0)" onclick="showEquityGraph(\''+ json[i].symbol+'\')">'+ json[i].symbol+'</a></td>';
			
			table=table+'<td>'+ json[i].cv1+'</td>';
			table=table+'<td>'+ json[i].ttq1+'</td>';
			table=table+'<td>'+ json[i].tt1+'</td>';
			
			table=table+'<td>'+ json[i].cv2+'</td>';
			table=table+'<td>'+ json[i].ttq2+'</td>';
			table=table+'<td>'+ json[i].tt2+'</td>';
			
			table=table+'<td>'+ json[i].cv3+'</td>';
			table=table+'<td>'+ json[i].ttq3+'</td>';
			table=table+'<td>'+ json[i].tt3+'</td>';
			
			table=table+'<td>'+ json[i].cv4+'</td>';
			table=table+'<td>'+ json[i].ttq4+'</td>';
			table=table+'<td>'+ json[i].tt4+'</td>';
			
			table=table+'<td>'+ json[i].cv5+'</td>';
			table=table+'<td>'+ json[i].ttq5+'</td>';
			table=table+'<td>'+ json[i].tt5+'</td>';
			
			table=table+'</tr>';
			
			table=table+'<tr>';
			var color='';
			
			if(json[i].closePerc1 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].closePerc1 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+' >'+ json[i].closePerc1+'</td>';
			if(json[i].totTrdQtyPerc1 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdQtyPerc1 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdQtyPerc1+'</td>';
			
			if(json[i].totTrdPerc1 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdPerc1 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdPerc1+'</td>';
			
			if(json[i].closePerc2 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].closePerc2 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].	Perc2+'</td>';
			if(json[i].totTrdQtyPerc2 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdQtyPerc2 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdQtyPerc2+'</td>';
			if(json[i].totTrdPerc2 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdPerc2 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdPerc2+'</td>';
			if(json[i].closePerc3 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].closePerc3 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].closePerc3+'</td>';
			if(json[i].totTrdQtyPerc3 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdQtyPerc3 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdQtyPerc3+'</td>';
			if(json[i].totTrdPerc3 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdPerc3 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdPerc3+'</td>';
			if(json[i].closePerc4 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].closePerc4 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].closePerc4+'</td>';
			if(json[i].totTrdQtyPerc4 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdQtyPerc4 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdQtyPerc4+'</td>';
			if(json[i].totTrdPerc4 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdPerc4 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdPerc4+'</td>';
			if(json[i].closePerc5 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].closePerc5 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].closePerc5+'</td>';
			if(json[i].totTrdQtyPerc5 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdQtyPerc5 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdQtyPerc5+'</td>';
			if(json[i].totTrdPerc5 < 0){
				color=' style="background-color:#FB967F"';
			}else if(json[i].totTrdPerc5 > 0){
				color=' style="background-color:#B6FDB9"';
			}
			table=table+'<td'+ color+'>'+ json[i].totTrdPerc5+'</td>';
			
			table=table+'</tr>';
		}
		
		table=table+'</table>';
		jQuery("#tableContainer").html(table);
		
		adjustTableHeight();
  	 }
		      
	
});}
	function adjustTableHeight(){
		var winH=window.innerHeight;
		var tableTop=jQuery("#tableContainer").offset().top; 
		if(winH-tableTop>100){
			jQuery("#tableContainer").height(winH-tableTop-50);	
		}
		
	} 

</script>
<div class="container" style="display: none;" id="jumbotron">
	
	<div class="container text-center"> 
   		<div id="chartdiv" style="float:left"></div>
   		<div style="margin-left: 20px; float:left">
			<a href="#" onclick="document.getElementById('jumbotron').style.display='none';adjustTableHeight();">Close</a>
			<input type="button" class="amChartsButton btn" id="addPanelButton" value="add panel" onclick="addPanel();">
			<input type="button" class="amChartsButton btn" id="removePanelButton" value="remove panel" onclick="removePanel();">
			
		</div>
		
 	</div>
</div>

<form:form action="submit.html" method="post" commandName="buyForm" onsubmit="return false">
	<div class="container" style="width:100%; margin-bottom:20px; background-color:light-grey;">
  <div class="form-group">
  <div class="col-xs-1">
	<label for="sector">Sector</label>
      <select class="form-control" id="sector">
      	<option value="All">All</option>
	      <c:forEach items="${sectorList}" var="sector" varStatus="x" >    		
	      	 <option value="${sector}">${sector}</option>   
	      </c:forEach>
        </select>
	</div>
	<div class="col-xs-1">
		<label for="symbol">Symbol</label>
		<input class="form-control" id="symbol" type="text">
	</div>
	<div class="col-xs-1">
		<label for="high52">52 Week High</label>
		<input class="form-control" id="high52" type="text">
	</div>
	<div class="col-xs-1">
		<label for="low52">52 Week low</label>
		<input class="form-control" id="low52" type="text">
	</div>
	<div class="col-xs-1">
		<label for="low52">% Change</label>
		<input class="form-control" id="limit" type="text">
	</div>
	<div class="col-xs-2" style="vertical-align: center;padding-top: 23px;" >
		<button type="button" class="btn btn-primary" onclick="showEquityData()" >Submit</button>
	</div>
	
	</div>
  
  </div>
  
  <div  class="container" id="tableContainer" style="height: 450px; overflow:auto; width:100%;">
  </div>
</form:form>