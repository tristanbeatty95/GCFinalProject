<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Import link for tailwind --> 
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css" rel="stylesheet">
<link rel="stylesheet" href="styles.css">
<script src="https://kit.fontawesome.com/aa77e8e357.js" crossorigin="anonymous"></script>
<title>Monthly Calendar</title>
</head>
<body>
	<!-- Navbar at top of page --> 
	<nav class="navbar">
		<ul class="nav-links">
			<li style="background-color:#8D8D8D" class="nav-item">
				<a href="/weekly-calendar?month=${monthNum}" style="color:#FFFFFF">Weekly View</a>
			</li>
			<li style="background-color:#8D8D8D" class="nav-item" class="button">
				<a href="#divOne" style="color:#FFFFFF">Add Event</a>
			</li>
			<li style="background-color: #8D8D8D" class="nav-item"><a
				href="/schedule-finder" style="color: #FFFFFF">Search Events</a></li>
		</ul>
		<ul class="nav-links">
			<li class="nav-item" id="companyNameText">
				<p style="color:#FFFFFF">Monthly Calendar</p>
			</li>
		</ul>
		<ul class="nav-links">
			<li style="background-color:#D15656" class="nav-item" id="logoutButton">
				<a href="/logout">Logout</a>
			</li>
		</ul>
	</nav>
	
	<div class="overlay" id="divOne">
		<div class="wrapper">
			
			<a href="#" class="close">&times;</a>
			<div class="content">
				<div class="container">
					<form action="submitEvent" method="post">
						<label for="eventName">Event Name</label>
						<input type="text" id="eventName" name="eventName" placeholder="Event name">
						<label for="startTime">Start Time</label>
						<input type="datetime-local" name="start" id="startTime">
						<br>
						<label for="endTime">End Time</label> 
						<input type="datetime-local" name="end" id="endTime">
						<br>
						<label for="addEmployees">Add Employees</label>
						<textarea id="addEmployees" name="addEmployees" placeholder="Add multiple employees by separating with a comma"
						rows="3" cols="30"></textarea>
						<input type="submit" value="Submit"> 	
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Calendar Grid --> 
	<div class="calendar-and-daily-info">
		<div>
			<div class="month-navigation">
				<a id="prevButton" href="/monthly-calendar?date=${prevMonthDate}"><i class="fa-solid fa-arrow-left"></i></a>
			
				<p id="month-header-text"><c:out value="${curMonthString}"/> <c:out value="${curMonthDate.year}"/></p>
			
				<a id="nextButton" href="/monthly-calendar?date=${nextMonthDate}"><i class="fa-solid fa-arrow-right"></i></a>
			</div>
			
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
		
				<!-- Week 1 -->
				<tr>
					<c:forEach var="date" items="${dates}" begin="0" end="6" varStatus="loop">
						<td>
							<a href="/monthly-calendar?date=${curMonthDate}&dayDate=${date}">
							${date.dayOfMonth}
							<br>
							<c:set var="dateString">${date.toString()}</c:set>
							<c:forEach var="dayEvent" items="${events[dateString]}">
								${dayEvent.eventName}
								<br>
							</c:forEach>
							</a>
						</td>
						
					</c:forEach>
				</tr>
				
				<tr>
					<c:forEach var="date" items="${dates}" begin="7" end="13" varStatus="loop">
						<td>
							<a href="/monthly-calendar?date=${curMonthDate}&dayDate=${date}">
							${date.dayOfMonth}
							<br>
							<c:set var="dateString">${date.toString()}</c:set>
							<c:forEach var="dayEvent" items="${events[dateString]}">
								${dayEvent.eventName}
								<br>
							</c:forEach>
							</a>
						</td>
						
					</c:forEach>
				</tr>
				
				<tr>
					<c:forEach var="date" items="${dates}" begin="14" end="20" varStatus="loop">
						<td>
							<a href="/monthly-calendar?date=${curMonthDate}&dayDate=${date}">
							${date.dayOfMonth}
							<br>
							<c:set var="dateString">${date.toString()}</c:set>
							<c:forEach var="dayEvent" items="${events[dateString]}">
								${dayEvent.eventName}
								<br>
							</c:forEach>
							</a>
						</td>
						
					</c:forEach>
				</tr>
				
				<tr>
					<c:forEach var="date" items="${dates}" begin="21" end="27" varStatus="loop">
						<td>
							<a href="/monthly-calendar?date=${curMonthDate}&dayDate=${date}">
							${date.dayOfMonth}
							<br>
							<c:set var="dateString">${date.toString()}</c:set>
							<c:forEach var="dayEvent" items="${events[dateString]}">
								${dayEvent.eventName}
								<br>
							</c:forEach>
							</a>
						</td>
						
					</c:forEach>
				</tr>
				
				<tr>
					<c:forEach var="date" items="${dates}" begin="28" end="34" varStatus="loop">
						<td>
							<a href="/monthly-calendar?date=${curMonthDate}&dayDate=${date}">
							${date.dayOfMonth}
							<br>
							<c:set var="dateString">${date.toString()}</c:set>
							<c:forEach var="dayEvent" items="${events[dateString]}">
								${dayEvent.eventName}
								<br>
							</c:forEach>
							</a>
						</td>
						
					</c:forEach>
				</tr>
				
				<tr>
					<c:forEach var="date" items="${dates}" begin="35" end="41" varStatus="loop">
						<td>
							<a href="/monthly-calendar?date=${curMonthDate}&dayDate=${date}">
							${date.dayOfMonth}
							<br>
							<c:set var="dateString">${date.toString()}</c:set>
							<c:forEach var="dayEvent" items="${events[dateString]}">
								${dayEvent.eventName}
								<br>
							</c:forEach>
							</a>
						</td>
						
					</c:forEach>
				</tr>
			</table>
		</div>
		<div class="daily-info-section">
			<h3 id="day-info-header">${curDayMonthString} ${curDayDate.dayOfMonth}</h3>
			<ul>
				<c:forEach var="event" items="${dayEvents}">
					<li>
						${event.eventName}	(${event.startTime} - ${event.endTime})<a href="/delete/${event.id}"> <i class="fa fa-trash" aria-hidden="true"></i></a>					</li>
				</c:forEach>
			</ul>
			<a href="/delete-event" id="delete-button">Delete</a>
		</div>
	</div>
	
</body>
</html>