<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<script src="<c:url value='/resources/js/jquery-3.1.1.min.js'/>"></script>
		<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
		<title>User Management Page</title>
		<link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
		<link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"></link>
		<link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>

	</head>
	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top">
		  <div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">User Management Page1 (Spring 3 MVC & Bootstrap & MongoDb Project)</a>
			</div>
		  </div>
		</nav>
		<br/>
		<br/>
	    <div class="generic-container">  

		    <div id="processMessageDiv" class="alert alert-${css} alert-dismissible" role="alert" <c:choose><c:when test="${not empty msg}">style="display: block"</c:when><c:otherwise>style="display: none"</c:otherwise></c:choose>>
			<button type="button" class="close" data-dismiss="alert"
                                aria-label="Close">
				<span aria-hidden="true">×</span>
			</button>
				<strong id="processMessageText">${msg}</strong>
		    </div>

	        <div class="panel panel-default">
	              <!-- Default panel contents -->
	            <div class="panel-heading"><span class="lead">List of Users </span></div>
	            <table class="table table-hover">
	                <thead>
	                    <tr>
	                        <th>User Name</th>
	                        <th>Name</th>
	                        <th>Last Name</th>
	                        <th>E-Mail</th>
	                        <th>Action</th>
	                    </tr>
	                </thead>
	                <tbody>
	                <c:forEach items="${users}" var="user">
	                    <tr>
	                        <td>${user.userName}</td>
	                        <td>${user.name}</td>
	                        <td>${user.lastName}</td>
	                        <td>${user.email}</td>
	                        <td>
	                        	<button type="button" class="btn btn-info btn-lg" onclick="window.location='users/${user.id}'">Edit</button>
	                        	<button type="button" class="btn btn-info btn-lg" onclick="deleteUserAjaxCall(this,'users/${user.id}/delete')">Delete</button>
	                        </td>
	                    </tr>
	                </c:forEach>
	                </tbody>
	            </table>
	        </div>
	        <div class="well">
	        	<button type="button" class="btn btn-info btn-lg" onclick="window.location='users/newUser'">Add User</button>
	        </div>
			
			<c:if test="${not empty selectedUser}">
			<!-- Modal -->
			<div id="myModal" class="modal fade" role="dialog">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <!-- Modal Header -->
			            <div class="modal-header">
			                <button type="button" class="close" 
			                   data-dismiss="modal">
			                       <span aria-hidden="true">&times;</span>
			                       <span class="sr-only">Close</span>
			                </button>
			                <h4 class="modal-title" id="myModalLabel">
				                <c:choose>
				                	<c:when test="${empty selectedUser.id}">
				                		Add New User
				                		<spring:url value="/users/newUser/save" var="formUrl" />
				                		<spring:url value="Save" var="buttonName" />
				                	</c:when>
				                	<c:otherwise>
				                	Update User
				                		<spring:url value="/users/{${selectedUser.id}}/update" var="formUrl" />
				                		<spring:url value="Update" var="buttonName" />
				                	</c:otherwise>
				                </c:choose>
			                </h4>
			            </div>
			            
			            <!-- Modal Body -->
			            <div class="modal-body">
					
							<!-- Bootstrap form -->
							<form:form modelAttribute="selectedUser" class="form-horizontal"
								id="add-user-form" action="${formUrl}">
								<fieldset>
									<form:hidden path="id" />
									<div class="control-group" id="userName">
										<label class="control-label">Enter your user name:</label>
										<div class="controls">
											<form:input path="userName" />
											<span class="help-inline"><form:errors path="userName" /></span>
										</div>
									</div>
									<div class="control-group" id="name">
										<label class="control-label">Enter your name:</label>
										<div class="controls">
											<form:input path="name" />
											<span class="help-inline"><form:errors path="name" /></span>
										</div>
									</div>
									<div class="control-group" id="lastName">
										<label class="control-label">Enter your last name:</label>
										<div class="controls">
											<form:input path="lastName" />
											<span class="help-inline"><form:errors path="lastName" /></span>
										</div>
									</div>
									<div class="control-group" id="email">
										<label class="control-label">Enter your e-mail:</label>
										<div class="controls">
											<form:input path="email" />
											<span class="help-inline"><form:errors path="email" /></span>
										</div>
									</div>
									<div class="g-recaptcha" data-sitekey="6LcGMwgUAAAAAKKQDOHODHe8uquI_plOzsPCJVBu"></div>
									<div class="form-actions">
										<button type="submit" class="btn btn-primary">${buttonName}</button>
						                <button type="button" class="btn btn-default"
						                        data-dismiss="modal">
						                            Close
						                </button>
									</div>
								</fieldset>
							</form:form>
			            </div>
			        </div>
			    </div>
			</div>
			</c:if>
	        
		  <hr>
		  <footer>
			<p>&copy; Ahmet BOZ 2016</p>
		  </footer>
	    </div>
		<script src="<c:url value='/resources/js/app.js'/>"></script>
	</body>
</html>