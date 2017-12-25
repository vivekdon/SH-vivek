<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE form:form PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="/SH/css/style.css" rel="stylesheet" type="text/css">
	<script  type="text/javascript" src="/SH/js/jquery.min.js"></script>
	
	<script type="text/javascript">
		function validateBuyform(){
			if($("#numericCode").val()==''  ){
				alert('Numeric code is not correct');
				return false;
			}
			if($("#buyVal").val()==''){
				alert('Buy value can not be null');
				return false;
			}
			return true;
		}
	</script>
</head>
<body>
	<form:form action="submitSale.html" method="post" commandName="buyForm" onsubmit="return validateBuyform()">
		<table>
			
			<tr>
				<th colspan="2">Sale Form</td>
			</tr>
			<c:choose>
				<c:when test="${not empty(buyForm.error)}">
					<tr>
						<td colspan="2" style="color: red; font-weight: bold;">${buyForm.error }</td>
					</tr>
				</c:when>
			</c:choose>
			<tr>
				<td>Numeric code</td>
				<td><form:input path="numericCode"  /> </td>
			</tr>
			<tr>
				<td>Sale Value</td>
				<td><form:input path="buyVal"/></td>
			</tr>
			<tr>
				<td>Quantity</td>
				<td><form:input path="quantity"/></td>
			</tr>
			<tr>
				<td colspan="2"> <input type="submit"> </td>
			</tr>
		</table>
	</form:form>
</body>
</html>
