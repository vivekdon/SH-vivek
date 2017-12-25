<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="jumbotron">
<div class="container">
<form:form action="upload.html" method="post" enctype="multipart/form-data" commandName="uploadForm" onsubmit="return validateUploadform()">
		
  <div class="form-group">
  	<label for="sel1" >File Type</label>
	
		<form:select path="fileType" cssClass="form-control" id="sel1">
			<form:option value="SELECT">SELECT</form:option>
			<form:option value="EQUITY">EQUITY</form:option>
			<form:option value="FUTURE">FUTURE</form:option>
			<form:option value="OPTION">OPTION</form:option>
			
		</form:select>
	
   </div>
	
     
	
	<div class="form-group">
		<label for="upload1">Upload File</label>
		
		<%-- 	<form:input path="file" cssClass="form-control" id="upload1"  />--%>
			<input type="file" name="file">
			
		
	</div>
	<div class="form-group"> 
    	
      	<button type="submit" class="btn btn-default">Submit</button>
    </div>
  </form:form>
  </div>
  </div>