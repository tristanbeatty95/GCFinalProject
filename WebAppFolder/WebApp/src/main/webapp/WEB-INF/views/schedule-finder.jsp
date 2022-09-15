<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Schedule Finder</title>
<!-- Import link for tailwind -->
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css"
	rel="stylesheet">
	<link href='https://fonts.googleapis.com/css?family=Poppins' rel='stylesheet'>
<link rel="stylesheet" href="styles.css">
<script src="https://kit.fontawesome.com/aa77e8e357.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<!-- Navbar at top of page -->
	<nav class="navbar">
		<ul class="nav-links">
			<li class="nav-item"><a
				href="/weekly-calendar?month=${monthNum}">Weekly View</a></li>
			<li class="nav-item"><a href="/monthly-calendar">Monthly
					View</a></li>
			<li class="nav-item" class="button"><a href="#divOne">Add
					Event</a></li>
		</ul>
		<ul class="nav-links2">
			<li class="nav-item" id="companyNameText">
				<p>Search For Open Times</p>
			</li>
		</ul>
		<ul class="nav-links3">
			<li style="background-color: #D15656" class="nav-item"
				id="logoutButton"><a href="/logout">Logout</a></li>
		</ul>
	</nav>

	<div class="overlay" id="divOne">
		<div class="wrapper">

			<a href="#" class="close">&times;</a>
			<div class="content">
				<div class="container">
					<form action="submitEvent" method="post">
						<label for="eventName">Event Name</label> <input type="text"
							id="eventName" name="eventName" placeholder="Event name">
						<label for="startTime">Start Time</label> <input
							type="datetime-local" name="start" id="startTime"> <br>
						<label for="endTime">End Time</label> <input type="datetime-local"
							name="end" id="endTime"> <br> <label
							for="addEmployees">Add Employees</label>
						<textarea id="addEmployees" name="addEmployees"
							placeholder="Add multiple employees by separating with a comma"
							rows="3" cols="30"></textarea>
						<input type="submit" value="Submit">
					</form>
				</div>
			</div>
		</div>
	</div>

	
<h3>Search For Open Dates To Hold Events </h3><br>
<form action="/schedule-finder1" method="get">
Date Start: <input type="datetime-local" name="start" required/><br>
Date End: <input type="datetime-local" name="end" required/><br>
List of Employees: <input type="text" name="employees" placeholder="Add multiple employees by separating with a comma"/> <br>
<input type="submit" />

</form>
<c:if test="${empty conflictMessage}">
   There are currently no conflicts to show!
</c:if>
<c:if test="${not empty conflictMessage}"> ${conflictMessageString } <br> ${suggestOneString } <br> ${suggestTwoString }</c:if>



</body>
</html>