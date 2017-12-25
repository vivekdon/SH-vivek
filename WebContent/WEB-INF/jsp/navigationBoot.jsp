<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Portfolio</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
      	<c:choose>
      		<c:when test="${nav!='' && nav=='equity'}">
      			<li class="active"><a href="../equity/show.html">Equity</a></li>
      		</c:when>
      		<c:otherwise>
      			<li><a href="../equity/show.html">Equity</a></li>
      		</c:otherwise>
      	</c:choose>
      	<c:choose>
      		<c:when test="${nav!='' && nav=='option'}">
      			<li class="active"><a href="../option/show.html">Option</a></li>
      		</c:when>
      		<c:otherwise>
      			<li><a href="../option/show.html">Option</a></li>
      		</c:otherwise>
      	</c:choose>
      	<c:choose>
      		<c:when test="${nav!='' && nav=='future'}">
      			<li class="active"><a href="../future/show.html">Future</a></li>
      		</c:when>
      		<c:otherwise>
      			<li><a href="../future/show.html">Future</a></li>
      		</c:otherwise>
      	</c:choose>
      	<c:choose>
      		<c:when test="${nav!='' && nav=='upload'}">
      			<li class="active"><a href="../upload/init.html">upload</a></li>
      		</c:when>
      		<c:otherwise>
      			<li><a href="../upload/init.html">upload</a></li>
      		</c:otherwise>
      	</c:choose>
      	<c:choose>
      		<c:when test="${nav!='' && nav=='intraday'}">
      			 <li class="active"><a href="#">Intraday</a></li>
      		</c:when>
      		<c:otherwise>
      			 <li><a href="#">Intraday</a></li>
      		</c:otherwise>
      	</c:choose>
      	
      	
      	
        
       
        
      </ul>
      
    </div>
  </div>
</nav>