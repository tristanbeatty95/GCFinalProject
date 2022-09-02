<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weekly Calendar</title>
<!-- Import link for tailwind --> 
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css" rel="stylesheet">
<link rel="stylesheet" href="styles.css">
</head>
<body>
<!-- Navbar at top of page --> 
	<nav class="navbar">
		<ul class="nav-links">
			
			<li style="background-color:#8D8D8D" class="nav-item">
				<a href="/monthly-calendar" style="color:#FFFFFF">Monthly View</a>
			</li>
			<li style="background-color:#8D8D8D" class="nav-item">
				<a href="/add-event" style="color:#FFFFFF">Add Event</a>
			</li>
		</ul>
		<ul class="nav-links">
			<li class="nav-item" id="companyNameText">
				<p style="color:#FFFFFF">Timely</p>
			</li>
		</ul>
		<ul class="nav-links">
			<li style="background-color:#D15656" class="nav-item" id="logoutButton">
				<a href="/logout">Logout</a>
			</li>
		</ul>
	</nav>
	<!-- Calendar Grid --> 
	<div class="calendar-and-daily-info">
		<div>
		<!-- Gets current month and year as a String -->
		<h1><c:out value="${monthStr}"/> <c:out value="${year}"/></h1>
			
			<table>
				<!-- Table Header (row 6) -->
				<tr>
					<th>Sunday</th>
					<th>Monday</th>
					<th>Tuesday</th>
					<th>Wednesday</th>
					<th>Thursday</th>
					<th>Friday</th>
					<th>Saturday</th>
				</tr>
		
				<!-- Week -->
			
					<tr>
					<c:forEach var="dayNum" items="${dayNums}" begin="0" end="6">
						<td>${dayNum}</td>
					</c:forEach>
				</tr>
		
			</table>
		</div>
	
	
	<div class="daily-info-section">
			<h3 id="day-info-header">Day Info</h3>
			<a href="/delete-event" id="delete-button">Delete</a>
		</div>
	</div>

</body>
</html>