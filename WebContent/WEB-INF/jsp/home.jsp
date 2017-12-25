<!-- THis div will represent table -->

<div style=" margin-left: 5%; float:left;" >
	<div style="margin:10px 0;">
		 
		Sort by <select onchange="changeSort();" id="sortBySel">
					<option value="lp1">lp1</option>
					<option value="lp2">lp2</option>
					<option value="lp3">lp3</option>
					<option value="lp4">lp4</option>
					<option value="total_change">total change</option>
					
		</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		Sort type <select onchange="changeSortType();" id="sortByType">
					<option value="ASC">ASC</option>
					<option value="DESC">DESC</option>
		</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="refershButton" onclick="toggleReferesh()">Stop</button>
	</div>
	<div id="tableSort"></div>
	
</div>
<!-- THis div will Buy data -->
<div style=" margin-left: 5%; float:left;" id="boughtDiv"></div>
<!-- THis div will represent bought data -->
<div style="" >
	
</div>