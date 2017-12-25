<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
var chartData = [];
function showOptionGraph(symbol, expDate,strPrice,optType){
	
	jQuery.ajax({
    	type: "POST",
    	url: '../option/getGraphData.html',
    	dataType: "json",
    	data: {  symbol:symbol,expDate:expDate,strPrice:strPrice,optType:optType},  
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
function showOptionData(){
	
	
	var symbol=document.getElementById('symbol').value;
	var index=document.getElementById('index').value;
	var expDate=document.getElementById('expDate').value;
	var type=document.getElementById('type').value;
	
	
	jQuery("#tableContainer").html('<div class="loader"></div>');
	 jQuery.ajax({
	    	type: "POST",
	    	url: '../option/showTable.html',
	    	dataType: "json",
	    	data: { symbol:symbol, index:index, expDate:expDate,type:type },  
	    	error: function(jqXHR, textStatus, errorThrown){  
		          alert('Error');
		      },
  	 success: function(json) {
  		 
		var numericCode='';
		if(json){
		var table='<table class="table table-bordered table-fixed">';
		table=table+'<thead>';
		table=table+'<tr style="background-color:#81A2F3; padding:0 0 0 0;">';
		table=table+'<th rowspan="2" style="padding:0 0 0 0;">Symbol</th><th rowspan="2" style="padding:0 0 0 0;">Expiry Date</th><th style="padding:0 0 0 0;" rowspan="2">Strike Price</th><th style="padding:0 0 0 0; text-align:center;" colspan="6" >'+json[0].vd1+'</th><th style="padding:0 0 0 0;text-align:center;" colspan="6">'+json[0].vd2+'</th><th style="padding:0 0 0 0;text-align:center;" colspan="6">'+json[0].vd3+'</th><th style="padding:0 0 0 0;text-align:center;" colspan="6">'+json[0].vd4+'</th><th style="padding:0 0 0 0;text-align:center;" colspan="6">'+json[0].vd5+'</th>';
		table=table+'</tr>';
		table=table+'<tr style="background-color:#81A2F3">';
		table=table+'<th style="padding:0 0 0 0;" colspan="2">Close price</th><th style="padding:0 0 0 0;" colspan="2">Volume</th><th style="padding:0 0 0 0;" colspan="2">Trade value</th><th style="padding:0 0 0 0;" colspan="2">Close price</th><th style="padding:0 0 0 0;" colspan="2">Volume</th><th style="padding:0 0 0 0;" colspan="2">Trade value</th><th style="padding:0 0 0 0;" colspan="2">Close price</th><th style="padding:0 0 0 0;" colspan="2">Volume</th><th style="padding:0 0 0 0;" colspan="2">Trade value</th><th style="padding:0 0 0 0;" colspan="2">Close price</th><th style="padding:0 0 0 0;" colspan="2">Volume</th><th style="padding:0 0 0 0;" colspan="2">Trade value</th><th style="padding:0 0 0 0;" colspan="2">Close price</th><th style="padding:0 0 0 0;" colspan="2">Volume</th><th style="padding:0 0 0 0;" colspan="2">Trade value</th>';
		table=table+'</tr>';
		
		table=table+'</thead>';
		table=table+'<tbody>';
		
		for(var i in json)
		{
			table=table+'<tr>';
			table=table+'<td style="background-color:#F5F4F3; padding:0 0 0 0;"> <a href="javascript:void(0)" onclick="showOptionGraph(\''+ json[i].symbol+'\',\''+ json[i].expDate+'\',\''+ json[i].strPrice+'\',\''+ json[i].optType+'\')">'+json[i].symbol+'('+json[i].optType+')</a>';
			
			table=table+'<td style="white-space: nowrap; padding:0 0 0 0;">'+ json[i].expDate+'</td>';
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].strPrice+'</td>';
			
			var color='';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].cp1+'</td>';
			if(json[i].cp1p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].cp1p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+' >'+ json[i].cp1p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].v1+'</td>';
			if(json[i].v1p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].v1p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].v1p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].tq1+'</td>';
			
			if(json[i].tq1p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].tq1p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].tq1p+'</td>';
			
			
			
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].cp2+'</td>';
			if(json[i].cp2p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].cp2p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].cp2p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].v2+'</td>';
			if(json[i].v2p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].v2p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].v2p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].tq2+'</td>';
			if(json[i].tq2p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].tq2p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].tq2p+'</td>';
			
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].cp3+'</td>';
			if(json[i].cp3p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].cp3p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].cp3p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].v3+'</td>';
			if(json[i].v3p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].v3p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].v3p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].tq3+'</td>';
			if(json[i].tq3p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].tq3p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].tq3p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].cp4+'</td>';
			if(json[i].cp4p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].cp4p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].cp4p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].v4+'</td>';
			if(json[i].v4p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].v4p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].v4p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].tq4+'</td>';
			if(json[i].tq4p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].tq4p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].tq4p+'</td>';
			
			
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].cp5+'</td>';
			if(json[i].cp5p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].cp5p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].cp5p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].v5+'</td>';
			if(json[i].v5p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].v5p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].v5p+'</td>';
			
			table=table+'<td style="padding:0 0 0 0;">'+ json[i].tq5+'</td>';
			if(json[i].tq5p < 0){
				color=' style="background-color:#FB967F;padding:0 0 0 0;"';
			}else if(json[i].tq5p > 0){
				color=' style="background-color:#B6FDB9;padding:0 0 0 0;"';
			}else{
				color=' style="padding:0 0 0 0;"';
			}
			table=table+'<td'+ color+'>'+ json[i].tq5p+'</td>';
			
			table=table+'</tr>';
			
			
		}
		
		table=table+'</table>';
		jQuery("#tableContainer").html(table);
		}else{
			jQuery("#tableContainer").html('No data found');
		}
		
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
	
	function updateIndexExpiry(obj){
		if(obj.value!=''){
			jQuery.ajax({
		    	type: "POST",
		    	url: '../option/updateIndexExpiry.html',
		    	dataType: "json",
		    	data: { symbol:obj.value },  
		    	error: function(jqXHR, textStatus, errorThrown){  
			          alert('Error');
			      },
	  			 success: function(json) {
	  		 
	  	 		}
			});	
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
		<label for="symbol">Symbol</label>
		<div><input class="form-control" id="symbol" type="text" onblur="updateIndexExpiry(this);"></div>
	</div>
	<div class="col-xs-1">
		<label for="index">Index</label>
		<div id="indexDiv"><input class="form-control" id="index" type="text"></div>
	</div>
	<div class="col-xs-1">
		<label for="expDate">Expiry Date</label>
		<div id="expDiv"><input class="form-control" id="expDate" type="text"></div>
	</div>
	<div class="col-xs-1">
		<label for="type">Type</label>
		<select id="type" class="form-control" >
			<option value="Both">Both</option>
			<option value="CE">CE</option>
			<option value="PE">PE</option>
		</select>
		
	</div>
	<div class="col-xs-2" style="vertical-align: center;padding-top: 23px;" >
		<button type="button" class="btn btn-primary" onclick="showOptionData()" >Submit</button>
	</div>
	
	</div>
  
  </div>
  
  <div  class="container" id="tableContainer" style="height: 450px; overflow:auto; width:100%;">
  </div>
</form:form>