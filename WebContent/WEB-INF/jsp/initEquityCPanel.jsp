			


<div class="jumbotron">
  <div class="container text-center"> 
   <div style="padding-bottom: 5px;">
<input type="button" class="amChartsButton" id="addPanelButton" value="add panel" onclick="addPanel();">
<input type="button" class="amChartsButton" id="removePanelButton" value="remove panel" onclick="removePanel();">
</div>
<div id="chartdiv"></div>
 
</div>
</div>
<div class="container">
  <div class="form-group row">
  <div class="col-xs-1">
	<label for="sel1">Sector</label>
      <select class="form-control" id="sel1">
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
      </select>
	</div>
	<div class="col-xs-1">
		<label for="ex1">Name</label>
		<input class="form-control" id="ex1" type="text">
	</div>
	<div class="col-xs-2">
		<label for="ex1">52 Week High</label>
		<input class="form-control" id="ex1" type="text">
	</div>
	<div class="col-xs-2">
		<label for="ex1">52 Week low</label>
		<input class="form-control" id="ex1" type="text">
	</div>
	<div class="col-xs-2">
		<label for="ex1">Percent Change</label>
		<input class="form-control" id="ex1" type="text">
	</div>
	</div>
  <button type="button" class="btn btn-default">Submit</button>
  </div>
  <div  class="container">
    <table class="table table-hover table-bordered">
    <thead>
      <tr>
        <th>symbol</th><th>Open</th><th>High</th><th>low</th><th>close</th><th>Prev Close</th><th>Total trade Qty</th><th>Total trade val</th><th>Total Trade</th><th>%</th>
      </tr>
    </thead>
	<tbody>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=MERCATOR" target="_blank">MERCATOR</a></td><td>40.65</td><td>43.75</td><td>40.65</td><td>43.5</td><td>41.4</td><td>1405604</td><td>60194660</td><td>3848</td><td>7.01</td>
		</tr>
			<tr><td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=MANINDS" target="_blank">MANINDS</a></td><td>70.8</td><td>75.5</td><td>70.75</td><td>74.85</td><td>71.85</td><td>452215</td><td>33434362.9</td><td>3233</td><td>5.72</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=GSPL" target="_blank">GSPL</a></td><td>163.8</td><td>174.3</td><td>162.3</td><td>173.15</td><td>163.4</td><td>886897</td><td>150719772</td><td>21986</td><td>5.7</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=PARAGMILK" target="_blank">PARAGMILK</a></td><td>208</td><td>219.25</td><td>206.1</td><td>217.5</td><td>208.05</td><td>651551</td><td>137405453.7</td><td>4893</td><td>4.56</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=ICICIPRULI" target="_blank">ICICIPRULI</a></td><td>438.25</td><td>459.75</td><td>435.85</td><td>457.55</td><td>440</td><td>808440</td><td>364395664.4</td><td>31924</td><td>4.4</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=INDIANB" target="_blank">INDIANB</a></td><td>270</td><td>284.6</td><td>269</td><td>281.8</td><td>275.85</td><td>1977684</td><td>554341357.15</td><td>19170</td><td>4.37</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=RPOWER" target="_blank">RPOWER</a></td><td>40.65</td><td>42.5</td><td>40.65</td><td>42.35</td><td>41.1</td><td>4145062</td><td>173558346.75</td><td>11427</td><td>4.18</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=TECHNO" target="_blank">TECHNO</a></td><td>366</td><td>384</td><td>363</td><td>381.15</td><td>368.85</td><td>20971</td><td>7861691.45</td><td>1534</td><td>4.13</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=CENTURYPLY" target="_blank">CENTURYPLY</a></td><td>289.5</td><td>302.5</td><td>289.5</td><td>300.85</td><td>290.9</td><td>150634</td><td>44815559.3</td><td>3029</td><td>3.92</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=DHAMPURSUG" target="_blank">DHAMPURSUG</a></td><td>181</td><td>189.7</td><td>180</td><td>188.05</td><td>180.75</td><td>501194</td><td>93283894.9</td><td>8399</td><td>3.89</td>
		</tr>
		<tr>
			<td><a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=INDIGO" target="_blank">INDIGO</a></td><td>1218</td><td>1274.75</td><td>1207.55</td><td>1264.9</td><td>1218.25</td><td>537883</td><td>670490704.75</td><td>20872</td><td>3.85</td>
		</tr>
		</tbody>
    <!--<tbody>
      <tr>
        <td>John</td>
        <td>Doe</td>
        <td>john@example.com</td>
      </tr>
      <tr>
        <td>Mary</td>
        <td>Moe</td>
        <td>mary@example.com</td>
      </tr>
      <tr>
        <td>July</td>
        <td>Dooley</td>
        <td>july@example.com</td>
      </tr>
    </tbody>-->
  </table>
</div>